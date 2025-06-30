package henrotaym.env.repositories;

import henrotaym.env.entities.Vegetable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VegetableRepository extends JpaRepository<Vegetable, Long> {}
