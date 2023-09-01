package com.vlasova.cleverbank.dao.impl.transaction;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.accounte.AccountType;
import com.vlasova.cleverbank.entity.transaction.TransactionType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TransactionTypeDao extends AbstractCrudDao<TransactionType> implements DaoInterface<Long, TransactionType> {
    protected String findByIdQuery = "select * FROM transaction_types WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM transaction_types WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM transaction_types ORDER BY id LIMIT ? OFFSET ?";
    @Override
    protected TransactionType mapFromResultSet(ResultSet resultSet) throws SQLException {
        TransactionType transactionType = new TransactionType();
        transactionType.setId(resultSet.getLong("id"));
        transactionType.setName(resultSet.getString("name"));

        return transactionType;
    }

    @Override
    public Optional<TransactionType> save(TransactionType type) throws SQLException {
        throw new UnsupportedOperationException("This operation does not supports!");
    }//TODO replace unsupp methods to parent class 

    @Override
    public boolean update(TransactionType transactionType) throws SQLException {
        throw new UnsupportedOperationException("This operation does not supports!");
    }
}
