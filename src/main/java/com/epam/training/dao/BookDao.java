package com.epam.training.dao;

import com.epam.training.exception.DAOException;
import com.epam.training.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    public int create(Book book) throws DAOException;

    public int delete(Long id) throws DAOException;

    public List<Book> findAllByTitle(String title) throws DAOException;

    public List<Book> findAllByAuthor(String author) throws DAOException;

    public List<Book> findAllByType(String type) throws DAOException;

    public List<Book> findAll() throws DAOException;

    public List<Book> getPageBooks(Long page) throws DAOException;

    public int updateById(Book book, Long id) throws DAOException;

    public Optional getBook(Long id) throws DAOException;

    public long getMaxId() throws DAOException;

}
