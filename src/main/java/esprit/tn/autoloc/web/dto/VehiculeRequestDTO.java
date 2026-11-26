package esprit.tn.autoloc.web.dto;

import esprit.tn.autoloc.domain.CategorieVehicule;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record VehiculeRequestDTO(

        @NotBlank(message = "L'immatriculation est obligatoire")
        @Pattern(regexp = "^\\d{1,3}TU\\d{1,4}$",
                message = "Format d'immatriculation invalide (ex. 120TU1001)")
        String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        @Size(max = 30, message = "La marque ne doit pas dépasser 30 caractères")
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        @Size(max = 30, message = "Le modèle ne doit pas dépasser 30 caractères")
        String modele,

        @NotNull(message = "La catégorie est obligatoire")
        CategorieVehicule categorie,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @Positive(message = "Le tarif journalier doit être strictement positif")
        BigDecimal tarifJournalier,

        @NotNull(message = "L'agence est obligatoire")
        Long agenceId
) { }