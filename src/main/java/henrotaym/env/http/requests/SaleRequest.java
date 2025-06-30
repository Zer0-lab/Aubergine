package henrotaym.env.http.requests;

import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SaleRequest {
  private final List<SaleVegetableRequest> vegetables;
}
