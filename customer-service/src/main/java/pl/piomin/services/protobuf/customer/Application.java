package pl.piomin.services.protobuf.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import pl.piomin.services.protobuf.customer.data.CustomerRepository;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Customer;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Customer.CustomerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufHttpMessageConverter();
	}

	@Bean
	RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
		return new RestTemplate(Arrays.asList(hmc));
	}

	@Bean
	CustomerRepository repository() {
		List<Customer> customers = new ArrayList<>();
		customers.add(Customer.newBuilder().setId(1).setPesel("12345").setName("Adam Kowalski")
				.setType(CustomerType.INDIVIDUAL).build());
		customers.add(Customer.newBuilder().setId(2).setPesel("12346").setName("Anna Malinowska")
				.setType(CustomerType.INDIVIDUAL).build());
		customers.add(Customer.newBuilder().setId(3).setPesel("12347").setName("Paweł Michalski")
				.setType(CustomerType.INDIVIDUAL).build());
		customers.add(Customer.newBuilder().setId(4).setPesel("12348").setName("Karolina Lewandowska")
				.setType(CustomerType.INDIVIDUAL).build());
		return new CustomerRepository(customers);
	}

}
