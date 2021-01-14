package com.epam.training.dao.impl;

import com.epam.training.constant.TableBD;
import com.epam.training.dao.AbstractDao;
import com.epam.training.dao.BookDao;
import com.epam.training.exception.DAOException;
import com.epam.training.mapper.BookRowMapper;
import com.epam.training.model.Book;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class BookDaoImpl extends AbstractDao<Book> implements BookDao {

    private int limitBooksOnPage = 20;
    private final String ADD_BOOK = "insert into book_catalog (author, title, type) values (?, ?, ?);";
    private final String DELETE_BOOK_BY_ID = "delete from book_catalog where book_id=?;";
    private final String FIND_ALL_BY_TYPE = "SELECT * FROM book_catalog WHERE type=?";
    private final String UPDATE_BOOK = "update book_catalog set author=?, title=?, type=? where book_id=?;";
    private final String SELECT_SINGLE_BOOK = "select * from book_catalog where book_id=?;";
    private final String FIND_MAX_ID = "SELECT max(user_id) from user_data;";
    private final String FIND_ALL = "SELECT * from book_catalog;";

    private Connection connection;

    public BookDaoImpl(Connection connection) {
        super(connection);
    }

    @Override
    public int create(Book book) throws DAOException {
        return save(ADD_BOOK, book.getAuthor(), book.getTitle(), book.getType());
    }

    @Override
    public int delete(Long id) throws DAOException {
       return delete(DELETE_BOOK_BY_ID, id);
    }

    @Override
    public List<Book> findAll() throws DAOException {
        return findAll(FIND_ALL);
    }

    @Override
    public List<Book> findAllByTitle(String title) throws DAOException {
        final String FIND_ALL_BY_TITLE = "SELECT * FROM book_catalog WHERE title LIKE '%" + title + "%';";
        return findAll(FIND_ALL_BY_TITLE);
    }

    @Override
    public List<Book> findAllByAuthor(String author) throws DAOException {
        final String FIND_ALL_BY_AUTHOR = "SELECT * FROM book_catalog WHERE author LIKE '%" + author + "%';";
        return findAll(FIND_ALL_BY_AUTHOR);
    }

    @Override
    public List<Book> findAllByType(String type) throws DAOException {
        return findAll(FIND_ALL_BY_TYPE, type);
    }

    @Override
    public List<Book> getPageBooks(Long page) throws DAOException {
        long offset = 0;
        offset = (page * limitBooksOnPage) - limitBooksOnPage;
        final String SELECT_ALL_ON_PAGE = "select * from book_catalog limit 20 offset " + offset;
        return findAll(SELECT_ALL_ON_PAGE);
    }

    @Override
    public int updateById(Book book, Long id) throws DAOException {
        return updateData(UPDATE_BOOK, book.getAuthor(), book.getTitle(), book.getType(), id);
    }

    @Override
    public Optional<Book> getBook(Long id) throws DAOException {
        return getSingleResult(SELECT_SINGLE_BOOK, new BookRowMapper(), id);
    }

    @Override
    public long getMaxId() throws DAOException{
        return findMaxId(FIND_MAX_ID);
    }

    @Override
    protected String getTableName() {
        return TableBD.TABLE_CATALOG_BOOK;
    }
}
