package pl.piomin.services.grpc.account.service;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;
import pl.piomin.services.grpc.account.model.AccountProto;
import pl.piomin.services.grpc.account.model.AccountsServiceGrpc;
import pl.piomin.services.grpc.account.repository.AccountRepository;

import java.util.List;

@GrpcService
public class AccountsService extends AccountsServiceGrpc.AccountsServiceImplBase {

    AccountRepository repository;

    public AccountsService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void findByNumber(StringValue request, StreamObserver<AccountProto.Account> responseObserver) {
        AccountProto.Account a = repository.findByNumber(request.getValue());
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void findByCustomer(Int32Value request, StreamObserver<AccountProto.Accounts> responseObserver) {
        List<AccountProto.Account> accounts = repository.findByCustomer(request.getValue());
        AccountProto.Accounts a = AccountProto.Accounts.newBuilder().addAllAccount(accounts).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Empty request, StreamObserver<AccountProto.Accounts> responseObserver) {
        List<AccountProto.Account> accounts = repository.findAll();
        AccountProto.Accounts a = AccountProto.Accounts.newBuilder().addAllAccount(accounts).build();
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }

    @Override
    public void addAccount(AccountProto.Account request, StreamObserver<AccountProto.Account> responseObserver) {
        AccountProto.Account a = repository.add(request.getCustomerId(), request.getNumber());
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }
}
