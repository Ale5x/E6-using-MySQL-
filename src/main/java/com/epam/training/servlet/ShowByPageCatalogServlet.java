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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ShowCatalog")
public class ShowByPageCatalogServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ShowByPageCatalogServlet.class);

    private BookService bookService = new BookService(new DaoHelperFactory());
    private Validator validator = new Validator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession();
            String next = req.getParameter("next");
            String back = req.getParameter("back");
            String jump = req.getParameter("jump");
            String start = req.getParameter("start");
            List<Book> bookResult = new ArrayList<>();
            long maxBookId = bookService.showMaxId();
            long page = 1l;
            Long getReqPage = (Long) session.getAttribute("countPage1");
            if(validator.isNull(getReqPage)) {
                page = 1l;
                session.setAttribute("countPage", page);
            }else {
                page = (Long) session.getAttribute("countPage");
            }
            if (!validator.isNull(start)) {
                page = 1l;
                bookResult = bookService.showPageByNumber(page);
            } else if (!validator.isNull(next)) {
                page++;
                if ((page * 20) >= maxBookId) {
                    page = (maxBookId - 20) / 20;
                }
                bookResult = bookService.showPageByNumber(page);
            } else if (!validator.isNull(back)) {
                page--;
                if (page <= 0) {
                    page = 1l;
                }
                bookResult = bookService.showPageByNumber(page);
            } else if (jump != "") {
                if(Long.parseLong(jump) * 20 < maxBookId)
                bookResult = bookService.showPageByNumber(Long.parseLong(jump));
            }

            session.setAttribute("countPage", page);
            req.setAttribute("page", page);
            req.setAttribute("bookResult", bookResult);

            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/showCatalog.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }
}
