package mk.ukim.finki.finance.service;

import mk.ukim.finki.finance.model.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findById(Long id);
}
