package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import javax.annotation.Nonnull;
import java.util.Set;

@Table("client")
public class Client {

    @Id
    private final Long id;
    @Nonnull
    private final String name;
    @MappedCollection(idColumn = "client_id")
    private final Address addressId;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;

    @PersistenceConstructor
    public Client(Long id, String name, Address addressId, Set<Phone> phones) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.phones = phones;
    }

    public Client(String name) {
        this(null, name, null, null);
    }

    public Client(String name, Address addressId, Set<Phone> phones) {
        this(null, name, addressId, phones);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Address getAddressId() {
        return addressId;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressId='" + addressId + '\'' +
//                ", phones=" + phones +
                '}';
    }
}
