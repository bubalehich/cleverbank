package com.vlasova.cleverbank.dao;

import com.vlasova.cleverbank.pool.ConnectionPool;

import java.sql.ResultSet;

public abstract class AbstractDao<Entity> {
    protected final ConnectionPool pool;

    protected AbstractDao() {
        pool = ConnectionPool.getInstance();
    }

    protected abstract Entity mapFromResultSet(ResultSet resultSet);
}
