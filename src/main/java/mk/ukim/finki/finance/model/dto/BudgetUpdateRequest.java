package mk.ukim.finki.finance.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BudgetUpdateRequest {

    private BigDecimal amount;

    public BudgetUpdateRequest(BigDecimal amount) {
        this.amount = amount;
    }
}
