package mk.ukim.finki.finance.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import mk.ukim.finki.finance.model.TransactionType;

import java.math.BigDecimal;

@Data
public class TransactionDto {

    private String name;
    private String description;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Long account_id;
    private Long category_id;

    public TransactionDto(String name, String description, BigDecimal amount, TransactionType type, Long account_id, Long category_id) {
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.type = type;
        this.account_id = account_id;
        this.category_id = category_id;
    }
}
