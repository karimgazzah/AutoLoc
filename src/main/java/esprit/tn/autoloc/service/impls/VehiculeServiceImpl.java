package esprit.tn.autoloc.service.impls;

import esprit.tn.autoloc.domain.Agence;
import esprit.tn.autoloc.domain.Equipement;
import esprit.tn.autoloc.domain.Vehicule;
import esprit.tn.autoloc.exception.ResourceNotFoundException;
import esprit.tn.autoloc.repository.IAgenceRepository;
import esprit.tn.autoloc.repository.IEquipementRepository;
import esprit.tn.autoloc.repository.IVehiculeRepository;
import esprit.tn.autoloc.service.IVehiculeService;
import esprit.tn.autoloc.web.dto.VehiculeRequestDTO;
import esprit.tn.autoloc.web.dto.VehiculeResponseDTO;
import esprit.tn.autoloc.web.dto.VehiculeResumeDTO;
import esprit.tn.autoloc.web.mapper.VehiculeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import esprit.tn.autoloc.exception.DuplicateResourceException;
import esprit.tn.autoloc.domain.CategorieVehicule;
import esprit.tn.autoloc.domain.StatutVehicule;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    private Vehicule getEntity(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    // ---------- Étape 4 ----------
    @Override
    @Transactional(readOnly = true)
    public VehiculeResponseDTO findById(Long id) {
        // charger le véhicule (404 si absent) puis le convertir
        return vehiculeMapper.toDto(getEntity(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findAll() {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findAll());
    }

    @Override
    @Transactional
    public VehiculeResponseDTO create(VehiculeRequestDTO dto) {
        if (vehiculeRepository.existsByImmatriculation(dto.immatriculation())) {
            throw new DuplicateResourceException(
                    "Un véhicule avec l'immatriculation " + dto.immatriculation() + " existe déjà");
        }
        // 1. convertir le DTO en entité
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        // 2. charger l'agence (404 si absente) et l'associer
        Agence agence = agenceRepository.findById(dto.agenceId())
                .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.agenceId()));
        vehicule.setAgence(agence);
        // 3. enregistrer puis renvoyer le DTO de réponse
        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));

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
        Vehicule existant = getEntity(id);
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
    @Transactional
    public Vehicule assignAgence(Long idVehicule, Long idAgence) {
        Vehicule vehicule = getEntity(idVehicule);
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule assignEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getEntity(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().add(equipement);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public void unassignEquipement(Long idVehicule, Long idEquipement) {
        Vehicule vehicule = getEntity(idVehicule);
        Equipement equipement = equipementRepository.findById(idEquipement)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", idEquipement));
        vehicule.getEquipements().remove(equipement);
        vehiculeRepository.save(vehicule);
    }

    @Override
    @Transactional
    public Vehicule addVehiculeAndAssignAgence(Vehicule vehicule, Long idAgence) {
        if (vehicule.getIdVehicule() != null) {
            throw new IllegalArgumentException("Un nouveau véhicule ne doit pas avoir d'identifiant");
        }
        Agence agence = agenceRepository.findById(idAgence)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", idAgence));
        verifier(vehicule);
        vehicule.setAgence(agence);
        return vehiculeRepository.save(vehicule);
    }
    // ---------- Étape 6 : Keywords ----------
    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByMarque(String marque) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByMarque(marque));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByCategorieAndStatut(CategorieVehicule c, StatutVehicule s) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByCategorieAndStatut(c, s));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByTarifBetween(BigDecimal min, BigDecimal max) {
        return vehiculeMapper.toResumeDtoList(
                vehiculeRepository.findByTarifJournalierBetweenOrderByTarifJournalierAsc(min, max));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByModeleContaining(String motCle) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByModeleContainingIgnoreCase(motCle));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByCategories(List<CategorieVehicule> categories) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByCategorieIn(categories));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByImmatriculation(String immatriculation) {
        return vehiculeRepository.existsByImmatriculation(immatriculation);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByStatut(StatutVehicule statut) {
        return vehiculeRepository.countByStatut(statut);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findTop3MoinsChers() {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findTop3ByOrderByTarifJournalierAsc());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findByAgence(Long agenceId) {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByAgence_IdAgence(agenceId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeResumeDTO> findSansAgence() {
        return vehiculeMapper.toResumeDtoList(vehiculeRepository.findByAgenceIsNull());
    }

    private void verifier(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }
}