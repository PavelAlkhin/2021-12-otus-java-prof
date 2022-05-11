package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;

@Table("phone")
public class Phone implements Cloneable {
    @Id
    @Nonnull
    private final Long id;

    private final String number;
    @Nonnull
    private final Long clientId;

    @PersistenceConstructor
    public Phone(Long id, String number, Long clientId) {
        this.id = id;
        this.number = number;
        this.clientId = clientId;
    }

    public Phone(String number, Long clientId) {
        this(null, number, clientId);
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public Phone clone() {
        return new Phone(this.id, this.number, this.clientId);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
                '}';
    }
}
