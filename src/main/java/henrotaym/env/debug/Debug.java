package henrotaym.env.debug;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Debug {
  public static DebugLogger logger() {
    return new DebugLogger(new ObjectMapper());
  }
}
