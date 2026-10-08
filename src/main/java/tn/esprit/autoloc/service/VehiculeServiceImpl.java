package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IVehiculeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {
    private final IVehiculeRepository vehiculeRepository;

    @Override
    public Vehicule create(Vehicule vehicule) {
        ServiceValidation.requireNewEntity(
                vehicule, vehicule == null ? null : vehicule.getIdVehicule(), "véhicule");
        validateTarif(vehicule);
        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule findById(Long id) {
        ServiceValidation.requireId(id, "le véhicule");
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicule", id));
    }

    @Override
    public List<Vehicule> findAll() {
        return vehiculeRepository.findAll();
    }

    @Override
    public Vehicule update(Long id, Vehicule vehicule) {
        ServiceValidation.requireUpdateEntity(vehicule, "Le véhicule");
        Vehicule existing = findById(id);
        validateTarif(vehicule);
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        existing.setStatut(vehicule.getStatut());
        return vehiculeRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ServiceValidation.requireId(id, "le véhicule");
        if (!vehiculeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicule", id);
        }
        vehiculeRepository.deleteById(id);
    }

    private void validateTarif(Vehicule vehicule) {
        ServiceValidation.requireNonNegative(vehicule.getTarifJournalier(), "Le tarif journalier");
    }
}
