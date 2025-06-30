package henrotaym.env.configurations;

import net.datafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class FakerConfiguration {
  @Bean
  Faker faker() {
    return new Faker();
  }
}
