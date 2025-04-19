package com.javatechie.service;

import com.javatechie.entity.Account;
import com.javatechie.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // spring transaction : AOP
    // around advice : before me + after me
    // before me -> create or get the transaction
    // transfer() method invoke
    // after me (success) -> commit the transaction
    // after me (failure) -> rollback the transaction

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public String transfer(Long fromId, Long toId, double amount) {

        Account senderAccount = accountRepository.findById(fromId)
                .orElseThrow(() -> new RuntimeException("From Account not found"));

        Account receiverAccount = accountRepository.findById(toId)
                .orElseThrow(() -> new RuntimeException("To Account not found"));

        //deduct balance from sender
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        accountRepository.save(senderAccount);

        //simulating failure scenario
        forcefullyThrowingException();

        //add balance on receiver account
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        accountRepository.save(receiverAccount);
        return "Amount transferred successfully from " + fromId + " to" + toId;
    }



    public void forcefullyThrowingException() {
        throw new RuntimeException("receiver bank server is down ");
    }
}
