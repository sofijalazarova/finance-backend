package mk.ukim.finki.finance.service;

import mk.ukim.finki.finance.model.Transaction;
import mk.ukim.finki.finance.model.dto.TransactionDto;
import mk.ukim.finki.finance.user.User;

public interface TransactionService {

    public Transaction save(TransactionDto transactionDto, User user);
}
