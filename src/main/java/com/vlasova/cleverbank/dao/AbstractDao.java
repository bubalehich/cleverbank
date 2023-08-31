package com.vlasova.cleverbank.dao;

import com.vlasova.cleverbank.entity.accounte.Account;
import com.vlasova.cleverbank.pool.ConnectionPool;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao<Entity> {
    protected final ConnectionPool pool;

    protected AbstractDao() {
        pool = ConnectionPool.getInstance();
    }

    protected abstract Entity mapFromResultSet(ResultSet resultSet) throws SQLException;
}
