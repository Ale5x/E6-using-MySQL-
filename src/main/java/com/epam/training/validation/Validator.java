package com.epam.training.validation;

import com.epam.training.constant.TableBD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validator {

    private final static Logger logger = LogManager.getLogger(Validator.class);

    private final String REGEX_EMAIL = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b";
    private final String REGEX_PASSWORD  = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
    private final int MAX_LENGTH = 200;


    public boolean isNull(Object obj) {
        if (obj == null) {
            logger.error("Method isNull. Object: " + obj + " - null");
            return true;
        }
        return false;
    }

    public boolean notLengthWord(String line) {
        if(line.length() < TableBD.LINE_LENGTH) {
            return true;
        }
        return false;
    }

    public boolean isId(long id, long maxId) {
        return (id > 0 && id <= maxId);
    }

    public boolean isEmail(String email) {
        if(email.trim().matches(REGEX_EMAIL)) {
            if(isMaxLength(email)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPassword(String password) {
        if(password.matches(REGEX_PASSWORD)) {
            return true;
        }
        return false;
    }

    public boolean isLogin(String login) {
        if(isMaxLength(login)) {
            return true;
        }
        return false;
    }

    private boolean isMaxLength(String word) {
        if(word.length() < MAX_LENGTH) {
            return true;
        }
        return false;
    }
}