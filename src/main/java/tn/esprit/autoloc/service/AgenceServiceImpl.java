package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {
    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence agence) {
        ServiceValidation.requireNewEntity(agence, agence == null ? null : agence.getIdAgence(), "agence");
        ServiceValidation.requireText(agence.getNom(), "Le nom de l'agence");
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById(Long id) {
        ServiceValidation.requireId(id, "l'agence");
        return agenceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public Agence update(Long id, Agence agence) {
        ServiceValidation.requireUpdateEntity(agence, "L'agence");
        Agence existing = findById(id);
        ServiceValidation.requireText(agence.getNom(), "Le nom de l'agence");
        existing.setNom(agence.getNom());
        existing.setVille(agence.getVille());
        existing.setAdresse(agence.getAdresse());
        existing.setTelephone(agence.getTelephone());
        return agenceRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ServiceValidation.requireId(id, "l'agence");
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }
}
