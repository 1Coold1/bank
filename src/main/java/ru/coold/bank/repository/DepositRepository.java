package ru.coold.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.coold.bank.model.Deposit;

import java.util.List;

public interface DepositRepository extends JpaRepository<Deposit,Long> {
    List<Deposit> findAllByOrderByDepositId();
    List<Deposit> findAllByOrderByClient();
    List<Deposit> findAllByOrderByBank();
    List<Deposit> findAllByOrderByOpeningDate();
    List<Deposit> findAllByOrderByPercent();
    List<Deposit> findAllByOrderByTermMonths();
}
