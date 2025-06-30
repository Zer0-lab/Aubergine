package henrotaym.env.exceptions;

import henrotaym.env.entities.Vegetable;
import java.math.BigInteger;
import lombok.Getter;

@Getter
public class InsufficientStockException extends RuntimeException {
  private final Vegetable vegetable;
  private final BigInteger quantity;

  public InsufficientStockException(Vegetable vegetable, BigInteger quantity) {
    super("Insufficient stock");
    this.vegetable = vegetable;
    this.quantity = quantity;
  }
}
