package esprit.tn.autoloc.web.controller;

import esprit.tn.autoloc.domain.Client;
import esprit.tn.autoloc.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("clients")
@RestController
public class ClientRestController {

    private final IClientService clientService;

    @PostMapping
    public Client add(@RequestBody Client client) {
        return clientService.create(client);
    }

    @GetMapping("{id}")
    public Client getClient(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @GetMapping
    public List<Client> getClient() {
        return clientService.findAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        clientService.deleteById(id);
    }

    @PutMapping("{id}")
    public Client update(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }
}