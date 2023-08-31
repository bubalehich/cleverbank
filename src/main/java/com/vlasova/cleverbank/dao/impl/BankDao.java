package com.vlasova.cleverbank.dao.impl;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.Bank;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@Slf4j
public class BankDao extends AbstractCrudDao<Bank> implements DaoInterface<Long, Bank> {
    private static final String SAVE = "INSERT INTO banks (bic_number,country, name) VALUES (?,?,?) ";
    private static final String UPDATE = "UPDATE banks SET bic_number = ?, bic_number = ?, name = ? WHERE id = ?";
    protected String findByIdQuery = "select * FROM banks WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM banks WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM card ORDER BY id LIMIT ? OFFSET ?";

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

    protected Bank mapFromResultSet(ResultSet resultSet) throws SQLException {
        Bank bank = new Bank();
        bank.setId(resultSet.getLong("id"));
        bank.setBicNumber(resultSet.getString("bic_number"));
        bank.setCountry(resultSet.getString("country"));
        bank.setName(resultSet.getString("name"));

        return bank;
    }
}
