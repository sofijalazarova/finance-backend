package mk.ukim.finki.finance.controller;


import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Budget;
import mk.ukim.finki.finance.model.dto.BudgetUpdateRequest;
import mk.ukim.finki.finance.service.BudgetService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequestMapping("/api/budget")
@AllArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @GetMapping("/current-month-budget")
    public ResponseEntity<Budget> getCurrentMonthBudget(@AuthenticationPrincipal User user) {

        Budget budget =  this.budgetService.getOrCreateBudget(user);
        return ResponseEntity.ok(budget);
    }

    @PostMapping("/update")
    public ResponseEntity<Budget> updateBudget(@AuthenticationPrincipal User user, @RequestBody BudgetUpdateRequest request) {

        return ResponseEntity.ok(budgetService.updateBudget(request.getAmount(), user));
    }


}
