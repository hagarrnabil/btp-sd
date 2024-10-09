package com.example.btpsd.controllers;
import com.example.btpsd.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    @Autowired
    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping
    public Map<String, Object> getAllUsers() {
        return accountsService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createUser(@RequestBody Map<String, Object> userPayload) {
        return accountsService.createUser(userPayload);
    }

    @PutMapping("/{userId}")
    public Map<String, Object> updateUser(@PathVariable String userId, @RequestBody Map<String, Object> userPayload) {
        return accountsService.updateUser(userId, userPayload);
    }

    @GetMapping("/{userId}")
    public Map<String, Object> getUserById(@PathVariable String userId) {
        return accountsService.getUserById(userId);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable String userId) {
        accountsService.deleteUser(userId);
    }
}


