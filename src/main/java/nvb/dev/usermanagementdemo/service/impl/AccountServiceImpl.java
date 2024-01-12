package nvb.dev.usermanagementdemo.service.impl;

import lombok.AllArgsConstructor;
import nvb.dev.usermanagementdemo.exception.AccountNotFoundException;
import nvb.dev.usermanagementdemo.exception.UserNotFoundException;
import nvb.dev.usermanagementdemo.model.Account;
import nvb.dev.usermanagementdemo.model.User;
import nvb.dev.usermanagementdemo.repository.AccountRepository;
import nvb.dev.usermanagementdemo.repository.UserRepository;
import nvb.dev.usermanagementdemo.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public Account saveAccount(Account account, Long userId) {
        User user = unwrapUser(userRepository.findById(userId), userId);
        account.setUser(user);
        return accountRepository.save(account);
    }

    @Override
    public Account findAccountByUserId(Long userId) {
        Optional<Account> optionalAccount = accountRepository.findByUserId(userId);
        return unwrapAccount(optionalAccount, userId);
    }

    @Override
    public Account updateAccount(Account account, Long userId) {
        Optional<Account> optionalAccount = accountRepository.findByUserId(userId);
        Account existingAccount = unwrapAccount(optionalAccount, userId);
        existingAccount.setName(account.getName());
        return accountRepository.save(existingAccount);
    }

    @Override
    public List<Account> getUserAccounts(Long userId) {
        return accountRepository.findAccountByUserId(userId);
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccountByUserId(Long userId) {
        accountRepository.deleteByUserId(userId);
    }

    private static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }

    private static Account unwrapAccount(Optional<Account> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new AccountNotFoundException(id);
    }

}
