package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.AccountInfo;
import com.example.account.dto.DeleteAccount;
import com.example.account.service.AccountService;
import com.example.account.service.RedisTestService;
import com.example.account.dto.CreateAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {

    //외부에서는 컨트롤러만 접속
    //컨트롤러는 service만 접속
    //service 는 Repository로 접속
    private final AccountService accountService;
    private final RedisTestService redisTestService;

    @PostMapping("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request
    ) {

        return CreateAccount.Response.from(accountService.createAccount(
                request.getUserId(),
                request.getInitialBalance()));
    }

    @DeleteMapping("/account")
    public DeleteAccount.Response DeleteAccount(
            @RequestBody @Valid DeleteAccount.Request request
    ) {

        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()));
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
            @RequestParam("user_id") Long userId
    ) {
        return accountService.getAccountByUserId(userId)
                .stream().map(accountDto -> AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());

    }

    @GetMapping("/get-lock")
    public String getLock() {
        return redisTestService.getLock();
    }


    @GetMapping("/account/{id}")
    public Account getAccount(
            @PathVariable Long id) {
        return accountService.getAccount(id);
    }


}
