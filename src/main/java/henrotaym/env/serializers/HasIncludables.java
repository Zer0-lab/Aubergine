package henrotaym.env.serializers;

import java.util.Set;

// TODO reactive to filter relations
// @JsonFilter(value = "include")
public interface HasIncludables {
  public Set<String> includables();
}
