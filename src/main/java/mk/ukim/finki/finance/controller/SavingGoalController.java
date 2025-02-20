package mk.ukim.finki.finance.controller;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.SavingGoal;
import mk.ukim.finki.finance.model.dto.SavingGoalDto;
import mk.ukim.finki.finance.service.SavingGoalService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/savings")
@AllArgsConstructor
public class SavingGoalController {

    private final SavingGoalService savingGoalService;

    @GetMapping
    public ResponseEntity<List<SavingGoal>> getUserSavingGoals(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(savingGoalService.getUserSavingGoals(user));
    }

    @PostMapping
    public ResponseEntity<SavingGoal> createSavinGoal(@RequestBody SavingGoalDto savingGoalDto, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.savingGoalService.createSavingGoal(savingGoalDto, user));
    }

    @PutMapping("/{goalId}/save")
    public ResponseEntity<SavingGoal> updateSavedAmount(@PathVariable Long goalId, @RequestParam BigDecimal amount, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(savingGoalService.updateSavedAmount(goalId, amount));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteSavingGoal(@AuthenticationPrincipal User user, @PathVariable Long id) {
        this.savingGoalService.deleteSavingGoal(id, user);
        return ResponseEntity.ok("Deleted");
    }
}
