package henrotaym.env.mappers;

import henrotaym.env.entities.Vegetable;
import henrotaym.env.http.resources.VegetableResource;
import org.springframework.stereotype.Component;

@Component
public class VegetableMapper {
  public VegetableResource resource(Vegetable vegetable) {
    return new VegetableResource(
        vegetable.getId(),
        vegetable.getName(),
        vegetable.getUnit(),
        vegetable.getPrice(),
        vegetable.getStock());
  }
}
