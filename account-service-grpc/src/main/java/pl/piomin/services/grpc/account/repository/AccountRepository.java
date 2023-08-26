package pl.piomin.services.grpc.account.repository;


import pl.piomin.services.grpc.account.model.AccountProto;

import java.util.List;

public class AccountRepository {

    List<AccountProto.Account> accounts;

    public AccountRepository(List<AccountProto.Account> accounts) {
        this.accounts = accounts;
    }

    public List<AccountProto.Account> findAll() {
        return accounts;
    }

    public List<AccountProto.Account> findByCustomer(int customerId) {
        return accounts.stream().filter(it -> it.getCustomerId() == customerId).toList();
    }

    public AccountProto.Account findByNumber(String number) {
        return accounts.stream()
                .filter(it -> it.getNumber().equals(number))
                .findFirst()
                .orElseThrow();
    }

}
