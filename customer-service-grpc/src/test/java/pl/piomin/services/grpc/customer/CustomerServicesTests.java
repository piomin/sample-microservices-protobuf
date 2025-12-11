package pl.piomin.services.grpc.customer;

import com.google.protobuf.Empty;
import com.google.protobuf.StringValue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.grpc.test.autoconfigure.AutoConfigureInProcessTransport;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.test.annotation.DirtiesContext;
import pl.piomin.services.grpc.customer.model.CustomerProto;
import pl.piomin.services.grpc.customer.model.CustomersServiceGrpc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureInProcessTransport
@DirtiesContext
public class CustomerServicesTests {

    @Autowired
    CustomersServiceGrpc.CustomersServiceBlockingStub service;

    @Test
    void shouldFindAll() {
        CustomerProto.Customers c = service.findAll(Empty.newBuilder().build());
        assertNotNull(c);
        assertFalse(c.getCustomersList().isEmpty());
    }

    @Test
    void shouldFindByPesel() {
        CustomerProto.Customer c = service.findByPesel(StringValue.newBuilder().setValue("12345").build());
        assertNotNull(c);
        assertNotEquals(0, c.getId());
    }

    @TestConfiguration
    static class Config {

        @Bean
        CustomersServiceGrpc.CustomersServiceBlockingStub stub(GrpcChannelFactory channels) {
            return CustomersServiceGrpc.newBlockingStub(channels.createChannel("local"));
        }

    }

}
