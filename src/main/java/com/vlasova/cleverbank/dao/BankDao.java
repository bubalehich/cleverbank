package com.vlasova.cleverbank.dao;

import com.vlasova.cleverbank.entity.Bank;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Slf4j
public class BankDao extends AbstractDao implements DaoInterface<Long, Bank> {
    private static final String FIND_BY_ID = "select * FROM banks WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM card ORDER BY id LIMIT ? OFFSET ?";
    private static final String SAVE = "INSERT INTO banks (bic_number,country, name) VALUES (?,?,?) ";
    private static final String UPDATE = "UPDATE banks SET bic_number = ?, bic_number = ?, name = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM banks WHERE id = ?";

    @Override
    public Optional<Bank> save(Bank bank) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bank.getBicNumber());
            preparedStatement.setString(2, bank.getCountry());
            preparedStatement.setString(3, bank.getName());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                bank.setId((long) resultSet.getInt(1));
            }
        }

        return Optional.of(bank);
    }

    @Override
    public boolean update(Bank bank) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, bank.getBicNumber());
            preparedStatement.setString(2, bank.getCountry());
            preparedStatement.setString(3, bank.getName());
            preparedStatement.setLong(4, bank.getId());

            return preparedStatement.executeUpdate() > 1;
        }
    }

    @Override
    public boolean delete(Long id) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    @Override
    public Optional<Bank> findById(Long id) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(mapFromResultSet(resultSet))
                        : Optional.empty();
            }
        }
    }

    @Override
    public Collection<Bank> findAll(Integer pageNumber, Integer pageSize) throws SQLException {
        List<Bank> banks = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, pageNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    banks.add(mapFromResultSet(resultSet));
                }
            }
        }

        return banks.isEmpty() ? new ArrayList<Bank>() : banks;
    }


    private Bank mapFromResultSet(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank();
        bank.setId(resultSet.getLong("id"));
        bank.setBicNumber(resultSet.getString("bic_number"));
        bank.setCountry(resultSet.getString("country"));
        bank.setName(resultSet.getString("name"));

        return bank;
    }
}
