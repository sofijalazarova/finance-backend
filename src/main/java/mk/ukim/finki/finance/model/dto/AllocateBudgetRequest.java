package mk.ukim.finki.finance.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllocateBudgetRequest {

    private BigDecimal amount;
    private Long categoryId;
    private Long budgetId;

    public AllocateBudgetRequest(BigDecimal amount, Long categoryId, Long budgetId) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.budgetId = budgetId;
    }
}
