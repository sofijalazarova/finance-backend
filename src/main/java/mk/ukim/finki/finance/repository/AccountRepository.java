package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Account;
import mk.ukim.finki.finance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findAllByUser(User user);
}
