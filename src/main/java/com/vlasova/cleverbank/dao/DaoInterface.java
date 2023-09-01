package com.vlasova.cleverbank.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface DaoInterface<Id, Entity> {
    Optional<Entity> save(Entity type) throws SQLException;

    boolean update(Entity entity) throws SQLException;

    boolean delete(Id id) throws SQLException;

    Optional<Entity> findById(Id id) throws SQLException;

    Collection<Entity> findAll(Integer pageNumber, Integer pageSize) throws SQLException;
}
