package mk.ukim.finki.finance.controller;

import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Transaction;
import mk.ukim.finki.finance.model.dto.TransactionDto;
import mk.ukim.finki.finance.service.TransactionService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@AllArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(this.transactionService.findByUser(user));
    }

    @PostMapping("add")
    public ResponseEntity<Transaction> addTransaction(@RequestBody TransactionDto transactionDto, @AuthenticationPrincipal User user) {
        try{
            System.out.println(transactionDto.toString());
            return ResponseEntity.ok(this.transactionService.save(transactionDto, user));
        }catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
