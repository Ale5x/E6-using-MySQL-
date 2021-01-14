package com.epam.training.dao;

import com.epam.training.connection.ConnectionFactory;
import com.epam.training.dao.impl.BookDaoImpl;
import com.epam.training.exception.DAOException;
import com.epam.training.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoTest {

    private ConnectionFactory connection;
    private BookDaoImpl bookDaoTest;

    @BeforeEach
    void setUp() {
            connection = new ConnectionFactory();
            bookDaoTest = new BookDaoImpl(connection.create());
    }

    @Test
    void create() {
        Book book = new Book("Test author 21 12", "Test title 21 12", "Test type");
        int actual = 0;
        int expected = 1;
        try {
            actual = bookDaoTest.create(book);

        }catch (DAOException e) {}
        assertEquals(expected, actual);
    }

    @Test
    void delete() {
        long removeId = 3;
        int actual = 0;
        int expected = 1;
        try {
            actual = bookDaoTest.delete(removeId);
        }catch (DAOException e) { }
        assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        List<Book> result = new ArrayList<>();
        try {
            result = bookDaoTest.findAll();
            for(Book book : result) {
                System.out.println(book.toString());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertTrue(!result.isEmpty());
    }

    @Test
    void findAllByTitle() {
        String findTitle = "властелин";
        List<Book> result = new ArrayList<>();
        try {
            result = bookDaoTest.findAllByTitle(findTitle);
            System.out.println(result.toString());

        }catch (DAOException e) {}
        assertTrue(!result.isEmpty());
    }

    @Test
    void findAllByAuthor() {
        String findAuthor = "рональд";
        List<Book> result = new ArrayList<>();
        try {
            result = bookDaoTest.findAllByAuthor(findAuthor);
        }catch (DAOException e) {}
        assertTrue(!result.isEmpty());
    }

    @Test
    void findAllByType() {
        String findType = "Электронный";
        List<Book> result = new ArrayList<>();
        try {
            result = bookDaoTest.findAllByType(findType);
            for(Book book : result) {
                System.out.println(book.toString());
            }
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertTrue(!result.isEmpty());
    }

    @Test
    void getPageBooks() {
        long page = 1;
        List<Book> result = new ArrayList<>();
        try {
            result = bookDaoTest.getPageBooks(page);
            for(Book book : result) {
                System.out.println(book.toString());
            }
        }catch (DAOException e){}
        assertTrue(result.size() > 1);      // при условии, что в БД уже находится > 1 книги
    }

    @Test
    void updateById() {
        Book book = new Book(1,"Update Author", "Test", "Test type");
        long id = 1;
        int actual = 0;
        int expected = 1;
        try {
            actual = bookDaoTest.updateById(book, id);
        }catch (DAOException e) {}
        assertEquals(expected, actual);
    }

    @Test
    void getBook() {
        long bookId = 4;
        Optional<Book> book = null;
        try {
            book = bookDaoTest.getBook(bookId);
            System.out.println(book.toString());
        }catch (DAOException e) {}
        assertTrue(book.isPresent());
    }

    @Test
    void getMaxId() {
        long maxId = 0;
        try {
            maxId = bookDaoTest.getMaxId();
            System.out.println("Max id - " + maxId);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        assertTrue(maxId != 0);
    }
}