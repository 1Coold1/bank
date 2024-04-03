package ru.coold.bank.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.coold.bank.model.Bank;
import ru.coold.bank.service.BankService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banks")
@AllArgsConstructor
public class BankController {
    private BankService service;

    @GetMapping()
    public List<Bank> findBanks(@RequestParam(value = "bankId", required = false) Long bankId,
                                    @RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "BIC", required = false) String BIC,
                                    @RequestParam(value = "orderBy", required = false) String orderBy)
    {
        return service.findBanksFilteredAndSorted(bankId, name, BIC, orderBy);
    }

    @PostMapping("/save_bank")
    public Bank saveBank(@RequestBody Bank bank){
        return service.saveBank(bank);
    }

    @PutMapping("/update_bank/{id}")
    public Bank updateBank(@PathVariable long id,@RequestBody Bank bank){
        return service.updateBank(bank, id);
    }

    @DeleteMapping("/delete_bank/{id}")
    public void deleteBank(@PathVariable long id){
        service.deleteBank(id);
    }
}
