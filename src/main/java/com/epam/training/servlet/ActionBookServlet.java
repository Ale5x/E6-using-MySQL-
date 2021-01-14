package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.Book;
import com.epam.training.model.User;
import com.epam.training.service.BookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ActionBook")
public class ActionBookServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ActionBookServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private BookService bookService = new BookService(daoHelperFactory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            String bookId = req.getParameter("bookId");
            String resultTask = "";
            if (bookId != "") {
                bookService.removeBook(user, Long.parseLong(bookId));
                resultTask = "Книга успешна удалена";
                req.setAttribute("resultTask", resultTask);
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/actionBook.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            String bookId = req.getParameter("bookId");
            String author = req.getParameter("author");
            String title = req.getParameter("title");
            String type = req.getParameter("type");
            String resultTask = "";
            if (bookId != "" & author != "" & title != "" & type != "") {
                bookService.updateBook(user, new Book(author, title, type), Long.parseLong(bookId));
                resultTask = "Данные книги успешно обновлены...";
                req.setAttribute("resultTask", resultTask);
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/actionBook.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }
}
