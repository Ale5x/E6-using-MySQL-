package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
import com.epam.training.model.User;
import com.epam.training.service.UserService;
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

@WebServlet("/update")
public class UpdateDataServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(UpdateDataServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private UserService userService = new UserService(daoHelperFactory);
    private Validator validator = new Validator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            String login = req.getParameter("login");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String resultTask = "Данные изменены";
            if (login != "") {
                user.setLogin(login);
            }
            if (email != "") {
                user.setEmail(email);
            }
            if (password != "") {
                user.setPassword(password);
            }
            if (login == "" || email == "" || password == "") {
                resultTask = "Пустые поля... Для изменения личных данных заполните одно поля";
            }
            userService.updateDataUser(user);
            req.setAttribute("resultTask", resultTask);
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/updatePage.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            String delete = req.getParameter("deletePage");
            if (!validator.isNull(delete)) {
                userService.removeAccount(user.getUserId());
                System.out.println("Delete page");
            }
            user = null;
            session.setAttribute("user", user);
            resp.sendRedirect("index.jsp");
        }catch (ServiceException e) {
            logger.error(e);
        }
    }
}
