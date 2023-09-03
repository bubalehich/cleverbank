package com.vlasova.cleverbank.dao;

import com.vlasova.cleverbank.exception.DataAccessException;

import java.util.Collection;
import java.util.Optional;

public interface DaoInterface<Id, Entity> {
    Entity save(Entity type) throws DataAccessException;

    boolean update(Entity entity) throws DataAccessException;

    boolean delete(Id id) throws DataAccessException;

    Optional<Entity> findById(Id id) throws DataAccessException;

    Collection<Entity> findAll(Integer pageNumber, Integer pageSize) throws DataAccessException;
}
