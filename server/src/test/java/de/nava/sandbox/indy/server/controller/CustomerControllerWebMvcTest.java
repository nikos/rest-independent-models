package de.nava.sandbox.indy.server.controller;

import de.nava.sandbox.indy.server.model.Customer;
import de.nava.sandbox.indy.server.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Only for demo-ing purposes.
 *
 * In this very project using the WebMvcTest does not make a real difference, since
 * a minimized (sliced) context with only the controller is almost identical to the
 * whole web application.
 *
 * See: https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerWebMvcTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CustomerService customerService;

  @Test
  public void shouldReturnOneCustomer() throws Exception {
    given(customerService.existsById(23L)).willReturn(true);
    given(customerService.findById(23L))
      .willReturn(Customer.builder().id(23L).firstname("Ada").lastname("Do").build());

    mvc.perform(get("/api/customers/23").accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().json("{\"id\":23,\"firstname\":\"Ada\",\"lastname\":\"Do\"}"));
  }

}
