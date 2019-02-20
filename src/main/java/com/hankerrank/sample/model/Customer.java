package com.hankerrank.sample.model;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "customer")
public class Customer {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long customerId;
	 
	 	@Column(nullable=false)
	    @Size(max = 50)
	    private String customerName;
	    
	    private Long contactNo;
	    
	    private String address;
	    
	    private String gender;

		
		public Customer () {
		    
		}
	    
		public Customer(Long customerId, String customerName) {
			super();
			this.customerId = customerId;
			this.customerName = customerName;
		}
		
		public Long getCustomerId() {
			return customerId;
		}

		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}

		public String getCustomerName() {
			return customerName;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public Long getContactNo() {
			return contactNo;
		}

		public void setContactNo(Long contactNo) {
			this.contactNo = contactNo;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

	    
	    
}
