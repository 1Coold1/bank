package ru.coold.bank.service;

import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.Deposit;

import java.sql.Date;
import java.util.List;

public interface DepositService {
    List<Deposit> findDepositsFilteredAndSorted(Long depositId, Client client, Bank bank, Date openingDate, Double percent, Integer termMonths, String orderBy);

    Deposit saveDeposit(Deposit deposit);
    Deposit updateDeposit(Deposit deposit, long id);
    void deleteDeposit(long id);
}
