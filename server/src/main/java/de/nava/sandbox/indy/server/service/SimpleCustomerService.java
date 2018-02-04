package de.nava.sandbox.indy.server.service;

import de.nava.sandbox.indy.server.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SimpleCustomerService implements CustomerService {

  private static final Map<Long, Customer> CUSTOMERS = new ConcurrentHashMap<>();

  static {
    CUSTOMERS.put(42L, Customer.builder().id(42L).firstname("Ada").lastname("Lovelace").build());
    CUSTOMERS.put(43L, Customer.builder().id(43L).firstname("Grace").lastname("Hopper").build());
  }

  @Override
  public Collection<Customer> findAll() {
    return CUSTOMERS.values();
  }

  @Override
  public boolean existsById(long id) {
    return CUSTOMERS.containsKey(id);
  }

  @Override
  public Customer findById(Long id) {
    return CUSTOMERS.get(id);
  }

  @Override
  public void save(Customer customer) {
    CUSTOMERS.put(customer.getId(), customer);
  }

  @Override
  public void deleteById(Long id) {
    CUSTOMERS.remove(id);
  }

}
