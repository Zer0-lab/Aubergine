package henrotaym.env.queues.emitters;

import henrotaym.env.queues.events.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Emitter {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  public <T extends Event> void send(T event) {
    this.kafkaTemplate.send(event.eventName(), event);
  }
}
