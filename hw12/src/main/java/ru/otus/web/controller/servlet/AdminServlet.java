package ru.otus.web.controller.servlet;

import ru.otus.web.model.cache.CacheEngine;
import ru.otus.web.model.config.Configuration;
import ru.otus.web.model.entity.DataSet;
import ru.otus.web.model.entity.UserDataSet;
import ru.otus.web.model.service.DBService;
import ru.otus.web.model.util.DBHelper;

import javax.annotation.PreDestroy;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminServlet extends HttpServlet {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try (DBService dbService = (DBService) this.getServletContext().getAttribute("dbservice")) {
            if (ADMIN_LOGIN.equals(login) && ADMIN_PASSWORD.equals(password)) {
                try {
                    long id = dbService.save(new UserDataSet("Ivan", 2));
                    dummyLoad();
                    dbService.load(id, UserDataSet.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                resp.getWriter().println(dbService.getCacheEngine().toString());
            } else {
                resp.getWriter().println("Authentication failed. Please, try again!!!");
            }

            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void dummyLoad() {
        int size = 1570;
        List<SoftReference<BigObject>> references = new ArrayList<>(size);

        for (int k = 0; k < size; k++) {
            references.add(new SoftReference<>(new BigObject()));
        }

        int sum = 0;
        for (int k = 0; k < size; k++) {
            if (references.get(k).get() != null) sum++;
        }

        System.out.println("Soft references: " + sum);
    }


    static class BigObject {
        final byte[] array = new byte[1024 * 1024];
    }
}
