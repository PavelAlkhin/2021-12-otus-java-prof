package ru.otus.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("address")
public class Address implements Cloneable {
    @Id
    private final Long id;

    private final String street;
    @Nonnull
    private final Long clientId;

    @PersistenceConstructor
    public Address(Long id, String street, Long clientId) {
        this.id = id;
        this.street = street;
        this.clientId = clientId;
    }

    public Address(Long id, String street) {
        this(id, street, null);
    }

    public Address(String street) {
        this(null, street);
    }

    public Address(String street, Long clientId) {
        this(null, street, clientId);
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public Address clone() {
        return new Address(this.id, this.street, this.clientId);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }
}
