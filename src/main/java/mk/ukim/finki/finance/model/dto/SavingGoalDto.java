package mk.ukim.finki.finance.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SavingGoalDto {

    private String name;
    private BigDecimal targetAmount;
    private BigDecimal savedAmount;
    private LocalDate targetDate;

    public SavingGoalDto(String name, BigDecimal targetAmount, BigDecimal savedAmount, LocalDate targetDate) {
        this.name = name;
        this.targetAmount = targetAmount;
        this.savedAmount = savedAmount;
        this.targetDate = targetDate;
    }
}
