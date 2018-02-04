package de.nava.sandbox.indy.server.controller;

import de.nava.sandbox.indy.server.model.Customer;
import de.nava.sandbox.indy.server.service.CustomerService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/api")
public class CustomerController {

  private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/customers")
  public Collection<Customer> getAllCustomers() {
    return customerService.findAll();
  }

  @GetMapping("/customers/{id}")
  public Customer getCustomer(@PathVariable("id") long id) {
    assertCustomerExists(id);
    return customerService.findById(id);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/customers")
  public Customer add(@RequestBody @Valid Customer customer) {
    customerService.save(customer);
    return customer;
  }

  @DeleteMapping("/customers/{id}")
  public void delete(@PathVariable("id") long id) {
    assertCustomerExists(id);
    customerService.deleteById(id);
  }

  // ~~

  private void assertCustomerExists(long id) {
    if (!customerService.existsById(id)) {
      throw new IllegalArgumentException("Unable to find customer for " + id);
    }
  }

  @ExceptionHandler({IllegalArgumentException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public void handleException(Exception e) {
    log.warn("Problem with customer: {}", e.getMessage());
  }

}
