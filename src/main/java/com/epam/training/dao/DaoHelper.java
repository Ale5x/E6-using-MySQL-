package com.epam.training.dao;

import com.epam.training.connection.ConnectionPool;
import com.epam.training.connection.ProxyConnection;
import com.epam.training.dao.impl.BookDaoImpl;
import com.epam.training.dao.impl.UserDaoImpl;
import com.epam.training.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DaoHelper implements AutoCloseable {

    private final static Logger logger = LogManager.getLogger(DaoHelper.class);

    private ProxyConnection connection;

    public DaoHelper(ConnectionPool connectionPool) {
        this.connection = connectionPool.getConnection();
    }

    public UserDaoImpl createUserDao() {
        return new UserDaoImpl(connection);
    }

    public BookDaoImpl createBookDao() {
        return new BookDaoImpl(connection);
    }

    @Override
    public void close() throws DAOException {
        try {
            if(!(connection == null)) {
                connection.close();
            }
        } catch (SQLException sqlE) {
            logger.error("Method close...");
            for(Throwable e : sqlE) {
                logger.error(e);
            }
            throw new DAOException(sqlE);
        }
    }

    public void startTransaction() throws DAOException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("Method startTransaction... Error... ");
            logger.error(e);
            throw new DAOException(e);
        }
    }
}
