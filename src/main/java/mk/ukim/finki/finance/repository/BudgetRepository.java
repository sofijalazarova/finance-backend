package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
