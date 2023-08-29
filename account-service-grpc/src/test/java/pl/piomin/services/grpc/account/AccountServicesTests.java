package pl.piomin.services.grpc.account;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.piomin.services.grpc.account.model.AccountProto;
import pl.piomin.services.grpc.account.model.AccountsServiceGrpc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "grpc.server.inProcessName=test",
        "grpc.server.port=-1",
        "grpc.client.inProcess.address=in-process:test"
})
@DirtiesContext
public class AccountServicesTests {

    @GrpcClient("inProcess")
    AccountsServiceGrpc.AccountsServiceBlockingStub service;

    @Test
    void shouldFindAll() {
        AccountProto.Accounts a = service.findAll(Empty.newBuilder().build());
        assertNotNull(a);
        assertFalse(a.getAccountList().isEmpty());
    }

    @Test
    void shouldFindByCustomer() {
        AccountProto.Accounts a = service.findByCustomer(Int32Value.newBuilder().setValue(1).build());
        assertNotNull(a);
        assertFalse(a.getAccountList().isEmpty());
    }

    @Test
    void shouldFindByNumber() {
        AccountProto.Account a = service.findByNumber(StringValue.newBuilder().setValue("111111").build());
        assertNotNull(a);
        assertNotEquals(0, a.getId());
    }

    @Test
    void shouldAddAccount() {
        AccountProto.Account a = AccountProto.Account.newBuilder()
                .setNumber("123456")
                .setCustomerId(10)
                .build();

        a = service.addAccount(a);
        assertNotNull(a);
        assertNotEquals(0, a.getId());
    }

}
