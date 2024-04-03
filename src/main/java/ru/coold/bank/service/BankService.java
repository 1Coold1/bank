package ru.coold.bank.service;

import org.springframework.http.ResponseEntity;
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.LegalForm;

import java.util.List;
import java.util.Optional;

public interface BankService {
    List<Bank> findBanksFilteredAndSorted(Long bankId, String name, String BIC, String orderBy);
    Bank saveBank(Bank bank);
    Bank updateBank(Bank bank, long id);
    void deleteBank(long id);
}
