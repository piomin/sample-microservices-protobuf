package pl.piomin.services.grpc.customer.repository;


import pl.piomin.services.grpc.customer.model.CustomerProto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CustomerRepository {

    private List<CustomerProto.Customer> customers;
    AtomicInteger id;

    public CustomerRepository(List<CustomerProto.Customer> customers) {
        this.customers = new CopyOnWriteArrayList<>(customers);
        this.id = new AtomicInteger();
        this.id.set(customers.size());
    }

    public CustomerProto.Customer findById(int id) {
        return customers.stream().filter(it -> it.getId() == id).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Customer with id " + id + " not found"));
    }

    public CustomerProto.Customer findByPesel(String pesel) {
        return customers.stream().filter(it -> it.getPesel().equals(pesel)).findFirst()
                .orElseThrow(() -> new NoSuchElementException("Customer with PESEL " + pesel + " not found"));
    }

    public List<CustomerProto.Customer> findAll() {
        return customers;
    }

    public CustomerProto.Customer add(CustomerProto.Customer.CustomerType type, String name, String pesel) {
        CustomerProto.Customer c = CustomerProto.Customer.newBuilder()
                .setId(id.incrementAndGet())
                .setType(type)
                .setName(name)
                .setPesel(pesel)
                .build();
        customers.add(c);
        return c;
    }
}
