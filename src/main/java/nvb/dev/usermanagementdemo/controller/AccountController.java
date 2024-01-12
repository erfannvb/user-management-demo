package nvb.dev.usermanagementdemo.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import nvb.dev.usermanagementdemo.model.Account;
import nvb.dev.usermanagementdemo.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1")
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/account/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> saveAccount(@RequestBody @Valid Account account, @PathVariable Long userId) {
        return new ResponseEntity<>(accountService.saveAccount(account, userId), HttpStatus.CREATED);
    }

//    @GetMapping(value = "/account/{userId}")
//    public ResponseEntity<Account> findAccountByUserId(@PathVariable Long userId) {
//        return new ResponseEntity<>(accountService.findAccountByUserId(userId), HttpStatus.OK);
//    }

    @PutMapping(value = "/account/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> updateAccount(@RequestBody @Valid Account account, @PathVariable Long userId) {
        return new ResponseEntity<>(accountService.updateAccount(account.getName(), userId), HttpStatus.OK);
    }

    @GetMapping(value = "/account/user/{userId}")
    public ResponseEntity<List<Account>> getUserAccounts(@PathVariable Long userId) {
        return new ResponseEntity<>(accountService.getUserAccounts(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/account/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return new ResponseEntity<>(accountService.getAllAccounts(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/account/user/{userId}")
    public ResponseEntity<HttpStatus> deleteAccountByUserId(@PathVariable Long userId) {
        accountService.deleteAccountByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
