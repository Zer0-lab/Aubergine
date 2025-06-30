package henrotaym.env.utils.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.function.ThrowingConsumer;

@Component
@RequiredArgsConstructor
public class JsonClient {
  private final MockMvc mockMvc;
  private final JsonResponse response;
  private final JsonRequest request;

  public JsonClient request(ThrowingConsumer<JsonRequest> callback) throws Exception {
    callback.acceptWithException(this.request);

    return this;
  }

  public JsonResponse perform() throws Exception {
    ResultActions result = this.mockMvc.perform(this.request.request());

    this.response.setResponse(result);

    return response;
  }
}
