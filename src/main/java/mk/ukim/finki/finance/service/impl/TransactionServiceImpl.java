package mk.ukim.finki.finance.service.impl;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Account;
import mk.ukim.finki.finance.model.Category;
import mk.ukim.finki.finance.model.Transaction;
import mk.ukim.finki.finance.model.dto.TransactionDto;
import mk.ukim.finki.finance.repository.AccountRepository;
import mk.ukim.finki.finance.repository.CategoryRepository;
import mk.ukim.finki.finance.repository.TransactionRepository;
import mk.ukim.finki.finance.service.TransactionService;
import mk.ukim.finki.finance.user.User;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

    @Override
    public Transaction save(TransactionDto transactionDto, User user) {

        Category category = this.categoryRepository.findById(transactionDto.getCategory_id()).orElseThrow();
        Account account = this.accountRepository.findById(transactionDto.getAccount_id()).orElseThrow();
        Transaction transaction = new Transaction(transactionDto.getName(), transactionDto.getDescription(), transactionDto.getAmount(), transactionDto.getType());
        transaction.setCategory(category);
        transaction.setUser(user);
        transaction.setAccount(account);
        return this.transactionRepository.save(transaction);
    }
}
