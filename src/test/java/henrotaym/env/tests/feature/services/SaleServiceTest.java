package henrotaym.env.tests.feature.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import henrotaym.env.entities.Sale;
import henrotaym.env.entities.Vegetable;
import henrotaym.env.exceptions.InsufficientStockException;
import henrotaym.env.http.requests.SaleRequest;
import henrotaym.env.http.requests.SaleVegetableRequest;
import henrotaym.env.http.requests.relationships.VegetableRelationshipRequest;
import henrotaym.env.repositories.SaleRepository;
import henrotaym.env.repositories.SaleVegetableRepository;
import henrotaym.env.repositories.VegetableRepository;
import henrotaym.env.services.SaleService;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SaleServiceTest {

  @Mock private VegetableRepository vegetableRepository;
  @Mock private SaleRepository saleRepository;
  @Mock private SaleVegetableRepository saleVegetableRepository;

  @InjectMocks private SaleService saleService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  private SaleRequest makeRequest(Vegetable vegetable, BigInteger quantity) {
    var relationship = new VegetableRelationshipRequest(vegetable.getId());
    var item = new SaleVegetableRequest(quantity, relationship);
    return new SaleRequest(List.of(item));
  }

  @Test
  void it_finalizes_sale_when_stock_is_sufficient() {
    // Given
    Vegetable veg = new Vegetable();
    veg.setId(1L);
    veg.setStock(BigInteger.valueOf(10));
    veg.setPrice(BigInteger.valueOf(5));

    BigInteger quantity = BigInteger.valueOf(3);
    SaleRequest request = makeRequest(veg, quantity);

    when(vegetableRepository.findAllById(List.of(1L))).thenReturn(List.of(veg));

    // When
    Sale result = saleService.checkout(request);

    // Then
    assertNotNull(result);
    verify(saleRepository).save(any(Sale.class));
    verify(vegetableRepository).saveAll(anyList());
    verify(saleVegetableRepository).saveAll(anyList());
  }

  @Test
  void it_throws_exception_and_does_not_save_when_stock_is_insufficient() {
    // Given
    Vegetable veg = new Vegetable();
    veg.setId(2L);
    veg.setStock(BigInteger.valueOf(1));

    BigInteger quantity = BigInteger.valueOf(3);
    SaleRequest request = makeRequest(veg, quantity);

    when(vegetableRepository.findAllById(List.of(2L))).thenReturn(List.of(veg));

    // When / Then
    assertThrows(InsufficientStockException.class, () -> saleService.checkout(request));

    verify(saleRepository, never()).save(any());
    verify(vegetableRepository, never()).saveAll(anyList());
    verify(saleVegetableRepository, never()).saveAll(anyList());
  }
}
