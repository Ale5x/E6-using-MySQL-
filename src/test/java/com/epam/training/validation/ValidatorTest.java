package com.epam.training.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {

    Validator validator = new Validator();

    @Test
    void isNull() {
        String object = null;
        assertTrue(validator.isNull(object));
    }

    @Test
    void isNotNull() {
        String object = "null";
        assertFalse(validator.isNull(object));
    }

    @Test
    void notLengthWord() {
        String length = "123456789";
        assertTrue(validator.notLengthWord(length));
    }

    @Test
    void notLengthWordIncorrectData() {
        String length = "123456789987654321123456789987456321123654789654123654789965412365478965412332123365478" +
                "123456789987654321123456789987456321123654789654123654789965412365478965412332123365478" +
                "123456789987654321123456789987456321123654789654123654789965412365478965412332123365478" +
                "123456789987654321123456789987456321123654789654123654789965412365478965412332123365478" +
                "123456789987654321123456789987456321123654789654123654789965412365478965412332123365478";
        System.out.println("Length length - " + length.length());
        assertFalse(validator.notLengthWord(length));
    }

    @Test
    void isId() {
        long id = 5;
        long maxId = 11;
        assertTrue(validator.isId(id, maxId));
    }

    @Test
    void isIdIncorrectDataId() {
        long id = -5;
        long maxId = 11;
        assertFalse(validator.isId(id, maxId));
    }

    @Test
    void isIdIncorrectIdMoreMaxId() {
        long id = 50;
        long maxId = 11;
        assertFalse(validator.isId(id, maxId));
    }

    @Test
    void isEmail() {
        String email = "email@email.com";
        assertTrue(validator.isEmail(email));
    }

    @Test
    void isEmailIncorrectData() {
        String email = "email@email";
        System.out.println(validator.isEmail(email));
        assertFalse(validator.isEmail(email));
    }

    @Test
    void isPassword() {
        String password = "admin";
        assertTrue(validator.isPassword(password));
    }

    @Test
    void isLoginIncorrectData() {
        String login = "hjk;sd554";
        assertFalse(validator.isLogin(login));
    }

    @Test
    void isLogin() {
        String login = "wwwwww";
        assertTrue(validator.isLogin(login));
    }
        //System.out.println(word.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{6,}$"));

}