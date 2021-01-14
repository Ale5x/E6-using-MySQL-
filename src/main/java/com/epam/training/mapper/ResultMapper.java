package com.epam.training.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class ResultMapper {

    public static long maxIdInTable(ResultSet resultSet) throws SQLException {
        long maxId = 0;
        while (resultSet.next()) {
            maxId = resultSet.getInt(1);
        }
        return maxId;
    }

    public static String getEmail(ResultSet resultSet) throws SQLException {
        String email = null;
        while(resultSet.next()) {
            email = resultSet.getString(1);
        }
        return email;
    }
}
