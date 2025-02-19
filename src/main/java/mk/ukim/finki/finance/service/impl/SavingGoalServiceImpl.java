package mk.ukim.finki.finance.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.SavingGoal;
import mk.ukim.finki.finance.model.dto.SavingGoalDto;
import mk.ukim.finki.finance.repository.SavingGoalRepository;
import mk.ukim.finki.finance.service.SavingGoalService;
import mk.ukim.finki.finance.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SavingGoalServiceImpl implements SavingGoalService {

    private final SavingGoalRepository savingGoalRepository;

    @Override
    public List<SavingGoal> getUserSavingGoals(User user) {
        return this.savingGoalRepository.findByUser(user);
    }

    @Override
    public SavingGoal createSavingGoal(SavingGoalDto savingGoalDto, User user) {

        SavingGoal savingGoal = new SavingGoal();
        savingGoal.setUser(user);
        savingGoal.setName(savingGoalDto.getName());
        savingGoal.setTargetAmount(savingGoalDto.getTargetAmount());
        savingGoal.setSavedAmount(savingGoalDto.getSavedAmount());
        savingGoal.setTargetDate(savingGoalDto.getTargetDate());
        return savingGoalRepository.save(savingGoal);
    }

    @Override
    public SavingGoal updateSavedAmount(Long goalId, BigDecimal amount) {
        SavingGoal savingGoal = savingGoalRepository.findById(goalId).orElseThrow();
        savingGoal.setSavedAmount(savingGoal.getSavedAmount().add(amount));
        return savingGoalRepository.save(savingGoal);
    }
}
