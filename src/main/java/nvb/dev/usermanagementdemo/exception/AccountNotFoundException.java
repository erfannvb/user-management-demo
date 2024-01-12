package nvb.dev.usermanagementdemo.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(Long accountId) {
        super(String.format("Account with id %d does not exist.", accountId));
    }

}
