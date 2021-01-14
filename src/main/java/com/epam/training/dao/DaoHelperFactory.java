package com.epam.training.dao;

import com.epam.training.connection.ConnectionPool;

public class DaoHelperFactory {

    public DaoHelper create() {
        return new DaoHelper(ConnectionPool.getInstance());
    }
}
