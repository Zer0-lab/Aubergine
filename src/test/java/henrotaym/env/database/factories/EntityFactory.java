package henrotaym.env.database.factories;

import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.springframework.data.jpa.repository.JpaRepository;

@RequiredArgsConstructor
public abstract class EntityFactory<T> {
  protected final Faker faker;
  private final JpaRepository<T, Long> repository;

  protected abstract T entity();

  protected abstract void attributes(T entity);

  protected void relationships(T entity) {}

  public T make(Consumer<T> callback) {
    T entity = this.entity();
    this.attributes(entity);
    callback.accept(entity);
    this.relationships(entity);

    return entity;
  }

  public T make() {
    return this.make((_) -> {});
  }

  public T create(Consumer<T> callback) {
    return this.repository.save(this.make(callback));
  }

  public T create() {
    return this.create((_) -> {});
  }
}
