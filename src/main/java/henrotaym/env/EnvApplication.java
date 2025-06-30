package henrotaym.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EnvApplication {

  public static void main(String[] args) {
    SpringApplication.run(EnvApplication.class, args);
  }
}
