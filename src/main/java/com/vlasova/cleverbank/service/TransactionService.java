package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.dao.impl.transaction.TransactionDao;
import com.vlasova.cleverbank.entity.transaction.Transaction;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class TransactionService {
    private TransactionDao dao;

    public Transaction save(Transaction transaction) {
        return dao.save(transaction);
    }
}
