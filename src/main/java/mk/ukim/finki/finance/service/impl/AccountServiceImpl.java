package mk.ukim.finki.finance.service.impl;

import mk.ukim.finki.finance.model.Account;
import mk.ukim.finki.finance.service.AccountService;

import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }
}
