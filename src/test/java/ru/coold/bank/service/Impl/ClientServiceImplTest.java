package ru.coold.bank.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.repository.ClientRepository;
import ru.coold.bank.service.ClientService;
import ru.coold.bank.service.NotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ClientServiceImpl service;

    @Test
    void findClientsFilteredAndSorted() {
        Client client = new Client();
        client.setClientId(1L);
        client.setName("Test Client");
        client.setShortName("TC");
        client.setAddress("Test Address");
        client.setLegalForm(LegalForm.LLC);

        when(repository.findAll()).thenReturn(Collections.singletonList(client));
        when(repository.findAllByOrderByClientId()).thenReturn(Collections.singletonList(client));

        List<Client> result = service.findClientsFilteredAndSorted(1L, "Test Client", "TC", "Test Address", LegalForm.LLC, "clientId");

        verify(repository).findAll();
        verify(repository).findAllByOrderByClientId();

        assertEquals(1, result.size());
        assertEquals(client, result.get(0));
    }

    @Test
    void saveClient() {
        Client client = new Client();
        client.setName("Test Client");

        when(repository.save(client)).thenReturn(client);

        Client savedClient = service.saveClient(client);

        verify(repository).save(client);

        assertEquals(client, savedClient);
    }

    @Test
    void updateClient() {
        long clientId = 1L;
        Client client = new Client();
        client.setClientId(clientId);
        client.setName("Updated Client");

        when(repository.findById(clientId)).thenReturn(Optional.of(client));
        when(repository.save(client)).thenReturn(client);

        Client updatedClient = service.updateClient(client, clientId);

        verify(repository).findById(clientId);
        verify(repository).save(client);

        assertEquals(client, updatedClient);
    }

    @Test
    void deleteClient() {
        long clientId = 1L;
        Client client = new Client();
        client.setClientId(clientId);

        when(repository.findById(clientId)).thenReturn(Optional.of(client));

        service.deleteClient(clientId);

        verify(repository).findById(clientId);
        verify(repository).deleteById(clientId);
    }

    @Test
    void deleteClient_ClientNotFound() {
        long clientId = 1L;

        when(repository.findById(clientId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.deleteClient(clientId));

        verify(repository).findById(clientId);
        verify(repository, never()).deleteById(clientId);
    }
}