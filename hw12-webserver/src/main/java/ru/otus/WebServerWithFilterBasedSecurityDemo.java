package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceUserClient;
import ru.otus.crm.service.DbServiceUserClientImpl;
import ru.otus.dao.InMemoryUserDao;
import ru.otus.dao.UserDao;
import ru.otus.model.User;
import ru.otus.server.UsersWebServer;
import ru.otus.server.UsersWebServerWithFilterBasedSecurity;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;
import ru.otus.services.UserAuthService;
import ru.otus.services.UserAuthServiceImpl;

import java.util.List;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithFilterBasedSecurityDemo {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final Logger log = LoggerFactory.getLogger(WebServerWithFilterBasedSecurityDemo.class);
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {

        DBServiceUserClient dbService = createHibernateService();

        UserDao userDao = new InMemoryUserDao();
        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl(dbService);

        UsersWebServer usersWebServer = new UsersWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, userDao, gson, templateProcessor, dbService);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static DBServiceUserClient createHibernateService(){
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        Class[] classes = new Class[4];
        classes[0] = Client.class;
        classes[1] = Address.class;
        classes[2] = Phone.class;
        classes[3] = User.class;

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, classes);
        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var userTemplate = new DataTemplateHibernate<>(User.class);

        DbServiceUserClientImpl dbServicet = new DbServiceUserClientImpl(transactionManager, clientTemplate, userTemplate);

        fillExamplesClients(dbServicet);
        fillUsers(dbServicet);

        return dbServicet;
    }

    private static void fillExamplesClients(DbServiceUserClientImpl dbService) {
        Address address1 = new Address("street 1");
        Address address2 = new Address("street 2");
        List<Phone> phoneList = List.of(new Phone("263876236876"));
        List<Phone> phoneList2= List.of(new Phone("263876236876"));
        dbService.saveClient(new Client("Client 1", address1, phoneList));
        dbService.saveClient(new Client("Client 2", address2, phoneList2));
    }

    private static void fillUsers(DbServiceUserClientImpl dbService){
        dbService.saveUser(new User(1L, "Крис Гир", "user1", "11111"));
        dbService.saveUser(new User(2L, "Ая Кэш", "user2", "11111"));
        dbService.saveUser(new User(3L, "Десмин Боргес", "user3", "11111"));
        dbService.saveUser(new User(4L, "Кетер Донохью", "user4", "11111"));
        dbService.saveUser(new User(5L, "Стивен Шнайдер", "user5", "11111"));
        dbService.saveUser(new User(6L, "Джанет Вэрни", "user6", "11111"));
        dbService.saveUser(new User(7L, "Брэндон Смит", "user7", "11111"));
    }
}
