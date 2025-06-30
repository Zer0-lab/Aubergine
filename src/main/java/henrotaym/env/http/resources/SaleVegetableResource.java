package henrotaym.env.http.resources;

import java.math.BigInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SaleVegetableResource {
  private final Long id;
  private final BigInteger quantity;
  private final BigInteger price;
  private final VegetableResource vegetable;
}
