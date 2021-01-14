package com.epam.training.connection;
import com.epam.training.exception.ConnectionException;
import com.epam.training.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class ConnectionFactory {

    private final static Logger logger = LogManager.getLogger(ConnectionFactory.class);

    private Validator validator = new Validator();

    private static final String PATH_TO_PROPERTIES = "src/main/resources/database.properties";
    private static String driverDatabase;
    private static String url;
    private static String user;
    private static String password;


    public ConnectionFactory(){
        Properties properties = new Properties();
        try (InputStream input = Files.newInputStream(Paths.get(PATH_TO_PROPERTIES))) {
            properties.load(input);
        } catch (IOException e) {
            logger.error("Properties file for connection to database not find. Path: " + PATH_TO_PROPERTIES);
            logger.error("ConnectionFactory.... Error... ", e);
        }
        driverDatabase = properties.getProperty("driverDatabase");
        url = properties.getProperty("url");
        user = properties.getProperty("username");
        password = properties.getProperty("password");

    }

    public Connection create() {
        Connection connection = null;
        try {
//            if(!validator.isNull(driverDatabase)) {
//                Class.forName(driverDatabase);
//            }
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            logger.error("Connection not created... ");
            logger.error("url: " + url);
            logger.error("username: " + user);
            logger.error("password: " + password);
            logger.error(e);
            throw new ConnectionException(e);
//        } catch (ClassNotFoundException e) {
//            logger.error("Connection not created...  Driver for mysql not found...");
//            logger.error("Class.forName(driverDatabase): " + driverDatabase);
        }
        return connection;
    }
}