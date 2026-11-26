package esprit.tn.autoloc.web.controller;

import esprit.tn.autoloc.domain.Equipement;
import esprit.tn.autoloc.service.IEquipementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("equipements")
@RestController
public class EquipementRestController {

    private final IEquipementService equipementService;

    @PostMapping
    public Equipement add(@RequestBody Equipement equipement) {
        return equipementService.create(equipement);
    }

    @GetMapping("{id}")
    public Equipement getEquipement(@PathVariable Long id) {
        return equipementService.findById(id);
    }

    @GetMapping
    public List<Equipement> getEquipement() {
        return equipementService.findAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        equipementService.deleteById(id);
    }

    @PutMapping("{id}")
    public Equipement update(@PathVariable Long id, @RequestBody Equipement equipement) {
        return equipementService.update(id, equipement);
    }
}