package ru.coold.bank.service.Impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.Deposit;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.repository.ClientRepository;
import ru.coold.bank.repository.DepositRepository;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DepositServiceImplTest {
    @Mock
    private DepositRepository repository;

    @InjectMocks
    private DepositServiceImpl service;

    @Test
    void findDepositsFilteredAndSorted() {
        Deposit deposit = new Deposit();
        Client client = new Client();
        Bank bank = new Bank();
        java.sql.Date date = new Date(System.currentTimeMillis());
        deposit.setDepositId(1L);
        deposit.setClient(client);
        deposit.setBank(bank);
        deposit.setOpeningDate(date);
        deposit.setPercent(0.05);
        deposit.setTermMonths(12);

        when(repository.findAll()).thenReturn(Collections.singletonList(deposit));
        when(repository.findAllByOrderByDepositId()).thenReturn(Collections.singletonList(deposit));

        List<Deposit> result = service.findDepositsFilteredAndSorted(1L, client, bank, date, 0.05,12,"depositId");

        verify(repository).findAll();
        verify(repository).findAllByOrderByDepositId();

        assertEquals(1, result.size());
        assertEquals(deposit, result.get(0));
    }

    @Test
    void saveDeposit() {
        Deposit deposit = new Deposit();
        java.sql.Date date = new Date(System.currentTimeMillis());
        deposit.setOpeningDate(date);

        when(repository.save(deposit)).thenReturn(deposit);

        Deposit savedDeposit = service.saveDeposit(deposit);

        verify(repository).save(deposit);

        assertEquals(deposit, savedDeposit);
    }

    @Test
    void updateDeposit() {
        long depositId = 1L;
        java.sql.Date date = new Date(System.currentTimeMillis());
        Deposit deposit = new Deposit();
        deposit.setDepositId(depositId);
        deposit.setOpeningDate(date);

        when(repository.findById(depositId)).thenReturn(Optional.of(deposit));
        when(repository.save(deposit)).thenReturn(deposit);

        Deposit updatedDeposit = service.updateDeposit(deposit, depositId);

        verify(repository).findById(depositId);
        verify(repository).save(deposit);

        assertEquals(deposit, updatedDeposit);
    }

    @Test
    void deleteDeposit() {
        long depositId = 1L;
        Deposit deposit = new Deposit();
        deposit.setDepositId(depositId);

        when(repository.findById(depositId)).thenReturn(Optional.of(deposit));

        service.deleteDeposit(depositId);

        verify(repository).findById(depositId);
        verify(repository).deleteById(depositId);
    }
}