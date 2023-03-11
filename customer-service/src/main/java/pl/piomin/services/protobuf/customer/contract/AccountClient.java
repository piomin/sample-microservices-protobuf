package pl.piomin.services.protobuf.customer.contract;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.piomin.services.protobuf.customer.model.CustomerProto.Accounts;

@FeignClient(value = "account-service")
public interface AccountClient {

    @RequestMapping(method = RequestMethod.GET, value = "/accounts/customer/{customerId}")
    Accounts getAccounts(@PathVariable("customerId") Integer customerId);
    
}
