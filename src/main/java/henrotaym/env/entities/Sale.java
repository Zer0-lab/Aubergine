  package henrotaym.env.entities;

  import jakarta.persistence.Column;
  import jakarta.persistence.Entity;
  import jakarta.persistence.EntityListeners;
  import jakarta.persistence.GeneratedValue;
  import jakarta.persistence.GenerationType;
  import jakarta.persistence.Id;
  import jakarta.persistence.OneToMany;
  import jakarta.persistence.Table;
  import java.math.BigInteger;
  import java.time.LocalDateTime;
  import java.util.ArrayList;
  import java.util.List;
  import lombok.AllArgsConstructor;
  import lombok.Getter;
  import lombok.NoArgsConstructor;
  import lombok.Setter;
  import org.springframework.data.annotation.CreatedDate;
  import org.springframework.data.jpa.domain.support.AuditingEntityListener;

  @Entity
  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  @EntityListeners(AuditingEntityListener.class)
  @Table(name = "sales")
  public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "amount", nullable = false)
    private BigInteger amount;

    @OneToMany(mappedBy = "sale")
    private List<SaleVegetable> saleVegetables = new ArrayList<>();
  }
