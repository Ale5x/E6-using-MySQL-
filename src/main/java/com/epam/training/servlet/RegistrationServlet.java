package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
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
import java.io.IOException;

@WebServlet("/Registration")
public class RegistrationServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(RegistrationServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private UserService userService = new UserService(daoHelperFactory);
    private Validator validator = new Validator();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String resultTask = "";
        try {
            if (login != "" && email != "" && password != "") {
                if (validator.isEmail(email) && validator.isPassword(password)) {
                    boolean result = false;
                    result = userService.registrationUser(email, login, password);
                    if (result) {
                        resultTask = "Регистрация прошла успешна. Войдите в систему...";
                    } else {
                        resultTask = "Email занят...";
                    }
                    req.setAttribute("resultTask", resultTask);
                }
                if(!validator.isPassword(password)) {
                    resultTask = "Ошибка.\nПароль указан некорректно... Пароль должен быть из минимум шести символов, " +
                            "минимум одна буква и одна цифра...";
                }
                if(!validator.isEmail(email)) {
                    resultTask = "Ошибка.\nEmail указан некорректно...";
                }
            } else {
                resultTask = "Для регистрации необходимо заполнить все поля...";
            }
            req.setAttribute("resultTask", resultTask);
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/messagePage.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }
}
