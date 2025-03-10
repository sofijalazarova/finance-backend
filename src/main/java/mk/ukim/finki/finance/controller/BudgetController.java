package mk.ukim.finki.finance.controller;


import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Budget;
import mk.ukim.finki.finance.model.CategoryBudget;
import mk.ukim.finki.finance.model.dto.AssignBudgetRequest;
import mk.ukim.finki.finance.model.dto.BudgetUpdateRequest;
import mk.ukim.finki.finance.repository.CategoryBudgetRepository;
import mk.ukim.finki.finance.service.BudgetService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/budget")
@AllArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    private final CategoryBudgetRepository categoryBudgetRepository;

    @GetMapping("/byUser")
    public ResponseEntity<List<CategoryBudget>> findAllByUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.categoryBudgetRepository.findAllByCategory_User(user));
    }

    @GetMapping("/current-month-budget")
    public ResponseEntity<Budget> getCurrentMonthBudget(@AuthenticationPrincipal User user) {

        Budget budget =  this.budgetService.getBudget(user).orElseThrow();
        return ResponseEntity.ok(budget);
    }

    @PostMapping("/update")
    public ResponseEntity<Budget> updateBudget(@AuthenticationPrincipal User user, @RequestBody BudgetUpdateRequest request) {

        return ResponseEntity.ok(budgetService.updateBudget(request.getAmount(), user));
    }


    @PostMapping("/assign")
    public ResponseEntity<String> assignToCategory(@AuthenticationPrincipal User user, @RequestBody AssignBudgetRequest request) {

        this.budgetService.assignToCategory(request.getBudgetId(), request.getCategoryId(), request.getAmount());
        return ResponseEntity.ok("Assigned successfully");
    }

    @GetMapping("/budgetPercentage")
    public ResponseEntity<BigDecimal> getBudgetPercentage(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.budgetService.getBudgetChangePercentage(user));
    }


}
