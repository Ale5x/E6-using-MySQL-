package com.epam.training.servlet;

import com.epam.training.dao.DaoHelperFactory;
import com.epam.training.exception.ServiceException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/SearchUser")
public class SearchUserServlet extends HttpServlet {

    private final static Logger logger = LogManager.getLogger(SearchUserServlet.class);

    private DaoHelperFactory daoHelperFactory = new DaoHelperFactory();
    private UserService userService = new UserService(daoHelperFactory);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try {
            String userId = req.getParameter("userId");
            String email = req.getParameter("email");
            String showAll = req.getParameter("allUser");
            String showAdmin = req.getParameter("allAdmin");
            Optional<User> optionalUser;
            List<User> userList = new ArrayList<>();
            if (userId != "") {
                optionalUser = userService.showUserById(user, Long.valueOf(userId));
                userList.add(new User(optionalUser.map(User::getUserId).orElse(0l),
                        optionalUser.map(User::getLogin).orElse("-"),
                        optionalUser.map(User::getEmail).orElse("-"),
                        optionalUser.map(User::getPassword).orElse("-"),
                        optionalUser.map(User::isAdminAccess).orElse(false)));
                req.setAttribute("userResult", userList);
            } else if (email != "") {
                optionalUser = userService.showUserByEmail(user, email);
                userList.add(new User(optionalUser.map(User::getUserId).orElse(0l),
                        optionalUser.map(User::getLogin).orElse("-"),
                        optionalUser.map(User::getEmail).orElse("-"),
                        optionalUser.map(User::getPassword).orElse("-"),
                        optionalUser.map(User::isAdminAccess).orElse(false)));
                req.setAttribute("userResult", userList);
            } else if (showAll != "") {
                userList = userService.showAllUsers(user);
                req.setAttribute("userResult", userList);
            } else if (showAdmin != "") {
                userList = userService.showAllAdmin(user);
                req.setAttribute("userResult", userList);
            }
            RequestDispatcher dispatcher = req.getRequestDispatcher("jsp/searchUser.jsp");
            dispatcher.forward(req, resp);
        } catch (ServiceException e){
            logger.error(e);
        }
    }
}
