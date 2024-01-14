package nvb.dev.usermanagementdemo.service;

import nvb.dev.usermanagementdemo.exception.EntityNotFoundException;
import nvb.dev.usermanagementdemo.exception.NoDataFoundException;
import nvb.dev.usermanagementdemo.model.Account;
import nvb.dev.usermanagementdemo.model.User;
import nvb.dev.usermanagementdemo.repository.AccountRepository;
import nvb.dev.usermanagementdemo.repository.UserRepository;
import nvb.dev.usermanagementdemo.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class AccountServiceTest {

    @Mock
    AccountRepository accountRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AccountServiceImpl accountService;

    @Test
    void saveAccountTest_ExistingUser() {
        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setName("test");
        account.setUser(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account savedAccount = accountService.saveAccount(account, user.getId());

        assertEquals(1L, savedAccount.getUser().getId());

        verify(userRepository, atLeastOnce()).findById(user.getId());
        verify(accountRepository, atLeastOnce()).save(any(Account.class));
    }

    @Test
    void saveAccountTest_NonExistingUser() {
        User user = new User();
        user.setId(1L);
        Account account = new Account();

        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.saveAccount(account, user.getId()));

        verify(userRepository, atLeastOnce()).findById(user.getId());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void findAccountByUserIdTest_ExistingAccount() {
        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setName("test");
        account.setUser(user);

        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.of(account));

        Account foundAccount = accountService.findAccountByUserId(user.getId());

        assertEquals(1L, foundAccount.getUser().getId());

        verify(accountRepository, atLeastOnce()).findByUserId(user.getId());
    }

    @Test
    void findAccountByUserIdTest_NonExistingAccount() {
        User user = new User();
        user.setId(1L);

        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.findAccountByUserId(user.getId()));

        verify(accountRepository, atLeastOnce()).findByUserId(user.getId());
    }

    @Test
    void updateAccountTest_ExistingAccount() {
        User user = new User();
        user.setId(1L);

        Account account = new Account();
        account.setId(1L);
        account.setName("test");
        account.setUser(user);

        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account updatedAccount = accountService.updateAccount("anotherTest", user.getId());

        assertEquals(1L, updatedAccount.getUser().getId());

        verify(accountRepository, atLeastOnce()).findByUserId(user.getId());
        verify(accountRepository, atLeastOnce()).save(any(Account.class));
    }

    @Test
    void updateAccountTest_NonExistingAccount() {
        User user = new User();
        user.setId(1L);

        when(accountRepository.findByUserId(user.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                accountService.updateAccount(anyString(), user.getId()));

        verify(accountRepository, atLeastOnce()).findByUserId(user.getId());
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void getUserAccountsTest() {
        User user = new User();
        user.setId(1L);

        List<Account> accountList = List.of(new Account(), new Account());

        when(accountRepository.findAccountByUserId(user.getId())).thenReturn(accountList);

        List<Account> accounts = accountService.getUserAccounts(user.getId());

        assertEquals(2, accounts.size());
        verify(accountRepository, atLeastOnce()).findAccountByUserId(user.getId());
    }

    @Test
    void getAllAccountsTest_ExistingAccounts() {
        List<Account> accountList = List.of(new Account(), new Account());

        when(accountRepository.findAll()).thenReturn(accountList);

        List<Account> allAccounts = accountService.getAllAccounts();

        assertEquals(2, allAccounts.size());
        verify(accountRepository, atLeastOnce()).findAll();
    }

    @Test
    void getAllAccountsTest_NonExistingAccounts() {
        when(accountRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> accountService.getAllAccounts());
        verify(accountRepository, atLeastOnce()).findAll();
    }

    @Test
    void deleteAccountByUserIdTest_ExistingUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        accountService.deleteAccountByUserId(1L);

        verify(userRepository, atLeastOnce()).findById(user.getId());
        verify(accountRepository, atLeastOnce()).deleteByUserId(user.getId());
    }

    @Test
    void deleteAccountByUserIdTest_NonExistingUser() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.deleteAccountByUserId(userId));

        verify(userRepository, atLeastOnce()).findById(userId);
        verify(accountRepository, never()).deleteByUserId(userId);
    }
}