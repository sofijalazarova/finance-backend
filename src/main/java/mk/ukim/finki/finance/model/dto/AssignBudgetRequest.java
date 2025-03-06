package mk.ukim.finki.finance.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AssignBudgetRequest {

    private BigDecimal amount;
    private Long categoryId;
    private Long budgetId;

    public AssignBudgetRequest(BigDecimal amount, Long categoryId, Long budgetId) {
        this.amount = amount;
        this.categoryId = categoryId;
        this.budgetId = budgetId;
    }
}
