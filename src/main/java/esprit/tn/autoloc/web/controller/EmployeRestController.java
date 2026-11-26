package esprit.tn.autoloc.web.controller;

import esprit.tn.autoloc.domain.Employe;
import esprit.tn.autoloc.service.IEmployeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("employes")
@RestController
public class EmployeRestController {

    private final IEmployeService employeService;

    @PostMapping
    public Employe add(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @GetMapping("{id}")
    public Employe getEmploye(@PathVariable Long id) {
        return employeService.findById(id);
    }

    @GetMapping
    public List<Employe> getEmploye() {
        return employeService.findAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        employeService.deleteById(id);
    }

    @PutMapping("{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }
}