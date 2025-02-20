package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.SavingGoal;
import mk.ukim.finki.finance.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {

    List<SavingGoal> findByUser(User user);

    List<SavingGoal> findByUserOrderByCreatedAtDesc(User user);

}
