package ru.coold.bank.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.coold.bank.model.Bank;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.Deposit;
import ru.coold.bank.service.DepositService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/deposits")
@AllArgsConstructor
public class DepositController {
    private DepositService service;

    @GetMapping()
    public List<Deposit> findDeposits(@RequestParam(value = "depositId", required = false) Long depositId,
                                      @RequestParam(value = "client", required = false) Client client,
                                      @RequestParam(value = "bank", required = false) Bank bank,
                                      @RequestParam(value = "openingDate", required = false) Date openingDate,
                                      @RequestParam(value = "percent", required = false) Double percent,
                                      @RequestParam(value = "termMonths", required = false) Integer termMonths,
                                      @RequestParam(value = "orderBy", required = false) String orderBy)
    {
        return service.findDepositsFilteredAndSorted(depositId,client,bank, (java.sql.Date) openingDate,percent,termMonths,orderBy);
    }

    @PostMapping("/save_deposit")
    public Deposit saveDeposit(@RequestBody Deposit deposit){
        return service.saveDeposit(deposit);
    }

    @PutMapping("/update_deposit/{id}")
    public Deposit updateDeposit(@PathVariable long id,@RequestBody Deposit deposit){
        return service.updateDeposit(deposit, id);
    }

    @DeleteMapping("/delete_deposit/{id}")
    public void deleteDeposit(@PathVariable long id){
        service.deleteDeposit(id);
    }
}
