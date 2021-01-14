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

@WebServlet("/ActionUser")
public class ActionUserServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(ActionBookServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private UserService userService = new UserService(daoHelperFactory);
    private Validator validator = new Validator();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        try {
            String idForm = req.getParameter("userId");
            String deleteForm = req.getParameter("delete");
            String addAdminForm = req.getParameter("addAdmin");
            String addUserForm = req.getParameter("backUser");
            String resultTask = "";
            if (idForm != "") {
                if (!validator.isNull(deleteForm)) {
                    userService.removeAccount(Long.parseLong(idForm));
                    resultTask = "Пользователь удалён...";
                } else if (!validator.isNull(addAdminForm)) {
                    userService.updateAccessUser(user, Long.parseLong(idForm), true);
                    resultTask = "Пользователь добавлен в администраторы...";
                } else if (!validator.isNull(addUserForm)) {
                    userService.updateAccessUser(user, Long.parseLong(idForm), false);
                    resultTask = "Пользователь убран из администраторов и добавлен в пользователи...";
                }
            }
            req.setAttribute("resultTask", resultTask);
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/searchUser.jsp");
            dispatcher.forward(req, resp);
        }catch (ServiceException e) {
            logger.error(e);
        }
    }
}
