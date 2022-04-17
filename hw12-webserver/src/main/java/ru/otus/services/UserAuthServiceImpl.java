package ru.otus.services;

import ru.otus.crm.service.DBServiceUserClient;
import ru.otus.dao.UserDao;
import ru.otus.model.User;

public class UserAuthServiceImpl implements UserAuthService {

    private final DBServiceUserClient dbService;

    public UserAuthServiceImpl(DBServiceUserClient dbService) {
        this.dbService = dbService;
    }

    @Override
    public boolean authenticate(String login, String password) {
        User user = dbService.getUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

}
