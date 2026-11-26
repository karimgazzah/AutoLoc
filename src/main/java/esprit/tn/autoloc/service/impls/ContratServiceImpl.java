package esprit.tn.autoloc.service.impls;

import esprit.tn.autoloc.domain.Contrat;
import esprit.tn.autoloc.domain.Paiement;
import esprit.tn.autoloc.exception.ResourceNotFoundException;
import esprit.tn.autoloc.repository.IContratRepository;
import esprit.tn.autoloc.repository.IPaiementRepository;
import esprit.tn.autoloc.service.IContratService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ContratServiceImpl implements IContratService {

    private final IContratRepository contratRepository;
    private final IPaiementRepository paiementRepository;


    @Override
    public Contrat create(Contrat contrat) {
        if (contrat.getIdContrat() != null) {
            throw new IllegalArgumentException("Un nouveau contrat ne doit pas avoir d'identifiant");
        }
        verifier(contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public Contrat findById (Long id) {
        return contratRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contrat", id));
    }

    @Override
    public List<Contrat> findAll() {
        return contratRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contrat", id);
        }
        contratRepository.deleteById(id);
    }

    @Override
    public Contrat update(Long id, Contrat contrat) {
        Contrat existant = findById(id);
        verifier(contrat);
        existant.setDateSignature(contrat.getDateSignature());
        existant.setMontantTotal(contrat.getMontantTotal());
        existant.setValide(contrat.isValide());
        return contratRepository.save(existant);
    }

    private void verifier(Contrat contrat) {
        if (contrat.getMontantTotal() != null && contrat.getMontantTotal().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Le montant total ne peut pas être négatif");
        }
    }


    @Override
    @Transactional
    public Contrat addWithPayments(Contrat contrat) {
        Set<Paiement> paiements = contrat.getPaiements();
        contrat.setPaiements(null);
        Contrat saved = contratRepository.save(contrat);

        if (paiements != null) {
            for (Paiement p : paiements) {
                p.setContrat(saved);
                paiementRepository.save(p);
            }
            saved.setPaiements(paiements);
        }
        return saved;
    }
}