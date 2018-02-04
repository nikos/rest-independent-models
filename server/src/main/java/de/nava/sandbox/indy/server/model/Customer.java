package de.nava.sandbox.indy.server.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.nava.sandbox.indy.server.model.Customer.CustomerBuilder;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonDeserialize(builder = CustomerBuilder.class)
// NOTE: @JsonIgnoreProperties(ignoreUnknown = true) not required,
//       since Spring Boot instantiates ObjectMapper already tolerant.
public final class Customer {

  private final Long id;
  @NotNull private final String firstname;
  @NotNull private final String lastname;


  @JsonPOJOBuilder(withPrefix = "")
  public static final class CustomerBuilder {
  }

}

