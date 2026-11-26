package esprit.tn.autoloc.service.impls;

import esprit.tn.autoloc.domain.Agence;
import esprit.tn.autoloc.exception.ResourceNotFoundException;
import esprit.tn.autoloc.repository.IAgenceRepository;
import esprit.tn.autoloc.service.IAgenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException("Une nouvelle agence ne doit pas avoir d'identifiant");
        }
        verifier(agence);
        return agenceRepository.save(agence);
    }

    @Override
    public Agence findById (Long id) {
        return agenceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agence", id));
    }

    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (!agenceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Agence", id);
        }
        agenceRepository.deleteById(id);
    }

    @Override
    public Agence update(Long id, Agence agence) {
        Agence existant = findById(id);
        verifier(agence);
        existant.setNom(agence.getNom());
        existant.setVille(agence.getVille());
        existant.setAdresse(agence.getAdresse());
        existant.setTelephone(agence.getTelephone());
        return agenceRepository.save(existant);
    }

    private void verifier(Agence agence) {
        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom de l'agence est obligatoire");
        }
    }

    @Override
    public Agence getByNom (String nom){
        return agenceRepository.findByNom(nom);
    }

    @Override
    public List<Agence> getByVilleAndAdresse (String ville, String adresse){
        return agenceRepository.findByVilleContainingIgnoreCaseAndAdresseContainingIgnoreCase(ville, adresse);
    }
}