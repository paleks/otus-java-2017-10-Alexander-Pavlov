package ru.otus.web.controller;

import ru.otus.web.model.cache.CacheEngine;
import ru.otus.web.model.entity.DataSet;
import ru.otus.web.model.entity.UserDataSet;
import ru.otus.web.model.service.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";

    private static final String LOGIN_PARAM = "login";
    private static final String PASSWORD_PARAM = "password";

    private static final String DB_SERVICE_ATTR = "dbservice";
    private static final String CACHE_ENGINE_ATTR = "cache";

    private static final int USERS_AMOUNT = 100;

    private DBService dbService;
    private CacheEngine<String, ? extends DataSet> cacheEngine;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            this.dbService = (DBService) this.getServletContext().getAttribute(DB_SERVICE_ATTR);
            this.cacheEngine = (CacheEngine<String, ? extends DataSet>) this.getServletContext().getAttribute(CACHE_ENGINE_ATTR);

            String login = req.getParameter(LOGIN_PARAM);
            String password = req.getParameter(PASSWORD_PARAM);

            if (ADMIN_LOGIN.equals(login) && ADMIN_PASSWORD.equals(password)) {
                useDataBaseService();

                Map<String, Object> pageVariables = createPageVariablesMap(req);

                resp.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
            } else {
                resp.getWriter().println("Authentication failed. Please, try again!!!");
            }
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);

            // trying to close JDBC connection
            this.dbService.close();
        } catch (IOException | ServletException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong with closing JDBC connection");
        }
    }

    private void useDataBaseService() throws SQLException {
        long id = 0;
        for (int i = 0; i < USERS_AMOUNT; i++) {
            id = this.dbService.save(new UserDataSet("Ivan", 2));
        }
        for (long i = id; i > id - USERS_AMOUNT; i--) {
            this.dbService.load(i, UserDataSet.class);
        }
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();

        pageVariables.put("max", this.cacheEngine.getMaxElements());
        pageVariables.put("life", this.cacheEngine.getLifeTime());
        pageVariables.put("idle", this.cacheEngine.getIdleTime());
        pageVariables.put("eternal", String.valueOf(this.cacheEngine.isEternal()));
        pageVariables.put("hits", this.cacheEngine.getHitCount());
        pageVariables.put("miss", this.cacheEngine.getMissCount());

        return pageVariables;
    }
}
