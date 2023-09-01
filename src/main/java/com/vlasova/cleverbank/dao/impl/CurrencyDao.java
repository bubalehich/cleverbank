package com.vlasova.cleverbank.dao.impl;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CurrencyDao extends AbstractCrudDao<Currency> implements DaoInterface<Long, Currency> {
    protected String findByIdQuery = "select * FROM currencies WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM currencies WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM currencies ORDER BY id LIMIT ? OFFSET ?";
    @Override
    protected Currency mapFromResultSet(ResultSet resultSet) throws SQLException {
        Currency currency = new Currency();
        currency.setId(resultSet.getLong("id"));
        currency.setName(resultSet.getString("name"));
        currency.setHandle(resultSet.getString("handle"));

        return currency;
    }

    @Override
    public Optional<Currency> save(Currency type) throws SQLException {
        throw new UnsupportedOperationException("This operation does not supports!");
    }

    @Override
    public boolean update(Currency currency) throws SQLException {
        throw new UnsupportedOperationException("This operation does not supports!");
    }
}
