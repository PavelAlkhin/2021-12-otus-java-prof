package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Phone;

import java.util.List;
import java.util.Optional;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    @Query("select * from phone")
    List<Phone> findAll();

    Optional<Phone> findByNumber(String number);

}
