package com.vlasova.cleverbank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudDao<Entity> extends AbstractDao<Entity> {
    protected String findAllQuery;
    protected String deleteByIdQuery;
    protected String findByIdQuery;

    public Collection<Entity> findAll(Integer pageNumber, Integer pageSize) throws SQLException {
        List<Entity> items = new ArrayList<>();

        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, pageNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    items.add(mapFromResultSet(resultSet));
                }
            }
        }

        return items;
    }

    public boolean delete(Long id) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        }
    }

    public Optional<Entity> findById(Long id) throws SQLException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(mapFromResultSet(resultSet))
                        : Optional.empty();
            }
        }
    }
}
