package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {
    private final IMaintenanceRepository maintenanceRepository;

    @Override
    public Maintenance create(Maintenance maintenance) {
        ServiceValidation.requireNewEntity(
                maintenance, maintenance == null ? null : maintenance.getIdMaintenance(), "maintenance");
        validateDates(maintenance);
        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance findById(Long id) {
        ServiceValidation.requireId(id, "la maintenance");
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }

    @Override
    public List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @Override
    public Maintenance update(Long id, Maintenance maintenance) {
        ServiceValidation.requireUpdateEntity(maintenance, "La maintenance");
        Maintenance existing = findById(id);
        validateDates(maintenance);
        existing.setDateDebut(maintenance.getDateDebut());
        existing.setDateFin(maintenance.getDateFin());
        existing.setDescription(maintenance.getDescription());
        return maintenanceRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ServiceValidation.requireId(id, "la maintenance");
        if (!maintenanceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Maintenance", id);
        }
        maintenanceRepository.deleteById(id);
    }

    private void validateDates(Maintenance maintenance) {
        if (maintenance.getDateDebut() != null
                && maintenance.getDateFin() != null
                && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin de maintenance ne peut pas précéder sa date de début");
        }
    }
}
