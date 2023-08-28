package pl.piomin.services.grpc.customer.service;

import com.google.protobuf.Empty;
import com.google.protobuf.Int32Value;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piomin.services.grpc.customer.client.AccountClient;
import pl.piomin.services.grpc.customer.model.CustomerProto;
import pl.piomin.services.grpc.customer.model.CustomersServiceGrpc;
import pl.piomin.services.grpc.customer.repository.CustomerRepository;

import java.util.List;

@GrpcService
public class CustomersService extends CustomersServiceGrpc.CustomersServiceImplBase {

    @Autowired
    CustomerRepository repository;
//    @Autowired
//    AccountClient accountClient;

    @Override
    public void findById(Int32Value request, StreamObserver<CustomerProto.Customer> responseObserver) {
        CustomerProto.Customer c = repository.findById(request.getValue());
//        CustomerProto.Accounts a = accountClient.getAccountsByCustomerId(c.getId());
//        c.getAccountsList().addAll(a.getAccountList());
        responseObserver.onNext(c);
        responseObserver.onCompleted();
    }

    @Override
    public void findByPesel(StringValue request, StreamObserver<CustomerProto.Customer> responseObserver) {
        CustomerProto.Customer c = repository.findByPesel(request.getValue());
        responseObserver.onNext(c);
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(Empty request, StreamObserver<CustomerProto.Customers> responseObserver) {
        List<CustomerProto.Customer> customerList = repository.findAll();
        CustomerProto.Customers c = CustomerProto.Customers.newBuilder().addAllCustomers(customerList).build();
        responseObserver.onNext(c);
        responseObserver.onCompleted();
    }

    @Override
    public void addCustomer(CustomerProto.Customer request, StreamObserver<CustomerProto.Customer> responseObserver) {
        CustomerProto.Customer c = repository.add(request.getType(), request.getName(), request.getPesel());
        responseObserver.onNext(c);
        responseObserver.onCompleted();
    }
}
