package henrotaym.env.entities;

import henrotaym.env.enums.entities.VegetableUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigInteger;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "vegetables")
public class Vegetable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @Column(name = "price", nullable = false)
  private BigInteger price;

  @Column(name = "unit", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private VegetableUnit unit;

  @Column(name = "stock", nullable = false)
  private BigInteger stock;

  @OneToMany(mappedBy = "vegetable")
  private List<SaleVegetable> saleVegetables;
}
