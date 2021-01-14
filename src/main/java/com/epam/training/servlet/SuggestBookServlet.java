package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.Book;
import com.epam.training.model.User;
import com.epam.training.service.UserService;
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

@WebServlet("/SuggestBook")
public class SuggestBookServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(SuggestBookServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private UserService userService = new UserService(daoHelperFactory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            String author = req.getParameter("author");
            String title = req.getParameter("title");
            String type = req.getParameter("type");
            String text = req.getParameter("text");

            userService.suggestBookMessage(user, new Book(author, title, type), text);
            String resultTask = "Сообщение отправлено...";
            req.setAttribute("resultTask", resultTask);
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/home.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }
}
