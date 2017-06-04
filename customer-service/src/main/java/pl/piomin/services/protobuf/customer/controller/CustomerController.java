package pl.piomin.services.protobuf.customer.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.piomin.services.protobuf.customer.contract.AccountClient;
import pl.piomin.services.protobuf.customer.data.CustomerRepository;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Accounts;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Customer;

@RestController
public class CustomerController {
	
	@Autowired
	CustomerRepository repository;
	@Autowired
	AccountClient accountClient;
	
	protected Logger logger = Logger.getLogger(CustomerController.class.getName());
	
	@RequestMapping("/customers/pesel/{pesel}")
	public Customer findByPesel(@PathVariable("pesel") String pesel) {
		logger.info(String.format("Customer.findByPesel(%s)", pesel));
		return repository.findByPesel(pesel);	
	}
	
	@RequestMapping("/customers")
	public List<Customer> findAll() {
		logger.info("Customer.findAll()");
		return repository.findAll();
	}
	
	@RequestMapping("/customers/{id}")
	public Customer findById(@PathVariable("id") Integer id) {
		logger.info(String.format("Customer.findById(%s)", id));
		Customer customer = repository.findById(id);
		Accounts accounts =  accountClient.getAccounts(id);
		customer.getAccountsList().addAll(accounts.getAccountList());
		return customer;
	}
	
}
