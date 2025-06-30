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
import jakarta.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Transactional
public class SaleService {
  private final VegetableRepository vegetableRepository;
  private final SaleRepository saleRepository;
  private final SaleVegetableRepository saleVegetableRepository;

  @Transactional
  public Sale checkout(SaleRequest saleRequest) {
    List<Long> vegetableIds =
        saleRequest.getVegetables().stream()
            .map(saleVegetableRequest -> saleVegetableRequest.getVegetable().getId())
            .toList();

    Map<Long, Vegetable> vegetablesMap =
        vegetableRepository.findAllById(vegetableIds).stream()
            .collect(Collectors.toMap(Vegetable::getId, v -> v));

    for (SaleVegetableRequest itemRequest : saleRequest.getVegetables()) {
      Vegetable vegetable = vegetablesMap.get(itemRequest.getVegetable().getId());
      if (vegetable.getStock().compareTo(itemRequest.getQuantity()) < 0) {
        throw new InsufficientStockException(vegetable, itemRequest.getQuantity());
      }
    }

    Sale sale = new Sale();
    sale.setAmount(BigInteger.ZERO);

    List<SaleVegetable> saleVegetables = new ArrayList<>();
    List<Vegetable> vegetables = new ArrayList<>();

    for (SaleVegetableRequest saleVegetableRequest : saleRequest.getVegetables()) {
      Vegetable vegetable = vegetablesMap.get(saleVegetableRequest.getVegetable().getId());

      vegetable.setStock(vegetable.getStock().subtract(saleVegetableRequest.getQuantity()));
      vegetables.add(vegetable);

      SaleVegetable saleVegetable = new SaleVegetable();
      saleVegetable.setPrice(vegetable.getPrice());
      saleVegetable.setQuantity(saleVegetableRequest.getQuantity());
      saleVegetable.setVegetable(vegetable);
      saleVegetable.setSale(sale);
      saleVegetables.add(saleVegetable);

      sale.setAmount(
          sale.getAmount().add(saleVegetable.getQuantity().multiply(saleVegetable.getPrice())));
    }

    sale.setSaleVegetables(saleVegetables);

    saleRepository.save(sale);
    vegetableRepository.saveAll(vegetables);
    saleVegetableRepository.saveAll(saleVegetables);

    return sale;
  }
}
