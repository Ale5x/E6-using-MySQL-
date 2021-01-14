package com.epam.training.mapper;

import com.epam.training.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookRowMapper implements RowMapper<Book> {

    @Override
    public Book map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("book_id");
        String title = resultSet.getString("title");
        String author = resultSet.getString("author");
        String type = resultSet.getString("type");
        return new Book(id, author, title, type);
    }
}
