package henrotaym.env.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@KafkaListener
@RetryableTopic
public @interface KafkaRetryableListener {
  @AliasFor(annotation = KafkaListener.class, attribute = "topics")
  String[] value() default {};

  @AliasFor(annotation = RetryableTopic.class, attribute = "attempts")
  String attempts() default "1";
}
