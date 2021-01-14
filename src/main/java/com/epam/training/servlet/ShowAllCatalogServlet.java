package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.Book;
import com.epam.training.service.BookService;
import com.epam.training.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ShowAllCatalog")
public class ShowAllCatalogServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ShowAllCatalogServlet.class);

    private Validator validator = new Validator();
    private BookService bookService = new BookService(new DaoHelperFactory());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String showAll = req.getParameter("showAll");
        List<Book> bookResult;
        if(!validator.isNull(showAll)) {
            try {
                bookResult = bookService.showAllBook();
                req.setAttribute("AllBookResult", bookResult);
            } catch (ServiceException e) {
                logger.error(e);
            }
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/showCatalog.jsp");
        dispatcher.forward(req, resp);
    }
}
