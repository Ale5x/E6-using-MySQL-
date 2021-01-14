package com.epam.training.connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectionFactoryTest {

    ConnectionFactory connectionFactory;
    Connection connection;

    @BeforeEach
    void setUp() {
        connectionFactory = new ConnectionFactory();
    }

    @Test
    void create() {
        connection = connectionFactory.create();
        try {
            System.out.println("Connection is null?? - " + connection == null);
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}