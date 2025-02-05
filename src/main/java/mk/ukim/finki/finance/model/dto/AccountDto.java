package mk.ukim.finki.finance.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private String name;
    private BigDecimal balance;

    public AccountDto(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
    }

}
