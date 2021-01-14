package com.epam.training.email;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertyEmail {

    private final static Logger logger = LogManager.getLogger(PropertyEmail.class);

    private final String PATH_PROPERTIES_EMAIL = "src/main/resources/email.properties";

    private String accountEmail;
    private String password;
    private String auth;
    private String authStatus;
    private String enable;
    private String enableStatus;
    private String host;
    private String hostStatus;
    private String port;
    private String portStatus;


    public PropertyEmail() {
        setPropertyEmail();
    }

    private void setPropertyEmail() {
        Properties prop = new Properties();
        try(InputStream input = Files.newInputStream(Paths.get(PATH_PROPERTIES_EMAIL))) {
            prop.load(input);
            accountEmail = prop.getProperty("accountEmail");
            password = prop.getProperty("accountPassword");
            auth = prop.getProperty("auth");
            authStatus = prop.getProperty("authStatus");
            enable = prop.getProperty("enable");
            enableStatus = prop.getProperty("enableStatus");
            host = prop.getProperty("host");
            hostStatus = prop.getProperty("hostStatus");
            port = prop.getProperty("port");
            portStatus = prop.getProperty("portStatus");

        }catch (IOException e) {
            logger.error("Settings file not found: " + PATH_PROPERTIES_EMAIL);
            logger.error(e);
        }
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public String getPassword() {
        return password;
    }

    public String getAuth() {
        return auth;
    }

    public String getAuthStatus() {
        return authStatus;
    }

    public String getEnable() {
        return enable;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public String getHost() {
        return host;
    }

    public String getHostStatus() {
        return hostStatus;
    }

    public String getPort() {
        return port;
    }

    public String getPortStatus() {
        return portStatus;
    }
}
