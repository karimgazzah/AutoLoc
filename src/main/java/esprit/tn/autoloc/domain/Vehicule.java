package esprit.tn.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(unique = true, nullable = false)
    private String immatriculation;

    private String marque;
    private String modele;

    @Enumerated(EnumType.STRING)
    private CategorieVehicule categorie;

    private BigDecimal tarifJournalier;

    @Enumerated(EnumType.STRING)
    private StatutVehicule statut;


    @ManyToOne(fetch = FetchType.LAZY)
    private Agence agence;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default
    private Set<Equipement> equipements = new HashSet<>();

}