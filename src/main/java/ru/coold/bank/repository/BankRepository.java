package ru.coold.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coold.bank.model.Bank;

import java.util.List;

public interface BankRepository extends JpaRepository<Bank,Long> {
    List<Bank> findAllByOrderByBankId();
    List<Bank> findAllByOrderByName();
    List<Bank> findAllByOrderByBIC();
}
