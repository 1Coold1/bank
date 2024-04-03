package ru.coold.bank.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.repository.BankRepository;
import ru.coold.bank.repository.ClientRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceImplTest {
    @Mock
    private BankRepository repository;

    @InjectMocks
    private BankServiceImpl service;
    @Test
    void findBanksFilteredAndSorted() {
        Bank bank = new Bank();
        bank.setBankId(1L);
        bank.setName("Test Bank");
        bank.setBIC("044525225");

        when(repository.findAll()).thenReturn(Collections.singletonList(bank));
        when(repository.findAllByOrderByBankId()).thenReturn(Collections.singletonList(bank));

        List<Bank> result = service.findBanksFilteredAndSorted(1L, "Test Bank", "044525225", "bankId");

        verify(repository).findAll();
        verify(repository).findAllByOrderByBankId();

        assertEquals(1, result.size());
        assertEquals(bank, result.get(0));
    }

    @Test
    void saveBank() {
        Bank bank = new Bank();
        bank.setName("Test Bank");

        when(repository.save(bank)).thenReturn(bank);

        Bank savedBank = service.saveBank(bank);

        verify(repository).save(bank);

        assertEquals(bank, savedBank);
    }

    @Test
    void updateBank() {
        long bankId = 1L;
        Bank bank = new Bank();
        bank.setBankId(bankId);
        bank.setName("Updated Bank");

        when(repository.findById(bankId)).thenReturn(Optional.of(bank));
        when(repository.save(bank)).thenReturn(bank);

        Bank updatedBank = service.updateBank(bank, bankId);

        verify(repository).findById(bankId);
        verify(repository).save(bank);

        assertEquals(bank, updatedBank);
    }

    @Test
    void deleteBank() {
        long bankId = 1L;
        Bank bank = new Bank();
        bank.setBankId(bankId);

        when(repository.findById(bankId)).thenReturn(Optional.of(bank));

        service.deleteBank(bankId);

        verify(repository).findById(bankId);
        verify(repository).deleteById(bankId);
    }
}