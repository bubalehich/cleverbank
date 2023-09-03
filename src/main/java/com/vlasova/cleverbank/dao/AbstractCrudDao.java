package com.vlasova.cleverbank.dao;

import com.vlasova.cleverbank.exception.DataAccessException;

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

    public Collection<Entity> findAll(Integer pageNumber, Integer pageSize) throws DataAccessException {
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
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return items;
    }

    public boolean delete(Long id) throws DataAccessException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public Optional<Entity> findById(Long id) throws DataAccessException {
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findByIdQuery)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next()
                        ? Optional.of(mapFromResultSet(resultSet))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public boolean update(Entity entity) throws DataAccessException {
        throw new UnsupportedOperationException("The requested operation is not supported");
    }

    public Entity save(Entity entity) throws DataAccessException {
        throw new UnsupportedOperationException("The requested operation is not supported");
    }
}
