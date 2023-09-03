package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.dao.impl.account.AccountTypeDao;
import com.vlasova.cleverbank.entity.accounte.AccountType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class AccountTypeService {
    private AccountTypeDao dao;

    public AccountType getById(Long id) {
        return dao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Account with %d id not found", id)));
    }
}
