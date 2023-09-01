package com.vlasova.cleverbank.dao.impl.transaction;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.transaction.TransactionType;
import com.vlasova.cleverbank.exception.MappingException;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class TransactionTypeDao extends AbstractCrudDao<TransactionType> implements DaoInterface<Long, TransactionType> {
    protected String findByIdQuery = "select * FROM transaction_types WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM transaction_types WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM transaction_types ORDER BY id LIMIT ? OFFSET ?";

    @Override
    protected TransactionType mapFromResultSet(ResultSet resultSet) {
        TransactionType transactionType = new TransactionType();
        try {
            transactionType.setId(resultSet.getLong("id"));
            transactionType.setName(resultSet.getString("name"));
            return transactionType;
        } catch (SQLException e) {
            throw new MappingException(e);
        }
    }
}
