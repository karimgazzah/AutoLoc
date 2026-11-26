package esprit.tn.autoloc.web.controller;

import esprit.tn.autoloc.domain.CategorieVehicule;
import esprit.tn.autoloc.domain.StatutVehicule;
import esprit.tn.autoloc.domain.Vehicule;
import esprit.tn.autoloc.service.IVehiculeService;
import esprit.tn.autoloc.web.dto.VehiculeRequestDTO;
import esprit.tn.autoloc.web.dto.VehiculeResponseDTO;
import esprit.tn.autoloc.web.dto.VehiculeResumeDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("vehicules")
@RestController
public class VehiculeRestController {

    private final IVehiculeService vehiculeService;

    @GetMapping
    public List<VehiculeResumeDTO> findAll() {
        return vehiculeService.findAll();
    }

    @GetMapping("{id}")
    public VehiculeResponseDTO findById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<VehiculeResponseDTO> create(@Valid @RequestBody VehiculeRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeService.create(dto));
    }

    // ---------- Endpoints existants ----------
    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }

    @PutMapping("{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @PutMapping("{idVehicule}/agence/{idAgence}")
    public Vehicule assignAgence(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        return vehiculeService.assignAgence(idVehicule, idAgence);
    }

    @PutMapping("{idVehicule}/equipement/{idEquipement}")
    public Vehicule assignEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return vehiculeService.assignEquipement(idVehicule, idEquipement);
    }

    @DeleteMapping("{idVehicule}/equipement/{idEquipement}")
    public void unassignEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        vehiculeService.unassignEquipement(idVehicule, idEquipement);
    }

    @PostMapping("agence/{idAgence}")
    public Vehicule addVehiculeAndAssignAgence(@RequestBody Vehicule vehicule, @PathVariable Long idAgence) {
        return vehiculeService.addVehiculeAndAssignAgence(vehicule, idAgence);
    }

    // ---------- Étape 6 : recherches Keywords ----------
    @GetMapping("recherche/marque/{marque}")                                   // K1
    public List<VehiculeResumeDTO> findByMarque(@PathVariable String marque) {
        return vehiculeService.findByMarque(marque);
    }

    @GetMapping("recherche/categorie/{categorie}/statut/{statut}")             // K2
    public List<VehiculeResumeDTO> findByCategorieAndStatut(@PathVariable CategorieVehicule categorie,
                                                            @PathVariable StatutVehicule statut) {
        return vehiculeService.findByCategorieAndStatut(categorie, statut);
    }

    @GetMapping("recherche/tarif/{min}/{max}")                                 // K3
    public List<VehiculeResumeDTO> findByTarif(@PathVariable BigDecimal min, @PathVariable BigDecimal max) {
        return vehiculeService.findByTarifBetween(min, max);
    }

    @GetMapping("recherche/modele/{motCle}")                                   // K4
    public List<VehiculeResumeDTO> findByModele(@PathVariable String motCle) {
        return vehiculeService.findByModeleContaining(motCle);
    }

    @GetMapping("recherche/categories/{categories}")                           // K5
    public List<VehiculeResumeDTO> findByCategories(@PathVariable List<CategorieVehicule> categories) {
        return vehiculeService.findByCategories(categories);
    }

    @GetMapping("recherche/existe/{immatriculation}")                          // K6
    public boolean existsByImmatriculation(@PathVariable String immatriculation) {
        return vehiculeService.existsByImmatriculation(immatriculation);
    }

    @GetMapping("recherche/count/{statut}")                                    // K7
    public long countByStatut(@PathVariable StatutVehicule statut) {
        return vehiculeService.countByStatut(statut);
    }

    @GetMapping("recherche/moins-chers")                                       // K8
    public List<VehiculeResumeDTO> findTop3MoinsChers() {
        return vehiculeService.findTop3MoinsChers();
    }

    @GetMapping("recherche/agence/{agenceId}")                                 // K9
    public List<VehiculeResumeDTO> findByAgence(@PathVariable Long agenceId) {
        return vehiculeService.findByAgence(agenceId);
    }

    @GetMapping("recherche/sans-agence")                                       // K10
    public List<VehiculeResumeDTO> findSansAgence() {
        return vehiculeService.findSansAgence();
    }
}