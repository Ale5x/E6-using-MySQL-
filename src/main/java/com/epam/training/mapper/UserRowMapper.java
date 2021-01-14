package com.epam.training.mapper;

import com.epam.training.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet resultSet) throws SQLException {
        long userId = resultSet.getLong("user_id");
        String login = resultSet.getString("login");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        boolean isAdmin = resultSet.getBoolean("admin_access");

        return new User(userId, login, email, password, isAdmin);
    }
}
