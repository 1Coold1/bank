package ru.coold.bank.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.service.ClientService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @Mock
    private ClientService service;

    @InjectMocks
    private ClientController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void findClients() throws Exception {
        List<Client> clients = List.of(new Client());
        when(service.findClientsFilteredAndSorted(1L, "Test Client", "TC", "Address", LegalForm.LLC, "clientId"))
                .thenReturn(clients);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/api/v1/clients")
                        .param("clientId", "1")
                        .param("name", "Test Client")
                        .param("shortName", "TC")
                        .param("address", "Address")
                        .param("legalForm", "LLC")
                        .param("orderBy", "clientId"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(clients)));
    }

    @Test
    void saveClient() throws Exception {
        Client client = new Client();
        when(service.saveClient(client)).thenReturn(client);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(post("/api/v1/clients/save_client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(client)));
    }

    @Test
    void updateClient() throws Exception {
        long clientId = 1L;
        Client client = new Client();
        when(service.updateClient(client, clientId)).thenReturn(client);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(put("/api/v1/clients/update_client/{id}", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(client)));
    }

    @Test
    void deleteClient() throws Exception {
        long clientId = 1L;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(delete("/api/v1/clients/delete_client/{id}", clientId))
                .andExpect(status().isOk());

        verify(service).deleteClient(clientId);
    }
}