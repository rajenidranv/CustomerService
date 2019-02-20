package com.hankerrank.sample.repository;

import org.springframework.data.repository.CrudRepository;

import com.hankerrank.sample.model.Customer;

public interface CustomerRepository  extends CrudRepository<Customer, Long>{

}
