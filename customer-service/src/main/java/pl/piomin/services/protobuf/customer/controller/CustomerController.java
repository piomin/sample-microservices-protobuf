package pl.piomin.services.protobuf.customer.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.protobuf.customer.contract.AccountClient;
import pl.piomin.services.protobuf.customer.data.CustomerRepository;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Accounts;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Customer;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Customers;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerRepository repository;
	@Autowired
	AccountClient accountClient;
	
	protected Logger logger = Logger.getLogger(CustomerController.class.getName());
	
	@RequestMapping(value = "/customers/pesel/{pesel}", produces = "application/x-protobuf")
	public Customer findByPesel(@PathVariable("pesel") String pesel) {
		logger.info(String.format("Customer.findByPesel(%s)", pesel));
		return repository.findByPesel(pesel);	
	}
	
	@RequestMapping(value = "/customers", produces = "application/x-protobuf")
	public Customers findAll() {
		logger.info("Customer.findAll()");
		return Customers.newBuilder().addAllCustomers(repository.findAll()).build();
	}
	
	@RequestMapping(value = "/customers/{id}", produces = "application/x-protobuf")
	public Customer findById(@PathVariable("id") Integer id) {
		logger.info(String.format("Customer.findById(%s)", id));
		Customer customer = repository.findById(id);
		Accounts accounts =  accountClient.getAccounts(id);		
		customer = Customer.newBuilder(customer).addAllAccounts(accounts.getAccountList()).build();
		return customer;
	}
	
}
