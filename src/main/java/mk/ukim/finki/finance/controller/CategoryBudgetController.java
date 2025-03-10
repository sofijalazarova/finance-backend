package mk.ukim.finki.finance.controller;


import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.CategoryBudget;
import mk.ukim.finki.finance.model.dto.AssignBudgetRequest;
import mk.ukim.finki.finance.repository.CategoryBudgetRepository;
import mk.ukim.finki.finance.service.BudgetService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-budgets")
@AllArgsConstructor
public class CategoryBudgetController {

    private final BudgetService budgetService;
    private final CategoryBudgetRepository categoryBudgetRepository;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryBudget>> findAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.categoryBudgetRepository.findAll());
    }
}
