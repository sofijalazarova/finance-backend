package mk.ukim.finki.finance.service;

import mk.ukim.finki.finance.model.Account;
import mk.ukim.finki.finance.model.dto.AccountDto;
import mk.ukim.finki.finance.user.User;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();
    Optional<Account> findById(Long id);
    List<Account> findAllByUserId(Long id);
    Optional<Account> save(AccountDto accountDto, User user);
    Optional<Account> edit(Long id, AccountDto accountDto, User user);
}
