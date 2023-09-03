package com.vlasova.cleverbank.service;

import com.vlasova.cleverbank.dao.impl.CurrencyDao;
import com.vlasova.cleverbank.entity.Currency;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CurrencyService {
    private CurrencyDao currencyDao;

    public Currency getById(long id) {
        return currencyDao.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("Currency with id %d does not exists.", id)));
    }
}
