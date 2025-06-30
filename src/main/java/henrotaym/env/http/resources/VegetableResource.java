package henrotaym.env.http.resources;

import henrotaym.env.enums.entities.VegetableUnit;
import java.math.BigInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class VegetableResource {
  private final Long id;
  private final String name;
  private final VegetableUnit unit;
  private final BigInteger price;
  private final BigInteger stock;
}
