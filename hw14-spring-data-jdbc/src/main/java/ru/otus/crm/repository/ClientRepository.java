package ru.otus.crm.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

//    @Query("SELECT " +
//            "id AS id, " +
//            "number AS number, " +
//            "address_id AS address_id " +
//            "FROM phone AS p" +
//            "JOIN address AS a" +
//            "on p.address_id = a.id")
    List<Client> findAll();

    Optional<Client> findByName(String name);

    @Query("select * from client where upper(name) = upper(:name)")
    Optional<Client> findByNameIgnoreCase(@Param("name") String name);

    @Modifying
    @Query("update client set name = :newName where id = :id")
    void updateName(@Param("id") long id, @Param("newName") String newName);
}
