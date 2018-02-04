package de.nava.sandbox.indy.client.service;

import de.nava.sandbox.indy.client.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;


@Service
public class CustomerSyncService {

  private static final Logger log = LoggerFactory.getLogger(CustomerSyncService.class);

  private final RestTemplate restTemplate;
  private final String serverEndpointUri;

  private CustomerSyncService(RestTemplateBuilder restTemplateBuilder,
    @Value("${indy.server.baseUri}") String serverEndpointUri)
  {
    // NOTE: it might be a good idea to enhance the (default) error handler
    this.restTemplate = restTemplateBuilder.build();
    this.serverEndpointUri = serverEndpointUri;
  }

  public Collection<Customer> getAllCustomers() {
    ResponseEntity<Customer[]> entities =
      restTemplate.getForEntity(format("%s/customers", serverEndpointUri), Customer[].class);
    Customer[] customers = entities.getBody();
    log.info("Retrieved customers: {}", Arrays.asList(customers));
    return Arrays.asList(customers);
  }

  public Customer getCustomer(long id) {
    ResponseEntity<Customer> entity =
      restTemplate.getForEntity(format("%s/customers/%d", serverEndpointUri, id), Customer.class);
    Customer customer = entity.getBody();
    log.info("Retrieved customer: {}", customer);
    return customer;
  }

  public Customer createCustomer(Customer customer) {
    try {
      ResponseEntity<Customer> entity =
        restTemplate.postForEntity(format("%s/customers", serverEndpointUri), customer,
          Customer.class
        );
      // HttpStatus statusCode = entity.getStatusCode();
      // MediaType contentType = entity.getHeaders().getContentType();
      Customer newCustomer = entity.getBody();
      log.info("Created customer: {}", newCustomer);
      return newCustomer;
    }
    catch (HttpClientErrorException e) {
      String response = e.getResponseBodyAsString();
      String statusText = e.getStatusText();
      if (e.getStatusCode().is4xxClientError()) {
        throw new IllegalArgumentException("Incomplete customer given");
      } else {
        throw new RuntimeException(format("Server Problem, status: %s [%s]", statusText, response));
      }
    }
  }

  // Only visible for testing
  String getServerEndpointUri() {
    return serverEndpointUri;
  }

}
