package ru.coold.bank.service.Impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.Deposit;
import ru.coold.bank.repository.DepositRepository;
import ru.coold.bank.service.DepositService;
import ru.coold.bank.service.NotFoundException;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class DepositServiceImpl implements DepositService {

    private final DepositRepository repository;

    @Override
    public List<Deposit> findDepositsFilteredAndSorted(Long depositId, Client client, Bank bank, Date openingDate, Double percent, Integer termMonths, String orderBy) {
        List<Deposit> depositsList = repository.findAll();
        if (orderBy != null) {
            switch (orderBy) {
                case "depositId":
                    depositsList = repository.findAllByOrderByDepositId();
                    break;
                case "client":
                    depositsList = repository.findAllByOrderByClient();
                    break;
                case "bank":
                    depositsList = repository.findAllByOrderByBank();
                    break;
                case "openingDate":
                    depositsList = repository.findAllByOrderByOpeningDate();
                    break;
                case "percent":
                    depositsList = repository.findAllByOrderByPercent();
                    break;
                case "termMonths":
                    depositsList = repository.findAllByOrderByTermMonths();
                    break;
                default:
                    // Возвращаем список вкладов по умолчанию
                    depositsList = repository.findAll();
                    break;
            }
        }

        if (depositId != null) {
            depositsList = depositsList
                    .stream()
                    .filter(element -> element.getDepositId() == depositId)
                    .toList();
        }
        if (client != null) {
            depositsList = depositsList
                    .stream()
                    .filter(element -> element.getClient().equals(client))
                    .toList();
        }
        if (bank != null) {
            depositsList = depositsList
                    .stream()
                    .filter(element -> element.getBank().equals(bank))
                    .toList();
        }
        if (openingDate != null) {
            depositsList = depositsList
                    .stream()
                    .filter(element -> element.getOpeningDate().equals(openingDate))
                    .toList();
        }
        if (percent != null) {
            depositsList = depositsList
                    .stream()
                    .filter(element -> element.getPercent() == percent)
                    .toList();
        }
        if (termMonths != null) {
            depositsList = depositsList
                    .stream()
                    .filter(element -> element.getTermMonths() == termMonths)
                    .toList();
        }
        if (depositsList.isEmpty()) { throw new NotFoundException("Deposits not found"); }
        else return depositsList;
    }

    @Override
    public Deposit saveDeposit(Deposit deposit) {
        return repository.save(deposit);
    }

    @Override
    public Deposit updateDeposit(Deposit deposit, long id) {
        repository.findById(id).orElseThrow(()-> new NotFoundException("Deposit with id "+id+" not found"));
        deposit.setDepositId(id);
        return repository.save(deposit);
    }

    @Override
    @Transactional
    public void deleteDeposit(long id) {
        repository.findById(id).orElseThrow(()-> new NotFoundException("Deposit with id "+id+" not found"));
        repository.deleteById(id);
    }
}
