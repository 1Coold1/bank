package ru.coold.bank.service.Impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.repository.ClientRepository;
import ru.coold.bank.service.ClientService;
import ru.coold.bank.service.NotFoundException;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    @Override
    public List<Client> findClientsFilteredAndSorted(Long clientId, String name, String shortName, String address, LegalForm legalForm, String orderBy)
    {
        List<Client> clientsList = repository.findAll();
        if (orderBy != null) {
            switch (orderBy) {
                case "clientId":
                    clientsList = repository.findAllByOrderByClientId();
                    break;
                case "name":
                    clientsList = repository.findAllByOrderByName();
                    break;
                case "shortName":
                    clientsList = repository.findAllByOrderByShortName();
                    break;
                case "address":
                    clientsList = repository.findAllByOrderByAddress();
                    break;
                case "legalForm":
                    clientsList = repository.findAllByOrderByLegalForm();
                    break;
                default:
                    // Возвращаем список клиентов по умолчанию
                    clientsList = repository.findAll();
                    break;
            }
        }

        if (clientId != null) {
            clientsList = clientsList
                    .stream()
                    .filter(element -> element.getClientId() == clientId)
                    .toList();
        }
        if (name != null) {
            clientsList = clientsList
                    .stream()
                    .filter(element -> element.getName().equals(name))
                    .toList();
        }
        if (shortName != null) {
            clientsList = clientsList
                    .stream()
                    .filter(element -> element.getShortName().equals(shortName))
                    .toList();
        }
        if (address != null) {
            clientsList = clientsList
                    .stream()
                    .filter(element -> element.getAddress().equals(address))
                    .toList();
        }
        if (legalForm != null) {
            clientsList = clientsList
                    .stream()
                    .filter(element -> element.getLegalForm().equals(legalForm))
                    .toList();
        }
        if (clientsList.isEmpty()) { throw new NotFoundException("Clients not found"); }
        else return clientsList;
    };

    @Override
    public Client saveClient(Client client) {
        return repository.save(client);
    }

    @Override
    public Client updateClient(Client client, long id) {
        repository.findById(id).orElseThrow(()-> new NotFoundException("Client with id "+id+" not found"));
        client.setClientId(id);
        return repository.save(client);
    }

    @Override
    @Transactional
    public void deleteClient(long id) {
        repository.findById(id).orElseThrow(()-> new NotFoundException("Client with id "+id+" not found"));
        repository.deleteById(id);
    }
}
