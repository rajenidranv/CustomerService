package com.hankerrank.sample;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hankerrank.Application;
import com.hankerrank.sample.model.Customer;



@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerTest {

	@Autowired
	private WebApplicationContext context;
	private Customer  customer;
	private MockMvc mvc;
	private String path="/tcs/hack/v1";

	
	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	
	@Test
	public void getCustomerInvalidTest() throws Exception {

		mvc.perform(MockMvcRequestBuilders.get(path+"/customer/1003").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(content().string("Customer not found"));
	}

	
	@Test
	public void addCustomerTest() throws Exception {
		
		customer = new Customer(Long.valueOf(1002),"Marie");
		
		MvcResult result = mvc.perform(MockMvcRequestBuilders.post(path+"/customer")
				.content(toJson(customer))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.customerId").isNumber()).andReturn();
		
		JSONObject json = new JSONObject(result.getResponse().getContentAsString()); 
		
		mvc.perform(MockMvcRequestBuilders.get(path+"/customer/"+json.get("customerId")).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty())
		.andExpect(jsonPath("$.customerId").value(json.get("customerId")))
		.andExpect(jsonPath("$.customerName").value("Marie"));
		
		mvc.perform(MockMvcRequestBuilders.get(path+"/customer").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$").isNotEmpty())
		.andExpect(jsonPath("$[0].customerId").isNumber())
		.andExpect(jsonPath("$[0].customerName").isString());
		
		mvc.perform(MockMvcRequestBuilders.post(path+"/customer")
				.content(toJson(customer))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
				.andExpect(content().string("Customer with same ID exists"));
	}
	
	@Test
	public void updateCustomerTest() throws Exception {

		customer = new Customer(Long.valueOf(1001),"Mertin");
		
		mvc.perform(MockMvcRequestBuilders.put(path+"/customer/1001").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(customer))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerId").isNumber()).andReturn();
		
		mvc.perform(MockMvcRequestBuilders.put(path+"/customer/1005").contentType(MediaType.APPLICATION_JSON)
				.content(toJson(customer))
	 			.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(content().string("No customer found to update"));
		
		mvc.perform(MockMvcRequestBuilders.delete(path+"/customer/1001").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string("Customer is deleted"));
		
		mvc.perform(MockMvcRequestBuilders.delete(path+"/customer/1002").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(content().string("Customer not found to be deleted"));
	}
	
	
	@Test
	public void deleteAllCustomerTest() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete(path+"/customer").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(content().string("Customers are deleted"));
		
		mvc.perform(MockMvcRequestBuilders.get(path+"/customer").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
		.andExpect(content().string("Customers not available"));
		
	}
	
	@Test
	public void main_test() {
	Application.main(new String[]{});
	}
	
	 private byte[] toJson(Object r) throws Exception {
	        ObjectMapper map = new ObjectMapper();
	        return map.writeValueAsString(r).getBytes();
	    }

}
