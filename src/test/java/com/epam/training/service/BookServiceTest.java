package com.epam.training.service;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {

    BookService bookServiceTest;

    @BeforeEach
    void setUp() {
        DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
        bookServiceTest = new BookService(daoHelperFactory);
    }

    @Test
    void showAllByAuthor() {
        List<Book> bookList = new ArrayList<>();
        String author = "толкин";
        try {
            bookList = bookServiceTest.showAllByAuthor(author);
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(bookList.isEmpty());
    }

    @Test
    void showAllByNotFindAuthor() {
        List<Book> bookList = new ArrayList<>();
        String author = "Asterix";
        try {
            bookList = bookServiceTest.showAllByAuthor(author);
            System.out.println("List size - " + bookList.size());
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(bookList.isEmpty());
    }


    @Test
    void showAllByTitle() {
        String title = "Колец";
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showAllByTitle(title);
            System.out.println("List size - " + bookList.size());
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(bookList.isEmpty());
    }

    @Test
    void showAllByNotFindTitle() {
        String title = "52525";
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showAllByTitle(title);
            System.out.println("List size - " + bookList.size());
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(bookList.isEmpty());
    }

    @Test
    void showAllByType() {
        String type = "Бумажный";
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showAllByType(type);
            System.out.println("List size - " + bookList.size());
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(bookList.isEmpty());
    }

    @Test
    void showAllByTypeIgnoreCase() {
        String type = "бумаЖный";
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showAllByType(type);
            System.out.println("List size - " + bookList.size());
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(bookList.isEmpty());
    }

    @Test
    void showAllByIncorrectType() {
        String type = "type";
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showAllByType(type);
            System.out.println("List size - " + bookList.size());
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(bookList.isEmpty());
    }

    @Test
    void showAllByNullType() {
        String type = null;
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showAllByType(type);
            System.out.println("List size - " + bookList.size());
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(bookList.isEmpty());
    }

    @Test
    void showAllBook() {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showAllBook();
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(bookList.isEmpty());
    }

    @Test
    void showBookById() {
        Optional<Book> optionalBook = null;
        long id = 4;
        try {
            optionalBook = bookServiceTest.showBookById(id);
            System.out.println(optionalBook.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(optionalBook.isPresent());
    }

    @Test
    void showBookByIncorrectId() {
        Optional<Book> optionalBook = null;
        long maxId = 9;
        try {
            optionalBook = bookServiceTest.showBookById(maxId);
            System.out.println(optionalBook.toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(optionalBook.get().getAuthor() == null);
    }

    @Test
    void showPageByNumber() {
        long page = 1;
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showPageByNumber(page);
            for(Book book : bookList) {
                System.out.println(book);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertFalse(bookList.isEmpty());
    }

    @Test
    void showPageByMaxNumber() {
        long page = 10;
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookServiceTest.showPageByNumber(page);
            for(Book book : bookList) {
                System.out.println(book.toString());
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        assertTrue(bookList.isEmpty());
    }

    @Test
    void showMaxId() {
        long maxId = 0;
        maxId = bookServiceTest.showMaxId();
        assertTrue(maxId > 0);
    }
}