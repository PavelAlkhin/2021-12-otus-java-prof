package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    @Query("select * from address")
    List<Address> findAll();

    Optional<Address> findByStreet(String street);

    List<Address> findAddressByClientId(Long clientId);

}
