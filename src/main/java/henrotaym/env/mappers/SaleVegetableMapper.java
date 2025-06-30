package henrotaym.env.mappers;

import henrotaym.env.entities.SaleVegetable;
import henrotaym.env.http.resources.SaleVegetableResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleVegetableMapper {
  private final VegetableMapper vegetableMapper;

  public SaleVegetableResource resource(SaleVegetable saleVegetable) {
    return new SaleVegetableResource(
        saleVegetable.getId(),
        saleVegetable.getQuantity(),
        saleVegetable.getPrice(),
        this.vegetableMapper.resource(saleVegetable.getVegetable()));
  }
}
