package henrotaym.env.mappers;

import henrotaym.env.enums.exceptions.ExceptionType;
import henrotaym.env.exceptions.ApiException;
import henrotaym.env.exceptions.InsufficientStockException;
import henrotaym.env.http.resources.exceptions.ApiExceptionResource;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Component
public class ApiExceptionMapper {
  public ResponseEntity<ApiExceptionResource> responseEntity(ApiException exception) {
    ApiExceptionResource resource = this.resource(exception);

    return ResponseEntity.status(resource.status()).body(resource);
  }

  public ApiExceptionResource resource(ApiException exception) {
    return new ApiExceptionResource(
        exception.getMessage(),
        exception.getStatus(),
        exception.getTimestamp(),
        exception.getType(),
        exception.getData());
  }

  public ApiException insufficientStockException(InsufficientStockException exception) {
    HashMap<String, Object> data = new HashMap<>();
    data.put(
        "vegetable",
        Map.of(
            "id", exception.getVegetable().getId(), "stock", exception.getVegetable().getStock()));
    data.put("quantity", exception.getQuantity());

    return this.builder(exception)
        .status(HttpStatus.UNPROCESSABLE_ENTITY)
        .type(ExceptionType.INSUFFICIENT_STOCK)
        .data(data)
        .build();
  }

  public ApiException entityNotFound(EntityNotFoundException exception) {
    return this.builder(exception)
        .status(HttpStatus.NOT_FOUND)
        .type(ExceptionType.MODEL_NOT_FOUND)
        .build();
  }

  public ApiException methodArgumentNotValid(MethodArgumentNotValidException exception) {
    HashMap<String, String> data = new HashMap<>();

    exception
        .getFieldErrors()
        .forEach((error) -> data.put(error.getField(), error.getDefaultMessage()));

    return this.builder(exception)
        .message("Request is invalid.")
        .status(HttpStatus.BAD_REQUEST)
        .type(ExceptionType.VALIDATION_ERROR)
        .data(data)
        .build();
  }

  public ApiException throwable(Throwable exception) {
    return this.builder(exception).build();
  }

  private ApiException.ApiExceptionBuilder builder(Throwable exception) {
    return ApiException.builder()
        .message(exception.getMessage())
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .timestamp(LocalDateTime.now())
        .type(ExceptionType.MODEL_NOT_FOUND)
        .stackTrace(exception.getStackTrace());
  }
}
