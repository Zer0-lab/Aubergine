package henrotaym.env.http.requests;

import henrotaym.env.http.requests.relationships.VegetableRelationshipRequest;
import java.math.BigInteger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SaleVegetableRequest {
  private final BigInteger quantity;
  private final VegetableRelationshipRequest vegetable;
}
