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
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements")
public class EquipementController {
    private final IEquipementService equipementService;

    @PostMapping
    public ResponseEntity<Equipement> create(@RequestBody Equipement equipement) {
        Equipement created = equipementService.create(equipement);
        return ResponseEntity.created(URI.create("/api/equipements/" + created.getIdEquipement())).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Equipement> findById(@PathVariable Long id) {
        return ResponseEntity.ok(equipementService.findById(id));
    }

    @GetMapping
    public List<Equipement> findAll() {
        return equipementService.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipement> update(@PathVariable Long id, @RequestBody Equipement equipement) {
        return ResponseEntity.ok(equipementService.update(id, equipement));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
