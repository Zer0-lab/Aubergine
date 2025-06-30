package henrotaym.env.database.factories;

import henrotaym.env.entities.Vegetable;
import henrotaym.env.enums.entities.VegetableUnit;
import java.math.BigInteger;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class VegetableFactory extends EntityFactory<Vegetable> {

  public VegetableFactory(Faker faker, JpaRepository<Vegetable, Long> repository) {
    super(faker, repository);
  }

  @Override
  protected Vegetable entity() {
    return new Vegetable();
  }

  @Override
  protected void attributes(Vegetable entity) {
    entity.setName(this.faker.food().vegetable());
    entity.setPrice(BigInteger.valueOf(this.faker.number().numberBetween(1000, 10000)));
    entity.setStock(BigInteger.valueOf(this.faker.number().numberBetween(1000, 10000)));
    VegetableUnit[] units = VegetableUnit.values();
    entity.setUnit(units[this.faker.random().nextInt(units.length)]);
  }
}
