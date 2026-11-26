package esprit.tn.autoloc.service;

import esprit.tn.autoloc.domain.Employe;

import java.util.List;

public interface IEmployeService {
    Employe create (Employe employe);
    Employe findById (Long id);
    List<Employe> findAll();
    void deleteById (Long id);
    Employe update (Long id, Employe employe);
}
