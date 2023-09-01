package com.vlasova.cleverbank.dao.impl.transaction;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.transaction.Transaction;
import com.vlasova.cleverbank.exception.DataAccessException;
import jakarta.enterprise.context.ApplicationScoped;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@ApplicationScoped
public class TransactionDao extends AbstractCrudDao<Transaction> implements DaoInterface<Long, Transaction> {
    private static final String SAVE = "INSERT INTO transactions (amount, date, receiver_id, sender_id, type_id, number, payload) VALUES (?,?,?,?,?,?,?)";
    protected String findByIdQuery = "SELECT * FROM transactions WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM transactions WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM transactions ORDER BY id LIMIT ? OFFSET ?";

    @Override
    public Optional<Transaction> save(Transaction transaction) throws DataAccessException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, transaction.getAmount());
            preparedStatement.setDate(2, new Date(transaction.getDate().getEpochSecond())); //TODO check!
            preparedStatement.setLong(3, transaction.getReceiver().getId());
            preparedStatement.setLong(4, transaction.getSender().getId());
            preparedStatement.setLong(5, transaction.getTransactionType().getId());
            preparedStatement.setString(6, transaction.getNumber().toString());
            preparedStatement.setString(7, transaction.getPayload());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                transaction.setId((long) resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return Optional.of(transaction);
    }

    @Override
    protected Transaction mapFromResultSet(ResultSet resultSet) {
        return null;
    }
}
