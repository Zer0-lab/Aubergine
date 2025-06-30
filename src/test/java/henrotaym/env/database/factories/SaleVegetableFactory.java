package henrotaym.env.database.factories;

import henrotaym.env.entities.SaleVegetable;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class SaleVegetableFactory extends EntityFactory<SaleVegetable> {

  private final SaleFactory saleFactory;
  private final VegetableFactory vegetableFactory;

  public SaleVegetableFactory(
      Faker faker,
      JpaRepository<SaleVegetable, Long> repository,
      VegetableFactory vegetableFactory,
      SaleFactory saleFactory) {
    super(faker, repository);
    this.saleFactory = saleFactory;
    this.vegetableFactory = vegetableFactory;
  }

  @Override
  protected SaleVegetable entity() {
    return new SaleVegetable();
  }

  @Override
  protected void attributes(SaleVegetable entity) {
    entity.setPrice(BigInteger.valueOf(this.faker.number().numberBetween(1000, 10000)));
    entity.setQuantity(BigInteger.valueOf(this.faker.number().numberBetween(1, 10)));
  }

  @Override
  protected void relationships(SaleVegetable entity) {
    if (entity.getSale() == null) {
      entity.setSale(this.saleFactory.create());
    }

    if (entity.getVegetable() == null) {
      entity.setVegetable(this.vegetableFactory.create());
    }
  }
}
