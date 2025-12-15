package pl.piomin.services.protobuf.customer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    protected Logger logger = LoggerFactory.getLogger(CustomerController.class.getName());

    @Autowired
    CustomerRepository repository;
    @Autowired
    AccountClient accountClient;

    public CustomerController(CustomerRepository repository, AccountClient accountClient) {
        this.repository = repository;
        this.accountClient = accountClient;
    }

    @RequestMapping(value = "/customers/pesel/{pesel}", produces = "application/x-protobuf")
    public Customer findByPesel(@PathVariable String pesel) {
        logger.info("Customer.findByPesel({})", pesel);
        return repository.findByPesel(pesel);
    }

    @RequestMapping(value = "/customers", produces = "application/x-protobuf")
    public Customers findAll() {
        logger.info("Customer.findAll()");
        return Customers.newBuilder().addAllCustomers(repository.findAll()).build();
    }

    @RequestMapping(value = "/customers/{id}", produces = "application/x-protobuf")
    public Customer findById(@PathVariable Integer id) {
        logger.info("Customer.findById({})", id);
        Customer customer = repository.findById(id);
        Accounts accounts = accountClient.getAccounts(id);
        customer = Customer.newBuilder(customer).addAllAccounts(accounts.getAccountList()).build();
        return customer;
    }

}
