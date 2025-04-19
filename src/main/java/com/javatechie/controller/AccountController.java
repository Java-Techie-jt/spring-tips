package com.javatechie.controller;

import com.javatechie.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/transfer")
    public String transfer(@RequestParam Long from,
                           @RequestParam Long to,
                           @RequestParam double amount) {
        accountService.transfer(from, to, amount);
        return "Transfer successful!";
    }
}
