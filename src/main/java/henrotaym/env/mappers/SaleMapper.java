package henrotaym.env.mappers;

import henrotaym.env.entities.Sale;
import henrotaym.env.http.resources.SaleResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleMapper {
  private final SaleVegetableMapper saleVegetableMapper;

  public SaleResource resource(Sale sale) {
    return new SaleResource(
        sale.getId(),
        sale.getAmount(),
        sale.getSaleVegetables().stream().map(this.saleVegetableMapper::resource).toList());
  }
}
