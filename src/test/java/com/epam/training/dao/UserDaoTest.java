package com.epam.training.dao;

import com.epam.training.connection.ConnectionFactory;
import com.epam.training.dao.impl.UserDaoImpl;
import com.epam.training.exception.DAOException;
import com.epam.training.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest{

   static ConnectionFactory connectionFactory;
   static UserDaoImpl userDaoTest;

    @BeforeEach
    void setUp(){
        connectionFactory = new ConnectionFactory();
        userDaoTest = new UserDaoImpl(connectionFactory.create());
    }

    @Test
    void create() {
        User user = new User("login", "email.com", "111", true);
        int actual = 0;
        int expected = 1;
        try {
            actual = userDaoTest.create(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @Test
    void updateData() {
        User user = new User(3, "login update", "email.com", "password update", false);
        int actual = 0;
        int expected = 1;
        try {
            actual = userDaoTest.updateData(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @Test
    void updateAccess() {
        long userId = 3;
        boolean access = false;
        int actual = 0;
        int expected = 1;
        try {
            actual = userDaoTest.updateAccess(userId, access);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertEquals(expected, actual);
    }

    @Test
    void getUser() {
        long userId = 3;
        Optional<User> user = null;
        try {
            user = userDaoTest.getUser(userId);
            System.out.println(user.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertTrue(user.isPresent());
    }

    @Test
    void testGetUser() {
        String email = "email@email.com";
        Optional<User> user = null;
        try {
            user = userDaoTest.getUser(email);
            System.out.println(user.toString());
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertTrue(user.isPresent());

    }

    @Test
    void testGetUserByEmailAndPassword() {
        User user = new User("email@email.com", "111");
        Optional<User> userOptional = null;
        try {
            userOptional = userDaoTest.getUser(user);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertTrue(userOptional.isPresent());
    }

    @Test
    void delete() {
        long removeId = 1;
        int actual = 0;
        int expected = 1;
        try {
            actual = userDaoTest.delete(removeId);
        } catch (DAOException  e) {
            e.printStackTrace();
        }
        assertTrue(actual > 0);
    }

    @Test
    void getListUsers() {
        List<User> userList = null;
        try {
            userList = userDaoTest.getListUsers();
            for(User user : userList) {
                System.out.println(user.toString());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertNotNull(userList.isEmpty());
    }

    @Test
    void getMaxId() {
        long maxId = 0;
        try {
            maxId = userDaoTest.getMaxId();
        } catch (DAOException e) {
            e.printStackTrace();
        }
        System.out.println("max id table \"user_data\" " + maxId);
        assertTrue(maxId > 0);
    }

    @Test
    void getEmailById() {
        long id = 3;
        String email = null;
        try {
            email = userDaoTest.getEmailById(id);
            System.out.println("Email id  " + id + " - " + email);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertNotNull(email);
    }
}