package esprit.tn.autoloc.service;

import esprit.tn.autoloc.domain.Vehicule;
import esprit.tn.autoloc.web.dto.VehiculeRequestDTO;
import esprit.tn.autoloc.web.dto.VehiculeResponseDTO;
import esprit.tn.autoloc.web.dto.VehiculeResumeDTO;
import esprit.tn.autoloc.domain.CategorieVehicule;
import esprit.tn.autoloc.domain.StatutVehicule;
import java.math.BigDecimal;

import java.util.List;

public interface IVehiculeService {
    VehiculeResponseDTO create(VehiculeRequestDTO dto);
    VehiculeResponseDTO findById(Long id);
    List<VehiculeResumeDTO> findAll();
    void deleteById(Long id);
    Vehicule update(Long id, Vehicule vehicule);
    Vehicule assignAgence(Long idVehicule, Long idAgence);
    Vehicule assignEquipement(Long idVehicule, Long idEquipement);
    void unassignEquipement(Long idVehicule, Long idEquipement);
    Vehicule addVehiculeAndAssignAgence(Vehicule vehicule, Long idAgence);
    // Étape 6 : Keywords
    List<VehiculeResumeDTO> findByMarque(String marque);                                          // K1
    List<VehiculeResumeDTO> findByCategorieAndStatut(CategorieVehicule c, StatutVehicule s);      // K2
    List<VehiculeResumeDTO> findByTarifBetween(BigDecimal min, BigDecimal max);                   // K3
    List<VehiculeResumeDTO> findByModeleContaining(String motCle);                                // K4
    List<VehiculeResumeDTO> findByCategories(List<CategorieVehicule> categories);                 // K5
    boolean existsByImmatriculation(String immatriculation);                                      // K6
    long countByStatut(StatutVehicule statut);                                                    // K7
    List<VehiculeResumeDTO> findTop3MoinsChers();                                                 // K8
    List<VehiculeResumeDTO> findByAgence(Long agenceId);                                          // K9
    List<VehiculeResumeDTO> findSansAgence();
}