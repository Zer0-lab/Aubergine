package henrotaym.env.http.controllers;

import henrotaym.env.entities.Sale;
import henrotaym.env.enums.ProfileName;
import henrotaym.env.http.requests.SaleRequest;
import henrotaym.env.http.resources.SaleResource;
import henrotaym.env.mappers.SaleMapper;
import henrotaym.env.services.SaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Profile(ProfileName.HTTP)
@RequestMapping("sales")
public class SaleController {
  private final SaleService saleService;
  private final SaleMapper saleMapper;

  @PostMapping("")
  public ResponseEntity<SaleResource> store(@RequestBody @Valid SaleRequest request) {
    Sale sale = this.saleService.checkout(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(this.saleMapper.resource(sale));
  }
}
