package esprit.tn.autoloc.repository;

import esprit.tn.autoloc.domain.CategorieVehicule;
import esprit.tn.autoloc.domain.StatutVehicule;
import esprit.tn.autoloc.domain.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // K1 — véhicules d'une marque
    List<Vehicule> findByMarque(String marque);

    // K2 — catégorie ET statut
    List<Vehicule> findByCategorieAndStatut(CategorieVehicule categorie, StatutVehicule statut);

    // K3 — tarif entre min et max, trié par tarif croissant
    List<Vehicule> findByTarifJournalierBetweenOrderByTarifJournalierAsc(BigDecimal min, BigDecimal max);

    // K4 — modèle contenant un mot-clé, sans tenir compte de la casse
    List<Vehicule> findByModeleContainingIgnoreCase(String motCle);

    // K5 — catégorie dans une liste
    List<Vehicule> findByCategorieIn(List<CategorieVehicule> categories);

    // K6 — l'immatriculation existe-t-elle déjà ?
    boolean existsByImmatriculation(String immatriculation);

    // K7 — nombre de véhicules dans un statut
    long countByStatut(StatutVehicule statut);

    // K8 — les 3 véhicules les moins chers
    List<Vehicule> findTop3ByOrderByTarifJournalierAsc();

    // K9 — véhicules d'une agence (ton attribut s'appelle idAgence)
    List<Vehicule> findByAgence_IdAgence(Long agenceId);

    // K10 — véhicules sans agence
    List<Vehicule> findByAgenceIsNull();
}