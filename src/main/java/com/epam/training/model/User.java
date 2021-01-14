package com.epam.training.model;

import com.epam.training.servlet.ActionBookServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class User {

    private final static Logger logger = LogManager.getLogger(User.class);

    private long userId;
    private String login;
    private String email;
    private String password;
    private String passwordCipher;
    private boolean adminAccess;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        setPassword(password);
    }

    public User(long userId, String login, String email, String password, boolean adminAccess) {
        this.userId = userId;
        this.login = login;
        this.email = email;
        this.password = password;
        this.adminAccess = adminAccess;
    }

    public User(long userId, String login, String email, String password) {
        this.userId = userId;
        this.login = login;
        this.email = email;
        setPassword(password);
    }

    public User(String login, String email, String password, boolean adminAccess) {
        this.login = login;
        this.email = email;
        setPassword(password);
        this.adminAccess = adminAccess;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(password.getBytes());
            StringBuilder passwordCipher = new StringBuilder();
            for (byte b : bytes) {
                passwordCipher.append(String.format("%02X", b));
            }
            this.password =  passwordCipher.toString();
        }catch (NoSuchAlgorithmException e) {
            logger.error("Method Password... Error build password... Password: " + password);
            logger.error(e);
        }
    }

    public String getPasswordCipher() {
        return passwordCipher;
    }

    public void setPasswordCipher(String passwordCipher) {
        this.passwordCipher = passwordCipher;
    }

    public boolean isAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(boolean adminAccess) {
        this.adminAccess = adminAccess;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + userId +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", adminAccess=" + adminAccess +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        User user = (User) obj;
        return userId == user.getUserId()
                && (passwordCipher == user.passwordCipher || (passwordCipher != null)
                && (passwordCipher.equals(user.getPasswordCipher())))
                && (login == user.login || (login != null) && (login.equals(user.getLogin())))
                && (email == user.email || (email != null) && (email.equals(user.getEmail())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, login, email, passwordCipher, adminAccess);
    }
}