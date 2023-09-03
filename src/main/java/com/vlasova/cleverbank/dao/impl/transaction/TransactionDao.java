package com.vlasova.cleverbank.dao.impl.transaction;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.transaction.Transaction;
import com.vlasova.cleverbank.exception.DataAccessException;
import com.vlasova.cleverbank.exception.MappingException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@RequiredArgsConstructor
@ApplicationScoped
public class TransactionDao extends AbstractCrudDao<Transaction> implements DaoInterface<Long, Transaction> {
    private static final String SAVE = "INSERT INTO transactions (amount, date, receiver_id, sender_id, type_id, number, payload, currency_id) VALUES (?,?,?,?,?,?,?)";
    protected String findByIdQuery = "SELECT * FROM transactions WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM transactions WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM transactions ORDER BY id LIMIT ? OFFSET ?";

    @Override
    public Transaction save(Transaction transaction) throws DataAccessException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setBigDecimal(1, transaction.getAmount());
            preparedStatement.setDate(2, new Date(transaction.getDate().getEpochSecond())); //TODO check!
            preparedStatement.setLong(3, transaction.getReceiver().getId());
            preparedStatement.setLong(4, transaction.getSender().getId());
            preparedStatement.setLong(5, transaction.getTransactionType().getId());
            preparedStatement.setString(6, transaction.getNumber().toString());
            preparedStatement.setString(7, transaction.getPayload());
            preparedStatement.setLong(8, transaction.getCurrency().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                transaction.setId((long) resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return transaction;
    }

    @Override
    protected Transaction mapFromResultSet(ResultSet resultSet) {
        try {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getLong("id"));
            transaction.setAmount(resultSet.getBigDecimal("amount"));
            transaction.setDate(Instant.from(resultSet.getDate("date").toInstant()));
            transaction.setNumber(UUID.fromString(resultSet.getString("number")));
            transaction.setPayload(resultSet.getString("payload"));

            return transaction;
        } catch (SQLException e) {
            throw new MappingException(e);
        }
    }
}
