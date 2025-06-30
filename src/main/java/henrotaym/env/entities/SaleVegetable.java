package henrotaym.env.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "sale_vegetable")
public class SaleVegetable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "quantity", nullable = false)
  private BigInteger quantity;

  @Column(name = "price", nullable = false)
  private BigInteger price;

  @ManyToOne(optional = false)
  @JoinColumn(name = "vegetable_id", nullable = false)
  private Vegetable vegetable;

  @ManyToOne(optional = false)
  @JoinColumn(name = "sale_id", nullable = false)
  private Sale sale;
}
