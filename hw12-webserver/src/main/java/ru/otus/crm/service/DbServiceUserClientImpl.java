package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;
import ru.otus.model.User;
import ru.otus.services.dto.ClientDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbServiceUserClientImpl implements DBServiceUserClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceUserClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final DataTemplate<User> userDataTemplate;

    public DbServiceUserClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, DataTemplate<User> userDataTemplate) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.userDataTemplate = userDataTemplate;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            return clientCloned;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientOptional = clientDataTemplate.findById(session, id);
            log.info("client: {}", clientOptional);
            return clientOptional;
        });
    }

    @Override
    public List<ClientDto> findAllClients() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            List<ClientDto> listDto = new ArrayList<>();
            clientList.forEach(c -> {
                listDto.add(ClientDto.create(c));
            });
            log.info("clientList:{}", clientList);
            return listDto;
        });
    }

    @Override
    public User saveUser(User user) {
        return transactionManager.doInTransaction(session -> {
            var userCloned = user.clone();
            if (user.getId() == null) {
                userDataTemplate.insert(session, userCloned);
                log.info("created user: {}", userCloned);
                return userCloned;
            }
            userDataTemplate.update(session, userCloned);
            log.info("updated user: {}", userCloned);
            return userCloned;
        });
    }

    @Override
    public Optional<User> getUser(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userOptional = userDataTemplate.findById(session, id);
            log.info("user: {}", userOptional);
            return userOptional;
        });
    }

    @Override
    public List<User> findAllUsers() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var userList = userDataTemplate.findAll(session);
            log.info("clientList:{}", userList);
            return userList;
        });
    }

    @Override
    public User getUserByLogin(String login) {
        return (User) transactionManager.doInReadOnlyTransaction(session -> {
            var userList = userDataTemplate.findByEntityField(session, "login", login);
            if (userList.size() > 0) {
                log.info("clientList:{}", userList);
                return userList.get(0);
            }
            return null;
        });
    }
}
