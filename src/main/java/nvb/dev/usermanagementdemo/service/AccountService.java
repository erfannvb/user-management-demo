package nvb.dev.usermanagementdemo.service;

import nvb.dev.usermanagementdemo.model.Account;

import java.util.List;

public interface AccountService {

    Account saveAccount(Account account, Long userId);

    Account findAccountByUserId(Long userId);

    Account updateAccount(Account account, Long userId);

    List<Account> getUserAccounts(Long userId);

    List<Account> getAllAccounts();

    void deleteAccountByUserId(Long userId);

}
