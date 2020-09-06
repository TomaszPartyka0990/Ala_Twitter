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

@WebServlet(name = "registerServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    private AppUserDAO userDao;
    private UserManagementService userService;

    @Override
    public void init() throws ServletException {
        this.userDao = new MySQLAppUserDAOImpl();
        this.userService = new UserManagementServiceImpl(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AppUser user = new AppUser();
        user.setLogin(request.getParameter(USER_LOGIN));
        user.setEmail(request.getParameter(USER_EMAIL));
        user.setPassword(request.getParameter(USER_PASSWORD));

        Set<ValidationError> errors = userService.validateRegUser(user);
        if (errors.isEmpty()) {
            userService.register(user);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.setAttribute(ERRORS, errors);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}
