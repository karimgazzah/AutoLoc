package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IContratRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {
    private final IContratRepository contratRepository;

    @Override
    public Contrat create(Contrat contrat) {
        ServiceValidation.requireNewEntity(contrat, contrat == null ? null : contrat.getIdContrat(), "contrat");
        validateMontant(contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById(Long id) {
        ServiceValidation.requireId(id, "le contrat");
        return contratRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        ServiceValidation.requireUpdateEntity(contrat, "Le contrat");
        Contrat existing = findById(id);
        validateMontant(contrat);
        existing.setDateSignature(contrat.getDateSignature());
        existing.setMontantTotal(contrat.getMontantTotal());
        existing.setValide(contrat.isValide());
        return contratRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ServiceValidation.requireId(id, "le contrat");
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    private void validateMontant(Contrat contrat) {
        if (contrat.getMontantTotal() != null && contrat.getMontantTotal().signum() < 0) {
            throw new IllegalArgumentException("Le montant total du contrat doit être positif ou nul");
        }
    }
}
