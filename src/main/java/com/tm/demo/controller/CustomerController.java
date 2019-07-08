package com.tm.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tm.demo.Model.Customer;
import com.tm.demo.Repository.CustomerRepository;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerRepository repository;
	
	@RequestMapping(method = RequestMethod.POST, value = "/")
	public Customer addNewCustomer(@Valid @RequestBody Customer customer) {
		repository.save(customer);
		return customer;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/multiple/")
	public List<Customer> addMultipleCustomers(@Valid @RequestBody List<Customer> customers) {
		for (Customer customer : customers) {
			repository.save(customer);
		}
		return customers;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/")
	public List<Customer> getAllCustomers() {
		return repository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{firstName}")
	public Customer getCustomerByFirstName(@PathVariable("firstName") String firstName) {
		return repository.findByFirstName(firstName);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{firstName}/{modifiedName}")
	public Customer updateExistingCustomer(@PathVariable("firstName") String firstName, @PathVariable("modifiedName") String modifiedName) {
		Customer customer = repository.findByFirstName(firstName);
		customer.setFirstName(modifiedName);
		repository.save(customer);
		return customer;
	}
	
	@RequestMapping(value = "/{firstName}", method = RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable("firstName") String firstName) {
		repository.delete(repository.findByFirstName(firstName));
	}
	

}
