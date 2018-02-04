package de.nava.sandbox.indy.client.service;

import de.nava.sandbox.indy.client.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@RunWith(SpringRunner.class)
@RestClientTest(CustomerSyncService.class)
public class CustomerSyncServiceMockServerTest {

  @Autowired
  private CustomerSyncService service;

  @Autowired
  private MockRestServiceServer server;

  @Test
  public void getVehicleDetailsWhenResultIsSuccessShouldReturnDetails() {
    server.expect(requestTo(service.getServerEndpointUri() + "customers/4711"))
      .andRespond(
        withSuccess("{\"id\": 4711, \"firstname\": \"Ada\", \"lastname\": \"Do\"}",
                    MediaType.APPLICATION_JSON));
    Customer customer = service.getCustomer(4711L);
    assertThat(customer.getId()).isEqualTo(4711L);
  }

}