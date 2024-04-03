package ru.coold.bank.service.Impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.repository.BankRepository;
import ru.coold.bank.repository.ClientRepository;
import ru.coold.bank.service.BankService;
import ru.coold.bank.service.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BankServiceImpl implements BankService {
    private final BankRepository repository;
    @Override
    public List<Bank> findBanksFilteredAndSorted(Long bankId, String name, String BIC, String orderBy)
    {
        List<Bank> banksList = repository.findAll();
        if (orderBy != null) {
            switch (orderBy) {
                case "bankId":
                    banksList = repository.findAllByOrderByBankId();
                    break;
                case "name":
                    banksList = repository.findAllByOrderByName();
                    break;
                case "BIC":
                    banksList = repository.findAllByOrderByBIC();
                    break;
                default:
                    // Возвращаем список банков по умолчанию
                    banksList = repository.findAll();
                    break;
            }
        }

        if (bankId != null) {
            banksList = banksList
                    .stream()
                    .filter(element -> element.getBankId() == bankId)
                    .toList();
        }
        if (name != null) {
            banksList = banksList
                    .stream()
                    .filter(element -> element.getName().equals(name))
                    .toList();
        }
        if (BIC != null) {
            banksList = banksList
                    .stream()
                    .filter(element -> element.getBIC().equals(BIC))
                    .toList();
        }
        if (banksList.isEmpty()) { throw new NotFoundException("Banks not found"); }
        else return banksList;
    };

    @Override
    public Bank saveBank(Bank bank) {
        return repository.save(bank);
    }

    @Override
    public Bank updateBank(Bank bank, long id) {
        repository.findById(id).orElseThrow(()-> new NotFoundException("Bank with id "+id+" not found"));
        bank.setBankId(id);
        return repository.save(bank);
    }

    @Override
    @Transactional
    public void deleteBank(long id) {
        repository.findById(id).orElseThrow(()-> new NotFoundException("Bank with id "+id+" not found"));
        repository.deleteById(id);
    }
}
