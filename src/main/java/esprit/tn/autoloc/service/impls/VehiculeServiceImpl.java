package esprit.tn.autoloc.service.impls;

import esprit.tn.autoloc.domain.Agence;
import esprit.tn.autoloc.domain.Contrat;
import esprit.tn.autoloc.domain.Equipement;
import esprit.tn.autoloc.domain.Vehicule;
import esprit.tn.autoloc.exception.ResourceNotFoundException;
import esprit.tn.autoloc.repository.IAgenceRepository;
import esprit.tn.autoloc.repository.IEquipementRepository;
import esprit.tn.autoloc.repository.IVehiculeRepository;
import esprit.tn.autoloc.service.IVehiculeService;
import esprit.tn.autoloc.web.controller.DTO.VehiculeDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    @Override
    public Vehicule create(Vehicule vehicule) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        verifier(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById (Long id) {
        return vehiculeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<VehiculeDTO> findAll() {
        List<VehiculeDTO> vehiculeDTOS = new ArrayList<>();
        for (Vehicule vehicule : vehiculeRepository.findAll()){
            vehiculeDTOS.add(toDTO(vehicule));
        }
        return vehiculeRepository.findAll();
    }
    public VehiculeDTO toDTO(Vehicule vehicule){
        VehiculeDTO vehiculeDTO = new VehiculeDTO();
        vehiculeDTO.setImmatriculation(vehicule.getImmatriculation());
        vehiculeDTO.setCategorie(vehicule.getCategorie());
        vehiculeDTO.setMarque(vehicule.getMarque());
        vehiculeDTO.setTarifJournalier(vehicule.getTarifJournalier());
        vehiculeDTO.setNomAgence(vehicule.getAgence());

        return vehiculeDTO;
    }

    @Override
    public void deleteById(Long id) {
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        Vehicule existant = findById(id);
        verifier(vehicule);
        existant.setImmatriculation(vehicule.getImmatriculation());
        existant.setMarque(vehicule.getMarque());
        existant.setModele(vehicule.getModele());
        existant.setCategorie(vehicule.getCategorie());
        existant.setTarifJournalier(vehicule.getTarifJournalier());
        existant.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existant);
    }

    @Override
    public Vehicule assignAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = findById(idVehicule);
        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule assignEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement).orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().add(equipement);
        return vehiculeRepository.save(vehicule);
    }
    @Override
    public void unassignEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = findById(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement).orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().remove(equipement);
        vehiculeRepository.save(vehicule);
    }

    @Transactional
    @Override
    public Vehicule addVehiculeAndAssignAgence(Vehicule vehicule, Long idAgence) {
        Agence agence = agenceRepository.findById(idAgence).orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicule.setAgence(agence);
        return create(vehicule);
    }

    private void verifier(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }
}