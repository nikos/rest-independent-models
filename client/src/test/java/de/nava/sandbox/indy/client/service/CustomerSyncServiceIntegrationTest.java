package de.nava.sandbox.indy.client.service;

import de.nava.sandbox.indy.client.IndyClientApplication;
import de.nava.sandbox.indy.client.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IndyClientApplication.class)
public class CustomerSyncServiceIntegrationTest {

  @Autowired
  private CustomerSyncService customerSyncService;

  @Test
  public void thatAllCustomersCanBeRetrieved() {
    Collection<Customer> customers = customerSyncService.getAllCustomers();
    assertThat(customers.size()).isGreaterThanOrEqualTo(0);
  }

  @Test
  public void thatNewCustomerCanBeCreated() {
    Customer c = Customer.builder().id(23L).firstname("Ida").lastname("Rhodes").build();
    Customer created = customerSyncService.createCustomer(c);
    assertThat(created.getId()).isEqualTo(23L);
    assertThat(created.getFirstname()).isEqualTo("Ida");
    assertThat(created.getLastname()).isEqualTo("Rhodes");
    assertThat(created.getBirthdate()).isNull();
  }

  @Test(expected = IllegalArgumentException.class)
  public void thatIncompleteCustomerCanNotBeCreated() {
    Customer c = Customer.builder().id(23L).firstname("").build();
    customerSyncService.createCustomer(c);
  }

}