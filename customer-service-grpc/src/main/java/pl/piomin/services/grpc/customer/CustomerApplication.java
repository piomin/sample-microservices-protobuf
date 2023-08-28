package pl.piomin.services.grpc.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.grpc.customer.model.CustomerProto;
import pl.piomin.services.grpc.customer.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableDiscoveryClient
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    CustomerRepository repository() {
        List<CustomerProto.Customer> customers = new ArrayList<>();
        customers.add(CustomerProto.Customer.newBuilder().setId(1).setPesel("12345").setName("Adam Kowalski")
                .setType(CustomerProto.Customer.CustomerType.INDIVIDUAL).build());
        customers.add(CustomerProto.Customer.newBuilder().setId(2).setPesel("12346").setName("Anna Malinowska")
                .setType(CustomerProto.Customer.CustomerType.INDIVIDUAL).build());
        customers.add(CustomerProto.Customer.newBuilder().setId(3).setPesel("12347").setName("Paweł Michalski")
                .setType(CustomerProto.Customer.CustomerType.INDIVIDUAL).build());
        customers.add(CustomerProto.Customer.newBuilder().setId(4).setPesel("12348").setName("Karolina Lewandowska")
                .setType(CustomerProto.Customer.CustomerType.INDIVIDUAL).build());
        return new CustomerRepository(customers);
    }
}
