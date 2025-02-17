package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.model.Transaction;
import mk.ukim.finki.finance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByUser(User user);

    @Query("SELECT COALESCE(SUM(t.amount), 0) FROM Transaction t WHERE  t.category = :category AND t.type = 'EXPENSE'")
    BigDecimal getTotalSpentByCategory(@Param("category") Category category);
}
