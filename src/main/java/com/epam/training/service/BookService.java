package com.epam.training.service;

import com.epam.training.dao.BookDao;
import com.epam.training.dao.DaoHelper;
import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.email.SendEmail;
import com.epam.training.exception.DAOException;
import com.epam.training.exception.MailException;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.Book;
import com.epam.training.model.User;
import com.epam.training.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookService {

    private final static Logger logger = LogManager.getLogger(BookService.class);

    private SendEmail sendEmail = new SendEmail();
    private Validator validator = new Validator();

    private DaoHelperFactory daoHelperFactory;
    private UserService userService = new UserService(daoHelperFactory);

    public BookService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void addBook(User user, Book book) throws ServiceException {
        if(!validator.isNull(book)) {
            if (user.isAdminAccess()) {
                if (validator.notLengthWord(book.getAuthor()) && validator.notLengthWord(book.getTitle())
                        && validator.notLengthWord(book.getType())) {
                    try (DaoHelper dao = daoHelperFactory.create()) {
                        BookDao bookDao = dao.createBookDao();
                        bookDao.create(book);
                        newBookMessage(book);
                    } catch (DAOException e) {
                        logger.error("Method addBook... Error...");
                        logger.error(e);
                        throw new ServiceException(e);
                    }
                } else {
                    logger.error("Length word/s: " + book.toString());
                    throw new ServiceException("Length word \"Author\" or \"Title\" or \"Type\" more max number");
                }
            }
        }
    }

    public void removeBook(User user, Long id) throws ServiceException {
        if(user.isAdminAccess()) {
            if (validator.isId(id, showMaxId())) {
                try (DaoHelper dao = daoHelperFactory.create()) {
                    BookDao bookDao = dao.createBookDao();
                    bookDao.delete(id);
                } catch (DAOException e) {
                    logger.error("Method removeBook... Error... id: " + id);
                    logger.error(e);
                    throw new ServiceException(e);
                }
            }
        }
    }

    public List<Book> showAllByAuthor(String author) throws ServiceException {
        List<Book> result = new ArrayList<>();
        if(!validator.isNull(author)) {
            try (DaoHelper dao = daoHelperFactory.create()) {
                BookDao bookDao = dao.createBookDao();
                return bookDao.findAllByAuthor(author);
            } catch (DAOException e) {
                logger.error("Method showAllByAuthor... Error... Author: " + author);
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public List<Book> showAllByTitle(String title) throws ServiceException {
        List<Book> result = new ArrayList<>();
        if(!validator.isNull(title)) {
            try (DaoHelper dao = daoHelperFactory.create()) {
                BookDao bookDao = dao.createBookDao();
                return bookDao.findAllByTitle(title);
            } catch (DAOException e) {
                logger.error("Method showAllByTitle... Error... Title: " + title);
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public List<Book> showAllByType(String type) throws ServiceException {
        List<Book> result = new ArrayList<>();
        if(!validator.isNull(type)) {
            try (DaoHelper dao = daoHelperFactory.create()) {
                BookDao bookDao = dao.createBookDao();
                return bookDao.findAllByType(type);
            } catch (DAOException e) {
                logger.error("Method showAllByType... Error... Type: " + type);
                logger.error(e);
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public List<Book> showAllBook() throws ServiceException {
        try(DaoHelper dao = daoHelperFactory.create()) {
            BookDao bookDao = dao.createBookDao();
            return bookDao.findAll();
        } catch (DAOException e) {
            logger.error("Method showAll... Error...");
            logger.error(e);
            throw new ServiceException(e);
        }
    }


    public Optional<Book> showBookById(Long id) throws ServiceException {
        Optional<Book> bookOptional = Optional.of(new Book());
        if(validator.isId(id, showMaxId())) {
            try (DaoHelper dao = daoHelperFactory.create()) {
                BookDao bookDao = dao.createBookDao();
                return bookDao.getBook(id);
            } catch (DAOException e) {
                logger.error("Method showBookById... Error... id: " + id);
                logger.error(e);
                throw new ServiceException();
            }
        }
        return bookOptional;
    }


    public List<Book> showBookAllByWord(String word) throws ServiceException{
        List<Book> result = new ArrayList<>();
        try {
            List<Book> resultByAuthor = showAllByAuthor(word);
            List<Book> resultByTitle = showAllByTitle(word);
            List<Book> resultByType = showAllByType(word);

            if(!resultByAuthor.isEmpty()) {
                result = resultByAuthor;
            }
            if(!resultByTitle.isEmpty()) {
                result = resultByTitle;
            }
            if(!resultByType.isEmpty()) {
                result = resultByType;
            }
        } catch (ServiceException e) {
            logger.error("Method showBookByWord... Error... Word: " + word);
            logger.error(e);
            throw new ServiceException(e);
        }
        return result;
    }

    public List<Book> showPageByNumber(Long page) throws ServiceException {
        int offset = 20;
        List<Book> bookList = new ArrayList<>();
        if(!validator.isNull(page)) {
            if((page * offset) < showMaxId()) {
                try (DaoHelper dao = daoHelperFactory.create()) {
                    BookDao bookDao = dao.createBookDao();
                    bookList = bookDao.getPageBooks(page);
                    return bookList;
                } catch (DAOException e) {
                    logger.error("Method showPageByNumber... Error... Page: " + page);
                    throw new ServiceException(e);
                }
            }
        }
        return bookList;
    }

    public long showMaxId(){
        try(DaoHelper dao = daoHelperFactory.create()) {
            BookDao bookDao = dao.createBookDao();
            return bookDao.getMaxId();
        } catch (DAOException e) {
            logger.error("Method showMaxId... Error...");
            logger.error(e);
        }
        return 0;
    }

    private void newBookMessage(Book book) throws ServiceException {
        String text = "Hello. We have a new book in our library. " + book.toString();
        if(!validator.isNull(book)) {
            try {
                User user = new User();
                user.setAdminAccess(true);
                long userMaxId = userService.showMaxId();
                for (long i = 1; i <= userMaxId; i++) {
                    String email = userService.showEmailById(user, i);
                    sendEmail.sendMail(email, text);
                }
            } catch (MailException e) {
                logger.error("Method newBookMessage... Error... Book: " + book.toString());
                logger.error(e);
                throw new ServiceException(e);
            }
        }
    }

    public void updateBook(User user, Book book, long id) throws ServiceException {
        if(user.isAdminAccess()) {
            try (DaoHelper dao = daoHelperFactory.create()) {
                BookDao bookDao = dao.createBookDao();
                bookDao.updateById(book, id);
            } catch (DAOException e) {
                logger.error("Method updateBook... Error... Book: " + book.toString());
                logger.error(e);
                throw new ServiceException(e);
            }
        }
    }
}
