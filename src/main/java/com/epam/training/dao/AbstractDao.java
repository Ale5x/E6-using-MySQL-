package com.epam.training.dao;

import com.epam.training.exception.DAOException;
import com.epam.training.mapper.ResultMapper;
import com.epam.training.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractDao<T> {

    private final static Logger logger = LogManager.getLogger(AbstractDao.class);

    private Connection connection;
    private String table;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public int save(String query, Object... parameters) throws DAOException {
        return executeUpdate(query, parameters);
    }

    public int delete(String query, Object... parameters) throws DAOException {
        return executeUpdate(query, parameters);
    }

    public Optional<T> findById(String query, Object... parameters) throws DAOException {
        return Optional.empty();
    }

    public List<T> findAll(String query, Object... parameters) throws DAOException {
        String table = getTableName();
        RowMapper<T> mapper = (RowMapper<T>) RowMapper.create(table);
        return executeQuery(query, mapper, parameters);
    }

    public long findMaxId(String query) throws DAOException {
        return singleLongExecuteQuery(query);
    }

    public int updateData(String query, Object... parameters) throws DAOException {
        return executeUpdate(query, parameters);
    }

    public int executeUpdate(String query, Object... parameters) throws DAOException {
        try (PreparedStatement prStatement = createStatement(query, parameters)) {
            int result = prStatement.executeUpdate();
            return result;
        } catch (SQLException SQLe) {
            logger.error("Method executeUpdate... Error...");
            for(Throwable e : SQLe) {
                logger.error(e);
            }
            throw new DAOException("Error in \"executeUpdate\"", SQLe);
        }
    }

    public List<T> executeQuery(String query, RowMapper<T> mapper, Object... parameters) throws DAOException{
        List<T> entities = new ArrayList<>();
        try(PreparedStatement prStatement = createStatement(query, parameters);
            ResultSet resultSet = prStatement.executeQuery()) {
            while (resultSet.next()) {
                T entity = mapper.map(resultSet);
                entities.add(entity);
            }
            return entities;
        }catch (SQLException SQLe) {
            logger.error("Method executeQuery... Error...");
            for(Throwable e : SQLe) {
                logger.error(e);
            }
        }
        return entities;
    }

    private int execute(String query, Object... parameters) throws DAOException{
        try(PreparedStatement prStatement = createStatement(query, parameters)) {
            return prStatement.executeUpdate();
        }catch (SQLException SQLe ) {
            logger.error("Method execute... Error...");
            for(Throwable e : SQLe) {
                logger.error(e);
            }
        }
        return 0;
    }

    private long singleLongExecuteQuery(String query) throws DAOException {
        try (PreparedStatement prStatement = createStatement(query);
             ResultSet resultSet = prStatement.executeQuery()) {
            return ResultMapper.maxIdInTable(resultSet);
        }catch (SQLException SQLe) {
            logger.error("Method singleLongExecuteQuery... Error");
            for(Throwable e : SQLe) {
                logger.error(e);
            }
        }
        return 0;
    }

    public String singleStringExecuteQuery(String query, Object... parameters) throws DAOException {
        try (PreparedStatement prStatement = createStatement(query, parameters);
             ResultSet resultSet = prStatement.executeQuery()) {
            return ResultMapper.getEmail(resultSet);
        }catch (SQLException SQLe) {
            logger.error("Method singleStringExecuteQuery... Error...");
            for(Throwable e : SQLe) {
                logger.error(e);
            }
        }
        return null;
    }

    public Optional<T> getSingleResult(String query, RowMapper<T> builder, Object... parameters) throws DAOException {
        List<T> item = executeQuery(query, builder, parameters);
        if (item.size() == 1) {
            return Optional.of(item.get(0));
        } else if (item.size() > 1) {
            logger.warn("Method getSingleResult... Find more 1");
            throw new UnsupportedOperationException("Find more 1");
        }
        return Optional.empty();
    }

    public PreparedStatement createStatement(String query, Object... parameters) throws SQLException {
        PreparedStatement prStatement = connection.prepareStatement(query);
        int arrayLength = parameters.length;
        for (int i = 1; i <= arrayLength; i++) {
            prStatement.setObject(i, parameters[i - 1]);
        }
        return prStatement;
    }

    protected String getTableName() {
        return table;
    }
}