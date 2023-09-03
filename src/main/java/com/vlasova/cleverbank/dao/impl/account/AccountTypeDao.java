package com.vlasova.cleverbank.dao.impl.account;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.accounte.AccountType;
import com.vlasova.cleverbank.exception.MappingException;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class AccountTypeDao extends AbstractCrudDao<AccountType> implements DaoInterface<Long, AccountType> {
    protected String findByIdQuery = "select * FROM account_type WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM account_type WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM account_type ORDER BY id LIMIT ? OFFSET ?";

    protected AccountType mapFromResultSet(ResultSet resultSet) {
        try {
            AccountType accountType = new AccountType();
            accountType.setId(resultSet.getLong("id"));
            accountType.setName(resultSet.getString("name"));

            return accountType;
        } catch (SQLException e) {
            throw new MappingException(e);
        }
    }
}
