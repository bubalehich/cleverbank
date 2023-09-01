package com.vlasova.cleverbank.dao.impl.account;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.Bank;
import com.vlasova.cleverbank.entity.accounte.Account;
import com.vlasova.cleverbank.entity.accounte.AccountType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountTypeDao extends AbstractCrudDao<AccountType> implements DaoInterface<Long, AccountType> {
    protected String findByIdQuery = "select * FROM account_type WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM account_type WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM account_type ORDER BY id LIMIT ? OFFSET ?";
    @Override
    public Optional<AccountType> save(AccountType type) throws SQLException {
        throw new UnsupportedOperationException("This operation does not supports!");
    }

    @Override
    public boolean update(AccountType accountType) throws SQLException {
        throw new UnsupportedOperationException("This operation does not supports!");
    }

    protected AccountType mapFromResultSet(ResultSet resultSet) throws SQLException {
        AccountType accountType = new AccountType();
        accountType.setId(resultSet.getLong("id"));
        accountType.setName(resultSet.getString("name"));

        return accountType;
    }
}
