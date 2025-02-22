package mk.ukim.finki.finance.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Budget;
import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.model.CategoryBudget;
import mk.ukim.finki.finance.repository.BudgetRepository;
import mk.ukim.finki.finance.repository.CategoryBudgetRepository;
import mk.ukim.finki.finance.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final CategoryBudgetRepository categoryBudgetRepository;

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

    @Override
    public void allocateToCategory(Long budgetId, Long categoryId, BigDecimal amount) {
        Budget budget = this.budgetRepository.findById(budgetId).orElseThrow(() -> new IllegalArgumentException("Budget not found"));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found"));

        CategoryBudget categoryBudget = this.categoryBudgetRepository.findByBudgetAndCategory(budget, category);

        if(categoryBudget != null) {
            budget.setTotalBudget(budget.getTotalBudget().subtract(amount.subtract(categoryBudget.getAllocatedAmount())));
            categoryBudget.setAllocatedAmount(amount);
            categoryBudget.setAvailableAmount(amount.subtract(categoryBudget.getTotalSpent()));

        }else {
            categoryBudget = new CategoryBudget();

//            if(amount.compareTo(budget.getTotalBudget()) > 0) {
//                throw new IllegalArgumentException("Not enough budget");
//            }

            categoryBudget.setAllocatedAmount(amount);
            categoryBudget.setAvailableAmount(amount.subtract(categoryBudget.getTotalSpent()));
            categoryBudget.setBudget(budget);
            categoryBudget.setCategory(category);
            budget.setTotalBudget(budget.getTotalBudget().subtract(amount));
        }

        categoryBudgetRepository.save(categoryBudget);
        budgetRepository.save(budget);
    }



}
