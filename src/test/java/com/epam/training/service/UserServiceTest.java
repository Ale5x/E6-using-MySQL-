package com.epam.training.service;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    static UserService userServiceTest;

    @BeforeEach
    void setUp() {
        DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
        userServiceTest = new UserService(daoHelperFactory);
    }

    @Test
    void registrationUser() {
        String login = "login";
        String email = "email12@gmail.com";
        String password = "111";
        boolean isResult = false;
        try {
            isResult = userServiceTest.registrationUser(email, login, password);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(isResult);
    }

    @Test
    public void verification() throws ServiceException{
        String email = "email@email.com";
        String password = "111";
        Optional<User> optionalUser = userServiceTest.verification(new User(email, password));
        System.out.println(optionalUser.map(User::getUserId).orElse(0l) + "/"
                            + optionalUser.map(User::getEmail).orElse("UNKNOWN") + "/"
                            + optionalUser.map(User::getLogin).orElse("UNKNOWN") + "/"
                            + optionalUser.map(User::isAdminAccess).orElse(false) + "/");
        assertTrue(optionalUser.isPresent());
    }

    @Test
    void registrationUserTestNumberTwo() {
        // User already created...
        String login = "login";
        String email = "email";
        String password = "111";
        boolean isResult = true;
        try {
            isResult = userServiceTest.registrationUser(email, login, password);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(isResult);
    }

    @Test
    void showUserByEmail() {
        User user = new User(1, "User", "user@gmail.com", "111", true);
        String email = "email@gmail.com";
        Optional<User> userOptional = Optional.empty();
        try {
            userOptional = userServiceTest.showUserByEmail(user, email);
            if(userOptional.isPresent()) {
                System.out.println(userOptional.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(userOptional.isPresent());
    }

    @Test
    void showUserByIncorrectEmail() {
        User user = new User(1, "User", "user@gmail.com", "111", true);
        String email = "email@gmail.com1";
        Optional<User> userOptional = null;
        try {
            userOptional = userServiceTest.showUserByEmail(user, email);
            if(userOptional.isPresent()) {
                System.out.println(userOptional.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(userOptional.isPresent());
    }

    @Test
    void showUserByEmailByIncorrectAccess() {
        User user = new User(1, "User", "user@gmail.com", "111", false);
        String email = "email@gmail.com";
        Optional<User> userOptional = null;
        try {
            userOptional = userServiceTest.showUserByEmail(user, email);
            if(userOptional.isPresent()) {
                System.out.println(userOptional.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(userOptional.get().getEmail() == null);
    }

    @Test
    void showUserById() {
        User user = new User(1, "User", "user@gmail.com", "111", true);
        long id = 3;
        Optional<User> userOptional = null;
        try {
            userOptional = userServiceTest.showUserById(user, id);
            if(userOptional.isPresent()) {
                System.out.println(userOptional.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(userOptional.isPresent());
    }

    @Test
    void showUserByIncorrectId() {
        User user = new User(1, "User", "user@gmail.com", "111", true);
        long id = 30;
        Optional<User> userOptional = null;
        try {
            userOptional = userServiceTest.showUserById(user, id);
            if(userOptional.isPresent()) {
                System.out.println(userOptional.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertNull(userOptional);
    }

    @Test
    void showUserByIdByIncorrectAccess() {
        User user = new User(1, "User", "user@gmail.com", "111", false);
        long id = 3;
        Optional<User> userOptional = Optional.empty();
        try {
            userOptional = userServiceTest.showUserById(user, id);
            if(userOptional.isPresent()) {
                System.out.println(userOptional.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(userOptional.get().getEmail() == null);
    }

    @Test
    void showEmailById() {
        User user = new User();
        user.setAdminAccess(true);
        long id = 3;
        String email = null;
        try {
            email = userServiceTest.showEmailById(user, id);
            System.out.println("email id " + id + " - " + email);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertNotNull(email);
    }

    @Test
    void showEmailByIncorrectId() {
        User user = new User();
        user.setAdminAccess(true);
        long id = 30;
        String email = null;
        try {
            email = userServiceTest.showEmailById(user, id);
            System.out.println("email id " + id + " - " + email);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertNull(email);
    }

    @Test
    void showEmailByIncorrectMinusId() {
        User user = new User();
        user.setAdminAccess(true);
        long id = -3;
        String email = null;
        try {
            email = userServiceTest.showEmailById(user, id);
            System.out.println("email id " + id + " - " + email);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertNull(email);
    }

    @Test
    void showAllUsers() {
        User user = new User(1, "User", "user@gmail.com", "111", true);
        List<User> userList = new ArrayList<>();
        try {
            userList = userServiceTest.showAllUsers(user);
            for(User users : userList) {
                System.out.println(users.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(userList.isEmpty());
    }

    @Test
    void showAllUsersByIncorrectAccess() {
        User user = new User(1, "User", "user@gmail.com", "111", false);
        List<User> userList = new ArrayList<>();
        try {
            userList = userServiceTest.showAllUsers(user);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(userList.isEmpty());
    }

    @Test
    void showMaxId() {
        long maxId = 0;
        try {
            maxId = userServiceTest.showMaxId();
            System.out.println("Max id - " + maxId);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(maxId > 0);
    }

    @Test
    void showAllAdmin() {
        User user = new User();
        user.setAdminAccess(true);
        List<User> userList = new ArrayList<>();
        try {
            userList = userServiceTest.showAllAdmin(user);
            for(User users : userList) {
                System.out.println(users.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(userList.isEmpty());
        }

    @Test
    void showAllAdminByIncorrectAccess() {
        User user = new User();
        user.setAdminAccess(false);
        List<User> userList = new ArrayList<>();
        try {
            userList = userServiceTest.showAllAdmin(user);
            for(User users : userList) {
                System.out.println(users.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(userList.isEmpty());
    }
}