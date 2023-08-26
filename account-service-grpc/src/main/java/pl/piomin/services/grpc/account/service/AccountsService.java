package pl.piomin.services.grpc.account.service;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piomin.services.grpc.account.model.AccountProto;
import pl.piomin.services.grpc.account.model.AccountsServiceGrpc;
import pl.piomin.services.grpc.account.repository.AccountRepository;

@GrpcService
public class AccountsService extends AccountsServiceGrpc.AccountsServiceImplBase {

    @Autowired
    AccountRepository repository;

    @Override
    public void findByNumber(AccountProto.FindByNumberRequest request, StreamObserver<AccountProto.Account> responseObserver) {
        AccountProto.Account a = repository.findByNumber(request.getNumber());
        responseObserver.onNext(a);
        responseObserver.onCompleted();
    }
}
