package mk.ukim.finki.finance.service;

import mk.ukim.finki.finance.model.Transaction;
import mk.ukim.finki.finance.model.dto.TransactionDto;
import mk.ukim.finki.finance.user.User;

import java.util.List;

public interface TransactionService {

    List<Transaction> findByUser(User user);
    Transaction save(TransactionDto transactionDto, User user);
    void delete(Long transactionId, User user);
}
