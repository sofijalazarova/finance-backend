package mk.ukim.finki.finance.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.*;
import mk.ukim.finki.finance.model.dto.TransactionDto;
import mk.ukim.finki.finance.repository.AccountRepository;
import mk.ukim.finki.finance.repository.CategoryBudgetRepository;
import mk.ukim.finki.finance.repository.CategoryRepository;
import mk.ukim.finki.finance.repository.TransactionRepository;
import mk.ukim.finki.finance.service.TransactionService;
import mk.ukim.finki.finance.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;
    private final CategoryBudgetRepository categoryBudgetRepository;

    @Override
    public List<Transaction> findByUser(User user) {
        return this.transactionRepository.findAllByUser(user);
    }

    @Transactional
    @Override
    public Transaction save(TransactionDto transactionDto, User user) {

        Category category = null;

        if(transactionDto.getCategory_id() != null) {
            category = categoryRepository.findById(transactionDto.getCategory_id()).orElseThrow();
        }

        Account account = this.accountRepository.findById(transactionDto.getAccount_id()).orElseThrow();
        CategoryBudget categoryBudget = this.categoryBudgetRepository.findByCategory(category);


        if(transactionDto.getType() == TransactionType.EXPENSE && account.getBalance().compareTo(transactionDto.getAmount()) < 0) {
            throw new RuntimeException("Expense account balance is too small");
        }

        Transaction transaction = new Transaction(transactionDto.getName(), transactionDto.getDescription(), transactionDto.getAmount(), transactionDto.getType());
        transaction.setCategory(category);
        transaction.setUser(user);
        transaction.setAccount(account);

        if(transactionDto.getType() == TransactionType.EXPENSE) {
            account.setBalance(account.getBalance().subtract(transactionDto.getAmount()));

        }else if(transactionDto.getType() == TransactionType.INCOME) {
            account.setBalance(account.getBalance().add(transactionDto.getAmount()));
        }

        accountRepository.save(account);
        Transaction savedTransaction = transactionRepository.save(transaction);
        BigDecimal totalSpent = transactionRepository.getTotalSpentByCategory(category);

        if(transactionDto.getType() == TransactionType.EXPENSE) {
            categoryBudget.setAvailableAmount(categoryBudget.getAllocatedAmount().subtract(totalSpent));
            categoryBudget.setTotalSpent(totalSpent);
        }else {
            categoryBudget.setAllocatedAmount(categoryBudget.getAllocatedAmount().add(transactionDto.getAmount()));
            categoryBudget.setAvailableAmount(categoryBudget.getAvailableAmount().add(transactionDto.getAmount()));
        }

        categoryBudgetRepository.save(categoryBudget);

        System.out.println(categoryBudget.getAvailableAmount());
        return savedTransaction;
    }

    @Override
    public void delete(Long transactionId, User user) {

        this.transactionRepository.deleteById(transactionId);

    }
}
