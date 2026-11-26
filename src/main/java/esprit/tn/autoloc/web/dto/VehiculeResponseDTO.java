package esprit.tn.autoloc.web.dto;

import esprit.tn.autoloc.domain.CategorieVehicule;
import esprit.tn.autoloc.domain.StatutVehicule;

import java.math.BigDecimal;
import java.util.List;

public record VehiculeResponseDTO(
        Long id,
        String immatriculation,
        String marque,
        String modele,
        CategorieVehicule categorie,
        BigDecimal tarifJournalier,
        StatutVehicule statut,
        String agenceNom,
        List<String> equipements
) { }