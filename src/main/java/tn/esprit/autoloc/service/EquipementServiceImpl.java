package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEquipementRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {
    private final IEquipementRepository equipementRepository;

    @Override
    public Equipement create(Equipement equipement) {
        ServiceValidation.requireNewEntity(
                equipement, equipement == null ? null : equipement.getIdEquipement(), "équipement");
        ServiceValidation.requireText(equipement.getLibelle(), "Le libellé de l'équipement");
        return equipementRepository.save(equipement);
    }

    @Override
    public Equipement findById(Long id) {
        ServiceValidation.requireId(id, "l'équipement");
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Equipement", id));
    }

    @Override
    public List<Equipement> findAll() {
        return equipementRepository.findAll();
    }

    @Override
    public Equipement update(Long id, Equipement equipement) {
        ServiceValidation.requireUpdateEntity(equipement, "L'équipement");
        Equipement existing = findById(id);
        ServiceValidation.requireText(equipement.getLibelle(), "Le libellé de l'équipement");
        existing.setLibelle(equipement.getLibelle());
        return equipementRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ServiceValidation.requireId(id, "l'équipement");
        if (!equipementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Equipement", id);
        }
        equipementRepository.deleteById(id);
    }
}
