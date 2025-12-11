package pl.piomin.services.protobuf.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Customer;
import pl.piomin.services.protobuf.customer.model.CustomerProto.Customers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerApplicationTest {

    RestTestClient template;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        template = RestTestClient.bindToApplicationContext(context)
                .baseUrl("/customers")
                .configureMessageConverters(clientBuilder -> clientBuilder.addCustomConverter(new ProtobufHttpMessageConverter()))
                .build();
    }

    //	@Test
    public void testFindById() {
        template.get().uri("/{id}", 1)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Customer.class);
    }

    @Test
    public void testFindByPesel() {
        template.get().uri("/pesel/{pesel}", "12346")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Customer.class);
    }

    @Test
    public void testFindAll() {
        template.get().exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Customers.class);
    }

}
