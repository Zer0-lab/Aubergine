package henrotaym.env.validators;

import henrotaym.env.annotations.ExistsInDatabase;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

public class ExistsInDatabaseValidator implements ConstraintValidator<ExistsInDatabase, Long> {

  private Class<? extends JpaRepository<?, Long>> repositoryClass;

  @Autowired ApplicationContext applicationContext;

  @Override
  public void initialize(ExistsInDatabase constraintAnnotation) {
    this.repositoryClass = constraintAnnotation.repository();
  }

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }

    return this.applicationContext.getBean(this.repositoryClass).existsById(value);
  }
}
