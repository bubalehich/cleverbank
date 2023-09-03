package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.dao.impl.transaction.TransactionTypeDao;
import com.vlasova.cleverbank.entity.transaction.TransactionType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

@RequiredArgsConstructor
@ApplicationScoped
public class TransactionTypeService {
    private TransactionTypeDao dao;

    public TransactionType getByName(String name) {
        return dao.findBy(Pair.of("name", name)).orElseThrow(()
                -> new EntityNotFoundException(String.format("Transaction type %s not found!", name)));
    }
}
