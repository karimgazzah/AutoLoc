package esprit.tn.autoloc.service;

import esprit.tn.autoloc.domain.Paiement;

import java.util.List;

public interface IPaiementService {
    Paiement findById (Long id);
    List<Paiement> findAll();
}