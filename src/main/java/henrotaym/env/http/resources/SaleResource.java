package henrotaym.env.http.resources;

import java.math.BigInteger;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SaleResource {
  private final Long id;
  private final BigInteger amount;
  private final List<SaleVegetableResource> items;
}
