package esprit.tn.autoloc.service;

import esprit.tn.autoloc.domain.Equipement;

import java.util.List;

public interface IEquipementService {
    Equipement create (Equipement equipement);
    Equipement findById (Long id);
    List<Equipement> findAll();
    void deleteById (Long id);
    Equipement update (Long id, Equipement equipement);
}