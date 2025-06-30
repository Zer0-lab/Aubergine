package henrotaym.env.tests.feature;

import static org.junit.jupiter.api.Assertions.*;

import henrotaym.env.ApplicationTest;
import henrotaym.env.database.factories.VegetableFactory;
import henrotaym.env.entities.Sale;
import henrotaym.env.entities.Vegetable;
import henrotaym.env.exceptions.InsufficientStockException;
import henrotaym.env.http.requests.SaleRequest;
import henrotaym.env.http.requests.SaleVegetableRequest;
import henrotaym.env.http.requests.relationships.VegetableRelationshipRequest;
import henrotaym.env.repositories.SaleRepository;
import henrotaym.env.repositories.VegetableRepository;
import henrotaym.env.services.SaleService;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SaleServiceFeatureTest extends ApplicationTest {

  @Autowired private SaleService saleService;
  @Autowired private VegetableFactory vegetableFactory;
  @Autowired private VegetableRepository vegetableRepository;
  @Autowired private SaleRepository saleRepository;

  private SaleRequest makeRequest(Vegetable vegetable, BigInteger quantity) {
    var relationship = new VegetableRelationshipRequest(vegetable.getId());
    var item = new SaleVegetableRequest(quantity, relationship);
    return new SaleRequest(List.of(item));
  }

  @Test
  public void it_creates_a_sale_when_stock_is_sufficient() {
    Vegetable veg = this.vegetableFactory.create();
    BigInteger initialStock = veg.getStock();
    BigInteger quantity = initialStock.divide(BigInteger.TWO);

    SaleRequest request = makeRequest(veg, quantity);

    Sale sale = this.saleService.checkout(request);

    assertNotNull(sale.getId(), "La vente doit être persistée");
    assertEquals(1, this.saleRepository.count(), "Une seule vente doit exister en base");

    Vegetable updated = this.vegetableRepository.findById(veg.getId()).orElseThrow();
    assertEquals(
        initialStock.subtract(quantity), updated.getStock(), "Le stock doit être décrémenté");
  }

  @Test
  public void it_fails_to_create_sale_when_stock_is_insufficient() {
    Vegetable veg = this.vegetableFactory.create();
    BigInteger initialStock = veg.getStock();
    BigInteger quantity = initialStock.add(BigInteger.TEN);

    SaleRequest request = makeRequest(veg, quantity);

    assertThrows(InsufficientStockException.class, () -> this.saleService.checkout(request));

    assertEquals(0, this.saleRepository.count(), "Aucune vente ne doit être enregistrée");

    Vegetable notUpdated = this.vegetableRepository.findById(veg.getId()).orElseThrow();
    assertEquals(initialStock, notUpdated.getStock(), "Le stock ne doit pas changer");
  }
}
