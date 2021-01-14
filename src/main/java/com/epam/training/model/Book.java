package com.epam.training.model;

import java.util.Objects;

public class Book {

    private long bookId;
    private String author;
    private String title;
    private String type;

    public Book() {

    }

    public Book(String author, String title, String type) {
        this.author = author;
        this.title = title;
        this.type = type;
    }

    public Book(long bookId, String author, String title, String type) {
        this.bookId = bookId;
        this.author = author;
        this.title = title;
        this.type = type;
    }

    /* public String getType() {
         return type;
     }



     public void setType(String type) {
         this.type = type;
     }
 */
    public long getBookId() {return bookId;}

    public void setBookId(long bookId) {this.bookId = bookId; }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "id - " + bookId + ". «" + author + " - " + title + "» - " + type + ".";
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj != null || obj.getClass() != this.getClass()) {
            return false;
        }
        Book book = (Book) obj;
        return bookId == book.bookId &&
                (author == book.author || (author != null && author.equals(book.getAuthor())))
                && (title == book.title || (title != null && title.equals(book.getTitle())))
                && (type == book.type || (type != null && type.equals(book.getType())));
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, author, title, type);
    }
}