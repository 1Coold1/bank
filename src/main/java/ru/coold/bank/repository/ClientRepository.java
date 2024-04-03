package ru.coold.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coold.bank.model.Client;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,Long> {

    List<Client> findAllByOrderByClientId();
    List<Client> findAllByOrderByName();
    List<Client> findAllByOrderByShortName();
    List<Client> findAllByOrderByAddress();
    List<Client> findAllByOrderByLegalForm();

}
