import org.example.components.AccountComponent;
import org.example.repositories.AccountRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest extends AbstractTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountComponent accountComponent;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setup() {
        accountRepository.deleteAll();
    }


//void createAccount(String name, String phone) {
    @Test
    public void createAccount() {
        var userName = "Bibabooba";
        var userPhone = "88005553535";

        //test
        var account = accountComponent.createAccount(userName, userPhone);

        //assert
        var user = userRepository.findByPhone(userPhone);
        var newAccount = accountRepository.findByUserid(user.getId());
        var defaultBalance = 100.00;

        assertThat(newAccount.getId()).isEqualTo(account.getId());
        assertThat(newAccount.getBalance()).isEqualTo(defaultBalance);

        //post
        accountRepository.delete(account);

    }

    @Test
    public void addToBalance() {
        var userName = "Bibabooba";
        var userPhone = "88005553535";
        var account = accountComponent.createAccount(userName, userPhone);
        var toAdd = 150.00;
        var previousBalance = account.getBalance();

        //test
        accountComponent.addBalanceByPhone(userPhone, toAdd);

        var changedAccount = accountRepository.findByUserid(account.getUserid());
        //assert
        assertThat(changedAccount.getBalance()).isEqualTo((previousBalance + toAdd));

        //post
        accountRepository.delete(account);
    }
}
