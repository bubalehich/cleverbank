package com.vlasova.cleverbank.dao;

import com.vlasova.cleverbank.pool.ConnectionPool;

public class AbstractDao {
    protected final ConnectionPool pool;

    protected AbstractDao() {
        pool = ConnectionPool.getInstance();
    }
}
