package com.hankerrank.sample.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hankerrank.sample.exception.BadResourceRequestException;
import com.hankerrank.sample.exception.NoSuchResourceFoundException;
import com.hankerrank.sample.model.Customer;
import com.hankerrank.sample.service.CustomerService;


@RestController
@RequestMapping("/tcs/hack/v1")
public class CustomerController {
	
	@Autowired
    private CustomerService customerService;
	
	 @GetMapping("/customer")
	    public ResponseEntity<Object> getAllCustomers() throws Exception {
		 ResponseEntity<Object> response = null;
		try{
			response = new ResponseEntity<Object>(customerService.getAllCustomer(), HttpStatus.OK);
		}
		catch(NoSuchResourceFoundException bex){
			response = new ResponseEntity<Object>("Customers not available",HttpStatus.NOT_FOUND);
		}
		return response;
	    }
	   
	    @GetMapping("/customer/{id}")
	    public ResponseEntity<Object> getAllCustomer(@PathVariable("id") Long id) throws Exception {
	    	Object customer = customerService.getCustomer(id);
	    	if(customer!=null){
	    		return new ResponseEntity<Object>(customer, HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<Object>("Customer not found",HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    @PostMapping("/customer")
	    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) throws Exception {
	    	ResponseEntity<Object> response = null;
	        try{
	            response = new ResponseEntity<Object>(customerService.addCustomer(customer),HttpStatus.CREATED);
	        }
	        catch (BadResourceRequestException bex) {
	            response = new ResponseEntity<Object>("Customer with same ID exists",HttpStatus.BAD_REQUEST);
	        }
	        return response;
	    }
	    
	    
	    
	    @PutMapping("/customer/{id}")
	    public ResponseEntity<Object> updateCustomer(@RequestBody Customer customer,  @PathVariable Long id) throws Exception {
	    	Object cust = customerService.getCustomer(id);
	    	if(cust != null){
	    		customerService.updateCustomer(customer);
	    		return new ResponseEntity<Object>(customer,HttpStatus.OK);    		
	    	}
	    	else{
	    		return new ResponseEntity<Object>("No customer found to update",HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    @DeleteMapping("/customer/{id}")
	    public ResponseEntity<Object> deleteCustomer(@PathVariable("id") Long id) {
	    	Object customer = customerService.getCustomer(id);
	    	if(customer!=null){
	    		customerService.deleteCustomer(id);
	        	return new ResponseEntity<Object>("Customer is deleted",HttpStatus.OK);
	    	}
	    	else{
	    		return new ResponseEntity<Object>("Customer not found to be deleted",HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    @DeleteMapping("/customer")
	    public ResponseEntity<Object> deleteAllCustomer() {
	    		customerService.deleteAllCustomer();
	        	return new ResponseEntity<Object>("Customers are deleted",HttpStatus.OK);
	    }
	    
	    

}
