package henrotaym.env.services;

import henrotaym.env.entities.Sale;
import henrotaym.env.entities.SaleVegetable;
import henrotaym.env.entities.Vegetable;
import henrotaym.env.exceptions.InsufficientStockException;
import henrotaym.env.http.requests.SaleRequest;
import henrotaym.env.http.requests.SaleVegetableRequest;
import henrotaym.env.repositories.SaleRepository;
import henrotaym.env.repositories.SaleVegetableRepository;
import henrotaym.env.repositories.VegetableRepository;
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleServiceBack {
  private final VegetableRepository vegetableRepository;
  private final SaleRepository saleRepository;
  private final SaleVegetableRepository saleVegetableRepository;

  public Sale checkout(SaleRequest saleRequest) {
    List<Vegetable> vegetables =
        saleRequest.getVegetables().stream()
            .map(
                vegetableRequest -> {
                  Vegetable vegetable =
                      this.vegetableRepository
                          .findById(vegetableRequest.getVegetable().getId())
                          .orElseThrow();

                  if (vegetable.getStock().longValue()
                      < vegetableRequest.getQuantity().longValue()) {
                    throw new InsufficientStockException(vegetable, vegetableRequest.getQuantity());
                  }

                  return vegetable;
                })
            .toList();

    Sale sale = new Sale();
    sale.setAmount(BigInteger.ZERO);
    this.saleRepository.save(sale);

    for (int index = 0; index < saleRequest.getVegetables().size(); index++) {
      SaleVegetableRequest vegetableRequest = saleRequest.getVegetables().get(index);
      Vegetable vegetable = vegetables.get(index);

      SaleVegetable saleVegetable = new SaleVegetable();
      saleVegetable.setPrice(vegetable.getPrice());
      saleVegetable.setQuantity(vegetableRequest.getQuantity());
      saleVegetable.setVegetable(vegetable);
      saleVegetable.setSale(sale);
      this.saleVegetableRepository.save(saleVegetable);
      vegetable.setStock(vegetable.getStock().subtract(saleVegetable.getQuantity()));
      this.vegetableRepository.save(vegetable);

      sale.setAmount(
          sale.getAmount().add(saleVegetable.getQuantity().multiply(saleVegetable.getPrice())));
      sale.getSaleVegetables().add(saleVegetable);
    }
    this.saleRepository.save(sale);

    return sale;
  }
}
