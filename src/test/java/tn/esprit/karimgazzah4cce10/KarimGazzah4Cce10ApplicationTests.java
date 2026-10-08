package tn.esprit.karimgazzah4cce10;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.CategorieVehicule;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.domain.StatutReservation;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

@SpringBootTest
class KarimGazzah4Cce10ApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    void contextLoads() {
    }

    @Test
    void createsAllExpectedEntityTables() {
        Integer tableCount = jdbcTemplate.queryForObject("""
                SELECT COUNT(*)
                FROM information_schema.tables
                WHERE table_schema = DATABASE()
                  AND table_name IN (
                    'agence', 'client', 'employe', 'equipement', 'reservation',
                    'contrat', 'paiement', 'maintenance', 'vehicule'
                  )
                """, Integer.class);

        Assertions.assertEquals(9, tableCount);
    }

    @Test
    void createsManyToManyJoinTableAndNonUniquePaymentForeignKey() {
        Integer joinTableCount = jdbcTemplate.queryForObject("""
                SELECT COUNT(*)
                FROM information_schema.tables
                WHERE table_schema = DATABASE()
                  AND table_name = 'vehicule_equipement'
                """, Integer.class);
        Integer uniquePaymentForeignKeyCount = jdbcTemplate.queryForObject("""
                SELECT COUNT(*)
                FROM information_schema.statistics
                WHERE table_schema = DATABASE()
                  AND table_name = 'paiement'
                  AND column_name = 'id_contrat'
                  AND non_unique = 0
                  AND index_name <> 'PRIMARY'
                """, Integer.class);

        Assertions.assertEquals(1, joinTableCount);
        Assertions.assertEquals(0, uniquePaymentForeignKeyCount);
    }

    @Test
    @Transactional
    void lazilyLoadsAndCascadesContractPayments() {
        Agence agence = new Agence();
        entityManager.persist(agence);

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-ATELIER-2");
        vehicule.setMarque("Test");
        vehicule.setModele("Test");
        vehicule.setCategorie(CategorieVehicule.CITADINE);
        vehicule.setTarifJournalier(BigDecimal.ONE);
        vehicule.setStatut(StatutVehicule.DISPONIBLE);
        vehicule.setAgence(agence);
        Maintenance maintenance = new Maintenance();
        maintenance.setVehicule(vehicule);
        vehicule.getMaintenances().add(maintenance);
        entityManager.persist(vehicule);

        Client client = new Client();
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setVehicule(vehicule);
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        client.getReservations().add(reservation);

        Contrat contrat = new Contrat();
        contrat.setReservation(reservation);
        reservation.setContrat(contrat);

        Paiement premierPaiement = new Paiement();
        premierPaiement.setContrat(contrat);
        Paiement secondPaiement = new Paiement();
        secondPaiement.setContrat(contrat);
        contrat.getPaiements().add(premierPaiement);
        contrat.getPaiements().add(secondPaiement);

        entityManager.persist(client);
        entityManager.flush();
        Assertions.assertEquals(1L, entityManager.createQuery(
                        "SELECT COUNT(m) FROM Maintenance m WHERE m.vehicule.idVehicule = :vehiculeId",
                        Long.class)
                .setParameter("vehiculeId", vehicule.getIdVehicule())
                .getSingleResult());
        Long contratId = contrat.getIdContrat();
        entityManager.clear();

        Contrat persistedContrat = entityManager.find(Contrat.class, contratId);
        Assertions.assertFalse(entityManagerFactory.getPersistenceUnitUtil()
                .isLoaded(persistedContrat, "paiements"));
        Assertions.assertEquals(2, persistedContrat.getPaiements().size());

        persistedContrat.getPaiements().remove(0);
        entityManager.flush();
        Assertions.assertEquals(1L, countPaymentsFor(contratId));

        entityManager.remove(persistedContrat);
        entityManager.flush();
        Assertions.assertEquals(0L, countPaymentsFor(contratId));
    }

    private long countPaymentsFor(Long contratId) {
        return entityManager.createQuery(
                        "SELECT COUNT(p) FROM Paiement p WHERE p.contrat.idContrat = :contratId",
                        Long.class)
                .setParameter("contratId", contratId)
                .getSingleResult();
    }
}
