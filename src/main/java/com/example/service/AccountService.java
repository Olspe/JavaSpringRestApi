package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
public class AccountService {

    //Field
    AccountRepository accountRepository;

    //Constructor
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    /**
     * Adds an account object as record in account table
     * @param account Account to add to account table
     * @return Returns Account that was added (contains id)
     */
    public Account addAccount(Account account){
        if(account.getUsername() == "" || account.getPassword().length() < 4){
            return null;
        }
        
        // List<Account> accounts = accountRepository.findAll();
        // for(Account acc: accounts){
        //     if(acc.getUsername().equals(account.getUsername())){
        //         return null;
        //     }
        // }

        Account res =  accountRepository.findAccountByUsername(account.getUsername());
        if(res != null){ //if account already exists
            return null;
        }
        res = accountRepository.save(account);
        if(res != null){
            return res;
        }
        return null;

    }


    /**
     *  Accesses account in the account table
     * @param account Account to access in the account table
     * @return Returns Account that was accessed
     */
    public Account logAccount(Account account){
        List<Account> accounts = accountRepository.findAll();
        Account loggedAccount = null;

        for(Account acc: accounts){
            if(acc.getUsername().equals(account.getUsername()) && acc.getPassword().equals(account.getPassword())){
                loggedAccount = acc;
                break;
            }
        }

        return loggedAccount;
    }

    
}
