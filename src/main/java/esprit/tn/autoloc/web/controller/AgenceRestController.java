package esprit.tn.autoloc.web.controller;

import esprit.tn.autoloc.domain.Agence;
import esprit.tn.autoloc.service.IAgenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("agences")
@RestController
public class AgenceRestController {

    private final IAgenceService agenceService;

    @PostMapping
    public Agence add(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @Operation (summary = "Recherche by id")
    @GetMapping("{id}")
    public Agence getAgence(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return agenceService.findById(id);
    }

    @GetMapping
    public List<Agence> getAgence() {
        return agenceService.findAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        agenceService.deleteById(id);
    }

    @PutMapping("{id}")
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @Operation (summary = "Recherche by nom")
    @GetMapping("nom/{nom}")
    public Agence getByNom (@PathVariable String nom){
        return agenceService.getByNom(nom);
    }

    @Operation (summary = "Recherche by Ville et Adresse")
    @GetMapping("search")
    public List<Agence> getVilleAndAdresse (@RequestParam String ville, @RequestParam String adresse){
        return agenceService.getByVilleAndAdresse(ville, adresse);
    }
}