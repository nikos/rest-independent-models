package de.nava.sandbox.indy.client.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.nava.sandbox.indy.client.model.Customer.CustomerBuilder;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
@JsonDeserialize(builder = CustomerBuilder.class)
public final class Customer {

  private final Long id;
  private final String firstname;
  private final String lastname;
  private final Date birthdate;

  @JsonPOJOBuilder(withPrefix = "")
  public static final class CustomerBuilder {
  }

}
