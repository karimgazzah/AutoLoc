package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
@Tag(name = "Employés")
public class EmployeController {
    private final IEmployeService employeService;

    @PostMapping
    public ResponseEntity<Employe> create(@RequestBody Employe employe) {
        Employe created = employeService.create(employe);
        return ResponseEntity.created(URI.create("/api/employes/" + created.getIdEmploye())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employe> findById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.findById(id));
    }

    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employe> update(@PathVariable Long id, @RequestBody Employe employe) {
        return ResponseEntity.ok(employeService.update(id, employe));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
