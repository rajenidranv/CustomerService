package com.hankerrank.sample.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.hankerrank.sample.exception.BadResourceRequestException;
import com.hankerrank.sample.exception.NoSuchResourceFoundException;
import com.hankerrank.sample.model.Customer;
import com.hankerrank.sample.repository.CustomerRepository;


@org.springframework.stereotype.Service
public class CustomerService {
	
	 @Autowired
	    private CustomerRepository customerRepository;
		

	 public List<Object> getAllCustomer(){
			List<Object> list = new ArrayList<Object>();
			customerRepository.findAll().forEach(e -> list.add(e));
			if(!list.isEmpty())
			{
				return list;
			}
			else{
				throw new NoSuchResourceFoundException("Customers not available");
		    }			
		}
		
		public Customer getCustomer(Long id) {
			Optional<Customer> customer = customerRepository.findById(id);
			if(customer.isPresent())
	        return customer.get();
			else
			return null;
	    }
		
		public Customer addCustomer(Customer customer){
			Customer saveCustomer;
			 	if (getCustomer(customer.getCustomerId())!= null)
			    {
			        throw new BadResourceRequestException("Customer with same ID exists");
			    }

			    saveCustomer = customerRepository.save(customer);

			    return saveCustomer;
		}
		
		public void updateCustomer(Customer customer){
			customerRepository.save(customer);
		}
		
		public void deleteCustomer(Long id) {
			customerRepository.deleteById(id);
	    }
		
		public void deleteAllCustomer() {
			customerRepository.deleteAll();
	    }
}
