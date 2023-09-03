package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.dao.impl.account.AccountDao;
import com.vlasova.cleverbank.entity.accounte.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class AccountService {
    @Inject
    private AccountDao accountDao;

    public Account getById(long id) {
        return accountDao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with id %d not found", id)));
    }

    public Account save(Account account) {
        if (account.isNew()) {
            return accountDao.save(account);
        }
        accountDao.update(account);
        return accountDao.findById(account.getId()).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with id %d not found", account.getId())));
    }
}
