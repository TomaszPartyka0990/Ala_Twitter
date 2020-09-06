package pl.sda.partyka.controller;

import pl.sda.partyka.model.AppUser;
import pl.sda.partyka.model.dao.AppUserDAO;
import pl.sda.partyka.model.dao.impl.MySQLAppUserDAOImpl;
import pl.sda.partyka.services.UserManagementService;
import pl.sda.partyka.services.impl.UserManagementServiceImpl;
import pl.sda.partyka.validation.ValidationError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static pl.sda.partyka.utils.ServletUtils.*;

@WebServlet(name = "loginServlet", urlPatterns = {"/", "/login"})
public class LoginServlet extends HttpServlet {
    private AppUserDAO userDao;
    private UserManagementService userService;

    @Override
    public void init() throws ServletException {
        this.userDao = new MySQLAppUserDAOImpl();
        this.userService = new UserManagementServiceImpl(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        AppUser user = new AppUser();
        user.setLogin(req.getParameter(USER_LOGIN));
        user.setPassword(req.getParameter(USER_PASSWORD));

        Set<ValidationError> errors = userService.validateLogUser(user);
        if (errors.isEmpty()) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            req.setAttribute(ERRORS, errors);
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
