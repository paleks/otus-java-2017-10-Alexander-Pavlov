package ru.otus.webapp.controller.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(displayName = "AdminServlet",
        name = "adminServlet",
        urlPatterns = "/admin")
public class AdminServlet extends HttpServlet {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter(LOGIN_PARAM);
        String password = req.getParameter(PASSWORD_PARAM);

        if (ADMIN_LOGIN.equals(login) && ADMIN_PASSWORD.equals(password)) {
            RequestDispatcher disp = req.getRequestDispatcher("/admin.html");
            disp.forward(req, resp);
        } else {
            resp.getWriter().println("Authentication failed. Please, try again!!!");
        }
    }
}
