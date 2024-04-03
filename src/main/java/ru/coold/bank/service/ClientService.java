package ru.coold.bank.service;

import ru.coold.bank.model.Client;
import ru.coold.bank.model.LegalForm;

import java.util.List;

public interface ClientService {

    List<Client> findClientsFilteredAndSorted(Long clientId, String name, String shortName, String address, LegalForm legalForm, String orderBy);

    Client saveClient(Client client);
    Client updateClient(Client client, long id);
    void deleteClient(long id);
}
