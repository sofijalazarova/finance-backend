package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Budget;
import mk.ukim.finki.finance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {

    Budget findByStartDate(LocalDate startDate);

    Optional<Budget> findByStartDateAndUser(LocalDate startDate, User user);
}
