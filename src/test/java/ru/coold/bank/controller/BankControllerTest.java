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
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.service.BankService;
import ru.coold.bank.service.ClientService;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BankControllerTest {
    @Mock
    private BankService service;

    @InjectMocks
    private BankController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void findBanks() throws Exception {
        List<Bank> banks = List.of(new Bank());
        when(service.findBanksFilteredAndSorted(1L, "Test Bank", "044525225",  "bankId"))
                .thenReturn(banks);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/api/v1/banks")
                        .param("bankId", "1")
                        .param("name", "Test Bank")
                        .param("BIC", "044525225")
                        .param("orderBy", "bankId"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(banks)));
    }

    @Test
    void saveBank() throws Exception {
        Bank bank = new Bank();
        when(service.saveBank(bank)).thenReturn(bank);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(post("/api/v1/banks/save_bank")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bank)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bank)));
    }

    @Test
    void updateBank() throws Exception {
        long bankId = 1L;
        Bank bank = new Bank();
        when(service.updateBank(bank, bankId)).thenReturn(bank);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(put("/api/v1/banks/update_bank/{id}", bankId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bank)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(bank)));
    }

    @Test
    void deleteBank() throws Exception{
        long bankId = 1L;

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(delete("/api/v1/banks/delete_bank/{id}", bankId))
                .andExpect(status().isOk());

        verify(service).deleteBank(bankId);
    }
}