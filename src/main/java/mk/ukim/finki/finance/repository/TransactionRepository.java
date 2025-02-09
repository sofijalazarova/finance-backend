package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Transaction;
import mk.ukim.finki.finance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUser(User user);
}
