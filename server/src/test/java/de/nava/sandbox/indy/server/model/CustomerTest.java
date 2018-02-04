package de.nava.sandbox.indy.server.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;


public class CustomerTest {

  private static final Logger LOG = LoggerFactory.getLogger(CustomerTest.class);

  private ObjectMapper objectMapper;

  @Before
  public void setUp() {
    objectMapper = new ObjectMapper()
      .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void shouldTolerateUnknownJsonProperty() throws IOException {
    String jsonAsString =
      "{\"id\":42, \"firstname\": \"Ada\", \"lastname\": \"Lovelace\", \"foo\": \"bar\"}";

    Customer customer = objectMapper.readValue(jsonAsString, Customer.class);

    assertThat(customer).isNotNull();
    LOG.info("Deserialized: {} ", customer);
  }

}