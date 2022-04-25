package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DBServiceUserClient;
import ru.otus.dao.UserDao;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.UserAuthService;
import ru.otus.servlet.AdminApiServlet;
import ru.otus.servlet.AdminServlet;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;

import java.util.Arrays;

public class UsersWebServerWithFilterBasedSecurity extends UsersWebServerSimple {
    private final UserAuthService authService;
    private final DBServiceUserClient dbService;
    private final Gson gson;

    public UsersWebServerWithFilterBasedSecurity(int port,
                                                 UserAuthService authService,
                                                 UserDao userDao,
                                                 Gson gson,
                                                 TemplateProcessor templateProcessor,
                                                 DBServiceUserClient dbService) {
        super(port, userDao, gson, templateProcessor, dbService);
        this.authService = authService;
        this.gson = gson;
        this.dbService = dbService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");

        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(templateProcessor, dbService)), "/admin/*");
        servletContextHandler.addServlet(new ServletHolder(new AdminApiServlet(gson, dbService)), "/api/admin/*");

        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
