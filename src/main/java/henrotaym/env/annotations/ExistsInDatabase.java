package henrotaym.env.annotations;

import henrotaym.env.validators.ExistsInDatabaseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.jpa.repository.JpaRepository;

@Constraint(validatedBy = ExistsInDatabaseValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
// @Repeatable(List.class)
public @interface ExistsInDatabase {

  String message() default "Entity not found.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  Class<? extends JpaRepository<?, Long>> repository();
}
