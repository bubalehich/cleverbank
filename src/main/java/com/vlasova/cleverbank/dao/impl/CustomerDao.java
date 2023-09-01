package com.vlasova.cleverbank.dao.impl;

import com.vlasova.cleverbank.dao.AbstractCrudDao;
import com.vlasova.cleverbank.dao.DaoInterface;
import com.vlasova.cleverbank.entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class CustomerDao extends AbstractCrudDao<Customer> implements DaoInterface<Long, Customer> {
    private static final String SAVE = "INSERT INTO customers (is_active, name, surname) VALUES (?,?,?) ";
    private static final String UPDATE = "UPDATE customers SET is_active = ?, name = ?, surname = ? WHERE id = ?";
    protected String findByIdQuery = "SELECT * FROM customers WHERE id = ?";
    protected String deleteByIdQuery = "DELETE FROM customers WHERE id = ?";
    protected String findAllQuery = "SELECT * FROM customers ORDER BY id LIMIT ? OFFSET ?";

    @Override
    protected Customer mapFromResultSet(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setName(resultSet.getString("name"));
        customer.setSurname(resultSet.getString("surname"));
        customer.setActive(resultSet.getBoolean("is_active"));

        return customer;
    }

    @Override
    public Optional<Customer> save(Customer customer) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE, RETURN_GENERATED_KEYS)) {
            preparedStatement.setBoolean(1, customer.isActive());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getSurname());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                customer.setId((long) resultSet.getInt(1));
            }
        }

        return Optional.of(customer);
    }

    @Override
    public boolean update(Customer customer) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setBoolean(1, customer.isActive());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.setString(3, customer.getSurname());
            preparedStatement.setLong(4, customer.getId());

            return preparedStatement.executeUpdate() > 1;
        }
    }
}
