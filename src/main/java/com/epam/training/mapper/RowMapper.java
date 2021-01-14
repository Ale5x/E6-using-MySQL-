package com.epam.training.mapper;

import com.epam.training.constant.TableBD;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {

    String UNKNOWN_TABLE = "Unknown table = ";

    T map(ResultSet resultSet) throws SQLException;

    static RowMapper<?> create(String tableName) {
        switch (tableName) {
            case TableBD.TABLE_CATALOG_BOOK:
                return new BookRowMapper();
            case TableBD.TABLE_USER:
                return new UserRowMapper();
            default:
                throw new IllegalArgumentException(UNKNOWN_TABLE + tableName);
        }
    }
}
