package henrotaym.env.queues.listeners;

import henrotaym.env.queues.events.Event;

public interface Listener<T extends Event> {
  public void listen(T event);
}
