package ru.otus.crm.service;

import ru.otus.crm.model.Client;
import ru.otus.model.User;
import ru.otus.services.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface DBServiceUserClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<ClientDto> findAllClients();

    User saveUser(User user);

    Optional<User> getUser(long id);

    List<User> findAllUsers();

    User getUserByLogin(String login);
}
