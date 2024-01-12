package nvb.dev.usermanagementdemo.repository;

import nvb.dev.usermanagementdemo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserId(Long userId);

    @Transactional
    void deleteByUserId(Long userId);

    List<Account> findAccountByUserId(Long userId);

}
