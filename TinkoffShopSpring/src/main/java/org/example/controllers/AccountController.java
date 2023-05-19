package org.example.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.example.components.AccountComponent;
import org.example.entity.Account;
import org.example.entity.Order;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class AccountController {

    @Autowired
    AccountComponent accountComponent;

    @PostMapping("allAccounts")
    @Operation(summary = "Получение всех аккаунтов")
    public List<Account> getAllAccounts() { return accountComponent.getAllAccounts(); }


    @PostMapping("createAccount")
    @Operation(summary = "Добавление нового аккаунта")
    public Account createAccount(
            @RequestParam String userName,
            @RequestParam String userPhone) {
        return accountComponent.createAccount(userName,userPhone);
    }

    @DeleteMapping("deleteAccountById")
    @Operation(summary = "Удаление Аккаунта")
    public void deleteAccount(@RequestParam Long id) {
        accountComponent.deleteAccountById(id);
    }

    public void addBalanceByPhone(@RequestParam String phone,
                                  @RequestParam Double toAdd) {
        accountComponent.addBalanceByPhone(phone, toAdd);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> handleException(NoSuchElementException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }
}
