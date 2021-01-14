package com.epam.training.service;

import com.epam.training.dao.DaoHelper;
import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.dao.UserDao;
import com.epam.training.exception.DAOException;
import com.epam.training.exception.PrintFileException;
import com.epam.training.exception.ServiceException;
import com.epam.training.io.PrintFile;
import com.epam.training.model.Book;
import com.epam.training.model.User;
import com.epam.training.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

public class UserService {

    private final static Logger logger = LogManager.getLogger(UserService.class);

    private Validator validator = new Validator();
    private PrintFile printFile = new PrintFile();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("y.MM.d - HH.mm");
    private String newDate = dateFormat.format(new GregorianCalendar().getTime());

    private DaoHelperFactory daoHelperFactory;
    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public boolean registrationUser(String email, String login, String password) throws ServiceException {
        try(DaoHelper dao = daoHelperFactory.create()) {
            UserDao userDao = dao.createUserDao();
            Optional<User> userOptional = userDao.getUser(email);
            if (!userOptional.isPresent()) {
                    userDao.create(new User(login, email, password, false));
                    return true;
            }
        }catch (DAOException e) {
            logger.error("Method registration... Error... Email:" + email + "/password:"
                    + password + "/login:" + login);
            logger.error(e);
            throw new ServiceException(e);
        }
        return false;
    }

    public Optional<User> verification(User user) throws ServiceException{
        try(DaoHelper dao = daoHelperFactory.create()) {
            UserDao userDao = dao.createUserDao();
            Optional<User> userOptional = userDao.getUser(user);
            return userOptional;
        } catch (DAOException e) {
            logger.error("Method verification... Error... User email:" + user.getEmail() +
                    ". User password " + user.getPassword());
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void removeAccount(Long id) throws ServiceException{
            try(DaoHelper dao = daoHelperFactory.create()) {
                UserDao userDao = dao.createUserDao();
                if(validator.isId(id, showMaxId())) {
                    userDao.delete(id);
                }
            } catch (DAOException e) {
                logger.error("Method removeAccount... Error... id: " + id);
                logger.error(e);
                throw new ServiceException(e);
            }
    }

   public Optional<User> showUserByEmail(User user, String email) throws ServiceException{
        Optional<User> userOptional = Optional.of(new User());
        if(user.isAdminAccess()) {
            try (DaoHelper dao = daoHelperFactory.create()) {
                UserDao userDao = dao.createUserDao();
                userOptional = userDao.getUser(email.toLowerCase().trim());
            } catch (DAOException e) {
                logger.error("Method showUserByEmail... Error... User - " + user.getUserId() +
                        " find email - " + email);
                logger.error(e);
                throw new ServiceException(e);
            }
        }
       return userOptional;
   }

    public String showEmailById(User user, Long id) throws ServiceException{
        String email = null;
        if(user.isAdminAccess()) {
            if (validator.isId(id, showMaxId())) {
                try (DaoHelper dao = daoHelperFactory.create()) {
                    UserDao userDao = dao.createUserDao();
                    email = userDao.getEmailById(id);
                } catch (DAOException e) {
                    logger.error("Method showEmailById.... Error... User - " + user.getUserId() +
                            " find id - " + id);
                    logger.error(e);
                    throw new ServiceException(e);
                }
            }
        }
        return email;
    }

    public void updateDataUser(User user) throws ServiceException {
        try (DaoHelper dao = daoHelperFactory.create()) {
            UserDao userDao = dao.createUserDao();
            userDao.updateData(user);
        } catch (DAOException e) {
            logger.error("Method updateDataUser... Error...");
            logger.error(e);
            throw new ServiceException();
        }
    }

    public Optional<User> showUserById(User user, Long id) throws ServiceException{
        Optional<User> userOptional = Optional.of(new User());
        if(user.isAdminAccess()) {
            if(validator.isId(id, showMaxId())) {
                try (DaoHelper dao = daoHelperFactory.create()) {
                    UserDao userDao = dao.createUserDao();
                    userOptional = userDao.getUser(id);
                } catch (DAOException e) {
                    logger.error("Method showUserById... Error...");
                    logger.error(e);
                    throw new ServiceException(e);
                }
            }
        }
        return userOptional;
    }

    public List<User> showAllUsers(User user) throws ServiceException{
        List<User> result = new ArrayList<>();
        if(user.isAdminAccess()) {
            try(DaoHelper dao = daoHelperFactory.create()) {
                UserDao userDao = dao.createUserDao();
                result = userDao.getListUsers();
                return result;
            } catch (DAOException e) {
                logger.error("Method showAllUsers... Error...");
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public long showMaxId() throws ServiceException {
        try(DaoHelper dao = daoHelperFactory.create()) {
            UserDao userDao = dao.createUserDao();
            return userDao.getMaxId();
        }catch (DAOException e) {
            logger.error("Method showMaxId");
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void updateAccessUser(User user, Long id, boolean access) throws ServiceException {
        if(user.isAdminAccess()) {
            try (DaoHelper dao = daoHelperFactory.create()) {
                UserDao userDao = dao.createUserDao();
                if (validator.isId(id, showMaxId())) {
                        userDao.updateAccess(id, access);
                }
            } catch (DAOException e) {
                logger.error("Method updateAccessUser... Error...");
                logger.error(e);
                throw new ServiceException(e);
            }
        }
    }

    public void message(String text, String userName) throws ServiceException {
        String name = newDate + " Message for Admin from - " + userName;
        try {
            printFile.print(text, printFile.getPATH_CATALOG_MESSAGE(), name);
        } catch (PrintFileException e) {
            logger.error("Method message... Error...");
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    public void suggestBookMessage(User user, Book book, String text) throws ServiceException {
        String name = newDate + " New Book - " + ((int) Math.random() * 10000);
        try {
            printFile.print(text, printFile.getPATH_CATALOG_MESSAGE(), name);
        }catch (PrintFileException e) {
            logger.error("Method suggestBookMessage... Error...");
            logger.error(e);
            throw new ServiceException();
        }
    }

    public List<User> showAllAdmin(User user) throws ServiceException{
        List<User> userList = new ArrayList<>();
        try (DaoHelper dao = daoHelperFactory.create()) {
            UserDao userDao = dao.createUserDao();
            if (user.isAdminAccess()) {
                userList = userDao.getListAdmin();
            }
        } catch (DAOException e) {
            logger.error("Method showAllAdmin... Error...");
            logger.error(e);
            throw new ServiceException(e);
        }
        return userList;
    }

 }
