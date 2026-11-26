package esprit.tn.autoloc.web.controller.DTO;

import esprit.tn.autoloc.domain.CategorieVehicule;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor

public class VehiculeDTO {
    private String immatriculation;
    private String marque;
    private String modele;
    private CategorieVehicule categorie;
    private BigDecimal tarifJournalier;
    private String nomAgence;
}
