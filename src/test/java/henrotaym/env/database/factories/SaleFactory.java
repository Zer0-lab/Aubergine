package henrotaym.env.database.factories;

import henrotaym.env.entities.Sale;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class SaleFactory extends EntityFactory<Sale> {

  public SaleFactory(Faker faker, JpaRepository<Sale, Long> repository) {
    super(faker, repository);
  }

  @Override
  protected Sale entity() {
    return new Sale();
  }

  @Override
  protected void attributes(Sale entity) {
    entity.setAmount(BigInteger.valueOf(this.faker.number().numberBetween(1000, 10000)));
  }
}
