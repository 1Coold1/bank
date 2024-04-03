package ru.coold.bank.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.coold.bank.model.Client;
import ru.coold.bank.model.LegalForm;
import ru.coold.bank.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@AllArgsConstructor
public class ClientController {
    private ClientService service;

    @GetMapping()
    public List<Client> findClients(@RequestParam(value = "clientId", required = false) Long clientId,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "shortName", required = false) String shortName,
                                          @RequestParam(value = "address", required = false) String address,
                                          @RequestParam(value = "legalForm", required = false) LegalForm legalForm,
                                          @RequestParam(value = "orderBy", required = false) String orderBy)
    {
        return service.findClientsFilteredAndSorted(clientId, name, shortName, address, legalForm, orderBy);
    }

    @PostMapping("/save_client")
    public Client saveClient(@RequestBody Client client){
        return service.saveClient(client);
    }

    @PutMapping("/update_client/{id}")
    public Client updateClient(@PathVariable long id,@RequestBody Client client){
        return service.updateClient(client, id);
    }

    @DeleteMapping("/delete_client/{id}")
    public void deleteClient(@PathVariable long id){
        service.deleteClient(id);
    }
}
