package mk.ukim.finki.finance.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Budget;
import mk.ukim.finki.finance.repository.BudgetRepository;
import mk.ukim.finki.finance.service.BudgetService;
import mk.ukim.finki.finance.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final BudgetRepository budgetRepository;

    public Optional<Budget> findByMonth(Integer month) {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = LocalDate.of(today.getYear(), month, 1);
        return Optional.of(this.budgetRepository.findByStartDate(startOfMonth));
    }

    @Override
    public Budget getOrCreateBudget(User user) {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        return this.budgetRepository.findByStartDateAndUser(currentMonth, user)
                .orElseGet(() -> {
                    Budget budget = new Budget(currentMonth, BigDecimal.ZERO);
                    budget.setUser(user);
                    return budgetRepository.save(budget);
                });
    }

    @Override
    public Budget updateBudget(BigDecimal amount, User user) {
        Budget budget = getOrCreateBudget(user);
        budget.setTotalBudget(amount);
        return this.budgetRepository.save(budget);
    }
}
