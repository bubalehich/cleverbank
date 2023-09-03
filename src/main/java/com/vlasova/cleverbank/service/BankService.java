package com.vlasova.cleverbank.service;


import com.vlasova.cleverbank.dao.impl.BankDao;
import com.vlasova.cleverbank.entity.Bank;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;

@ApplicationScoped
public class BankService {
    @Inject
    private BankDao bankDao;

    public Bank getById(long id) {
        return bankDao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Bank with id %d not found", id)));
    }

    public Bank create() {
        return null;
    }
}
