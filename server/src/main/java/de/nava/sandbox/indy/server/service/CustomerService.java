package de.nava.sandbox.indy.server.service;

import de.nava.sandbox.indy.server.model.Customer;

import java.util.Collection;


public interface CustomerService {

  Collection<Customer> findAll();

  boolean existsById(long id);
  Customer findById(Long id);

  void save(Customer customer);

  void deleteById(Long id);

}
