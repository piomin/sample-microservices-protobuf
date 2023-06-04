package pl.piomin.services.protobuf.account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import pl.piomin.services.protobuf.account.data.AccountRepository;
import pl.piomin.services.protobuf.account.model.AccountProto.Account;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    ProtobufHttpMessageConverter protobufHttpMessageConverter() {
        return new ProtobufHttpMessageConverter();
    }

    @Bean
    RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
        return new RestTemplate(Arrays.asList(hmc));
    }

    @Bean
    AccountRepository repository() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(Account.newBuilder().setId(1).setCustomerId(1).setNumber("111111").build());
        accounts.add(Account.newBuilder().setId(2).setCustomerId(2).setNumber("222222").build());
        accounts.add(Account.newBuilder().setId(3).setCustomerId(3).setNumber("333333").build());
        accounts.add(Account.newBuilder().setId(4).setCustomerId(4).setNumber("444444").build());
        accounts.add(Account.newBuilder().setId(5).setCustomerId(1).setNumber("555555").build());
        accounts.add(Account.newBuilder().setId(6).setCustomerId(2).setNumber("666666").build());
        accounts.add(Account.newBuilder().setId(7).setCustomerId(2).setNumber("777777").build());
        return new AccountRepository(accounts);
    }

}
