package pl.piomin.services.protobuf.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.web.context.WebApplicationContext;
import pl.piomin.services.protobuf.account.model.AccountProto.Account;
import pl.piomin.services.protobuf.account.model.AccountProto.Accounts;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountApplicationTest {

    protected Logger logger = LoggerFactory.getLogger(AccountApplicationTest.class);
    RestTestClient template;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        template = RestTestClient.bindToApplicationContext(context)
                .baseUrl("/accounts")
                .configureMessageConverters(clientBuilder -> clientBuilder.addCustomConverter(new ProtobufHttpMessageConverter()))
                .build();
    }

    @Test
    public void testFindByNumber() {
        template.get().uri("/{id}", "111111")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Account.class);
    }

    @Test
    public void testFindByCustomer() {
        template.get().uri("/customer/{customer}", "111111")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Accounts.class);
    }

    @Test
    public void testFindAll() {
        template.get().exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Accounts.class);
    }


//    @TestConfiguration
//    static class Config {
//
//        @Bean
//        public RestTemplateBuilder restTemplateBuilder() {
//            return new RestTemplateBuilder().additionalMessageConverters(new ProtobufHttpMessageConverter());
//        }
//
//    }

}
