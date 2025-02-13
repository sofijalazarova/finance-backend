package mk.ukim.finki.finance.controller;


import lombok.AllArgsConstructor;
import mk.ukim.finki.finance.model.Account;
import mk.ukim.finki.finance.model.dto.AccountDto;
import mk.ukim.finki.finance.service.AccountService;
import mk.ukim.finki.finance.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(this.accountService.findAll());
    }

    @GetMapping("byUser")
    public ResponseEntity<List<Account>> getAllAccountByUserId(@AuthenticationPrincipal User user){
        try{
            return ResponseEntity.ok(this.accountService.findAllByUserId(user.getId()));
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("add")
    public ResponseEntity<Account> addAccount(@AuthenticationPrincipal User user, @RequestBody AccountDto accountDto){

        try {
           return this.accountService.save(accountDto, user)
                   .map(account -> ResponseEntity.ok().body(account))
                   .orElseGet(() -> ResponseEntity.badRequest().build());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("edit/{id}")
    public ResponseEntity<Account> editAccount(@AuthenticationPrincipal User user, @PathVariable Long id, @RequestBody AccountDto accountDto){
        try {
            return this.accountService.edit(id, accountDto, user)
                    .map(account -> ResponseEntity.ok().body(account))
                    .orElseGet(() -> ResponseEntity.badRequest().build());
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
