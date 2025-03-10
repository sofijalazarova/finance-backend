package mk.ukim.finki.finance.service;

import mk.ukim.finki.finance.model.Budget;
import mk.ukim.finki.finance.user.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface BudgetService {

    Optional<Budget> findByMonth(Integer month);

    Budget getOrCreateBudget(User user);
    Optional<Budget> getBudget(User user);
    Budget createBudget(User user);

    Budget updateBudget(BigDecimal amount, User user);

    void assignToCategory(Long budgetId, Long categoryId, BigDecimal amount);

    BigDecimal getBudgetChangePercentage(User user);
}
