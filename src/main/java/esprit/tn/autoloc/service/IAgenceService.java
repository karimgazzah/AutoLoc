package esprit.tn.autoloc.service;

import esprit.tn.autoloc.domain.Agence;

import java.util.List;

public interface IAgenceService {
    Agence create (Agence agence);
    Agence findById (Long id);
    List<Agence> findAll();
    void deleteById (Long id);
    Agence update (Long id, Agence agence);
    List<Agence> getByVilleAndAdresse (String ville, String adresse);
    Agence getByNom(String nom);

}