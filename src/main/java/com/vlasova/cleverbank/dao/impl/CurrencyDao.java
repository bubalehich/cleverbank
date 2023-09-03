package com.vlasova.cleverbank.dao.impl;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.Currency;
import com.vlasova.cleverbank.exception.MappingException;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class CurrencyDao extends AbstractCrudDao<Currency> implements DaoInterface<Long, Currency> {
    protected String findByIdQuery = "select * FROM currencies WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM currencies WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM currencies ORDER BY id LIMIT ? OFFSET ?";

    @Override
    protected Currency mapFromResultSet(ResultSet resultSet) {
        try {
            Currency currency = new Currency();
            currency.setId(resultSet.getLong("id"));
            currency.setName(resultSet.getString("name"));
            currency.setHandle(resultSet.getString("handle"));

            return currency;
        } catch (SQLException e) {
            throw new MappingException(e);
        }
    }
}
