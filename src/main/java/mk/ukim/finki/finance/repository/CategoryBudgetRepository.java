package mk.ukim.finki.finance.repository;

import mk.ukim.finki.finance.model.Budget;
import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.model.CategoryBudget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryBudgetRepository extends JpaRepository<CategoryBudget, Long> {

    CategoryBudget findByBudgetAndCategory(Budget budget, Category category);

    CategoryBudget findByCategory(Category category);
}
