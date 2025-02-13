package mk.ukim.finki.finance.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Account;
import mk.ukim.finki.finance.model.dto.AccountDto;
import mk.ukim.finki.finance.repository.AccountRepository;
import mk.ukim.finki.finance.repository.UserRepository;
import mk.ukim.finki.finance.service.AccountService;
import mk.ukim.finki.finance.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public List<Account> findAll() {
        return this.accountRepository.findAll();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Account> findAllByUserId(Long id) {
        User user = this.userRepository.findById(id).orElseThrow();
        return this.accountRepository.findAllByUser(user);
    }

    @Override
    public Optional<Account> save(AccountDto accountDto, User user) {
        Account account = new Account();
        account.setName(accountDto.getName());
        account.setBalance(accountDto.getBalance());
        account.setType(accountDto.getType());
        account.setUser(user);
        return Optional.of(this.accountRepository.save(account));
    }

    @Override
    public Optional<Account> edit(Long id, AccountDto accountDto, User user) {
        Account account = this.accountRepository.findById(id).orElseThrow();
        account.setName(accountDto.getName());
        account.setBalance(accountDto.getBalance());
        account.setType(accountDto.getType());
        account.setUser(user);
        return Optional.of(this.accountRepository.save(account));
    }
}
