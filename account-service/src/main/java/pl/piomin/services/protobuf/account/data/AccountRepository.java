package pl.piomin.services.protobuf.account.data;

import java.util.List;
import java.util.stream.Collectors;

import pl.piomin.services.protobuf.account.model.AccountProto.Account;

public class AccountRepository {

    List<Account> accounts;

    public AccountRepository(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Account> findAll() {
        return accounts;
    }

    public List<Account> findByCustomer(int customerId) {
        return accounts.stream().filter(it -> it.getCustomerId() == customerId).toList();
    }

    public Account findByNumber(String number) {
        return accounts.stream()
                .filter(it -> it.getNumber().equals(number))
                .findFirst()
                .orElseThrow();
    }

}
