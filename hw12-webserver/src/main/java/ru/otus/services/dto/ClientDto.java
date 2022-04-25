package ru.otus.services.dto;

import ru.otus.crm.model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDto {
    private Long id;
    private String name;
    private String address;
    private List<PhoneDto> phones = new ArrayList<>();

    public ClientDto(Long id, String name, String address, List<PhoneDto> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }

    public static ClientDto create(Client client){
        return new ClientDto(client.getId(), client.getName(), client.getAddress().getStreet(), PhoneDto.createList(client.getPhones()));
    }
}
