package mk.ukim.finki.finance.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import mk.ukim.finki.finance.model.AccountType;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private String name;
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    public AccountDto(String name, BigDecimal balance, AccountType type) {
        this.name = name;
        this.balance = balance;
        this.type = type;
    }

}
