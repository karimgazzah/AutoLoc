package tn.esprit.autoloc.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {
    private final IEmployeRepository employeRepository;

    @Override
    public Employe create(Employe employe) {
        ServiceValidation.requireNewEntity(employe, employe == null ? null : employe.getIdEmploye(), "employé");
        ServiceValidation.requireText(employe.getNom(), "Le nom de l'employé");
        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        ServiceValidation.requireId(id, "l'employé");
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        ServiceValidation.requireUpdateEntity(employe, "L'employé");
        Employe existing = findById(id);
        ServiceValidation.requireText(employe.getNom(), "Le nom de l'employé");
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        existing.setRole(employe.getRole());
        return employeRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        ServiceValidation.requireId(id, "l'employé");
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }
}
