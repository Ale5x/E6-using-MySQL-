package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.Book;
import com.epam.training.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/SearchBook")
public class SearchBookServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(SearchBookServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private BookService bookService = new BookService(daoHelperFactory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String author = req.getParameter("author");
            String title = req.getParameter("title");
            String type = req.getParameter("type");
            String word = req.getParameter("word");
            String bookId = req.getParameter("bookId");
            List<Book> result = new ArrayList<>();
            long countResult = 0;
            if (author != "") {
                result = bookService.showAllByAuthor(author);
                countResult = result.size();
                req.setAttribute("countResult", countResult);
                req.setAttribute("listResult", result);
            } else if (title != "") {
                result = bookService.showAllByTitle(title);
                countResult = result.size();
                req.setAttribute("countResult", countResult);
                req.setAttribute("listResult", result);
            } else if (type != "") {
                result = bookService.showAllByType(type);
                countResult = result.size();
                req.setAttribute("countResult", countResult);
                req.setAttribute("listResult", result);
            } else if (word != "") {
                result = bookService.showBookAllByWord(word);
                countResult = result.size();
                req.setAttribute("countResult", countResult);
                req.setAttribute("listResult", result);
            } else if (bookId != "") {
                Book book = new Book();
                Optional<Book> optBook = bookService.showBookById(Long.valueOf(bookId));
                result.add(book);
                countResult = result.size();
                req.setAttribute("countResult", countResult);
                req.setAttribute("listResult", result);
            } else {
                String resultTask = "Пустые поля, заполните все поля";
                req.setAttribute("resultTask", resultTask);
                resp.sendRedirect("jsp/messagePage.jsp");
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher("/jsp/searchBook.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }
}
