package org.example.components;

import org.example.entity.Account;
import org.example.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;

@Component
public class AccountComponent {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserComponent userComponent;

    public List<Account> getAllAccounts() { return accountRepository.findAll(); }

    public Account createAccount(String userName, String userPhone) {
        var user = userComponent.getOrCreateUser(userName, userPhone);
        var account = new Account(user.getId());
        accountRepository.save(account);
        return account;
    }

    public Account getAccountByUser (String userPhone) {
        var user = userComponent.getUserByPhone(userPhone);
        if (user != null)
            return accountRepository.findByUserid(user.getId());
        throw new NoSuchElementException(
                String.format("Аккаунта с телефоном '%s' не существует", userPhone)
        );
    }

    public void addBalanceByPhone (String phone, Double toAdd) {
        var account = getAccountByUser(phone);
        account.setBalance(account.getBalance() + toAdd);
        accountRepository.save(account);
    }

    public void deleteAccountById (Long id) {
        accountRepository.deleteById(id);
    }


}
