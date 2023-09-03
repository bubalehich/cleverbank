package com.vlasova.cleverbank.dao.impl.transaction;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.transaction.TransactionType;
import com.vlasova.cleverbank.exception.DataAccessException;
import com.vlasova.cleverbank.exception.MappingException;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@ApplicationScoped
public class TransactionTypeDao extends AbstractCrudDao<TransactionType> implements DaoInterface<Long, TransactionType> {
    private static final String FIND_BY = "SELECT * FROM transaction_types WHERE ? = ?";
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

    public Optional<TransactionType> findBy(Pair<String, String> pair) throws DataAccessException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY)) {
            preparedStatement.setString(1, pair.getKey());
            preparedStatement.setString(2, pair.getValue());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return Optional.of(mapFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
