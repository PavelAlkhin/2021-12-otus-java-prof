package ru.otus.crm.service;

import ru.otus.crm.model.Client;

import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

public class ClientCache {

    private Map<Long, Client> cache = new WeakHashMap<>();

    public Optional<Client> findClientInCache(Long id){
        return Optional.ofNullable(cache.get(id));
    }

    public void setUpClientInCache(Client client){
        cache.put(client.getId(), client);
    }
}
