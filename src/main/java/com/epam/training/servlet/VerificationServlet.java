package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.User;
import com.epam.training.service.BookService;
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
import java.util.Optional;

@WebServlet("/Index")
public class VerificationServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(VerificationServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private UserService userService = new UserService(daoHelperFactory);
    private BookService bookService = new BookService(daoHelperFactory);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
        if (email != "" && password != "") {
            Optional<User> optionalUser = userService.verification(new User(email, password));
            if (optionalUser.isPresent()) {
                User user = new User();
                user.setUserId(optionalUser.get().getUserId());
                user.setEmail(optionalUser.get().getEmail());
                user.setLogin(optionalUser.get().getLogin());
                user.setAdminAccess(optionalUser.get().isAdminAccess());
                user.setPassword(optionalUser.get().getPassword());
                session.setAttribute("countUser", userService.showMaxId());
                session.setAttribute("countBook", bookService.showMaxId());
                session.setAttribute("user", user);
                RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/home.jsp");
                dispatcher.forward(req, resp);
            } else {
                String resultTask = "Неправильный логин или пароль! Зарегистрируйтесь, чтобы войти в систему...";
                req.setAttribute("resultTask", resultTask);
                RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/messagePage.jsp");
                dispatcher.forward(req, resp);
            }
        }
        } catch (ServiceException e) {
            logger.error(e);
        }
        resp.sendRedirect("index.jsp");
    }
}
