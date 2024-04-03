package ru.coold.bank.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.Deposit;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.service.ClientService;
import ru.coold.bank.service.DepositService;

import java.sql.Date;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class DepositControllerTest {
    @Mock
    private DepositService service;

    @InjectMocks
    private DepositController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findDeposits() throws Exception {
        List<Deposit> deposits = List.of(new Deposit());
        when(service.findDepositsFilteredAndSorted(1L,null,null,null,0.14,12,"depositId"))
                .thenReturn(deposits);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/api/v1/deposits")
                        .param("depositId", "1")
                        .param("percent", "0.14")
                        .param("termMonths", "12")
                        .param("orderBy", "depositId"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(deposits)));
    }

    @Test
    void saveDeposit() throws Exception {
        Deposit deposit = new Deposit();
        when(service.saveDeposit(deposit)).thenReturn(deposit);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(post("/api/v1/deposits/save_deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deposit)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(deposit)));
    }

    @Test
    void updateDeposit() throws Exception {
        long depositId = 1L;
        Deposit deposit = new Deposit();
        when(service.updateDeposit(deposit, depositId)).thenReturn(deposit);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(put("/api/v1/deposits/update_deposit/{id}", depositId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deposit)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(deposit)));
    }

    @Test
    void deleteDeposit() throws Exception {
        long depositId = 1L;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(delete("/api/v1/deposits/delete_deposit/{id}", depositId))
                .andExpect(status().isOk());

        verify(service).deleteDeposit(depositId);
    }
}