package mk.ukim.finki.finance.service;

import mk.ukim.finki.finance.model.SavingGoal;
import mk.ukim.finki.finance.model.dto.SavingGoalDto;
import mk.ukim.finki.finance.user.User;

import java.math.BigDecimal;
import java.util.List;

public interface SavingGoalService {

    List<SavingGoal> getUserSavingGoals(User user);
    SavingGoal createSavingGoal(SavingGoalDto savingGoalDto, User user);
    SavingGoal updateSavedAmount(Long goalId, BigDecimal amount);
}
