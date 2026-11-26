package esprit.tn.autoloc.repository;

import esprit.tn.autoloc.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface IClientRepository extends JpaRepository<Client, Long> {

    @Query("select client from Client client " +
            "join client.reservations res " +
            "where res.dateDebut between :debut and :fin")
    List<Client> findByReservations(@Param("debut") LocalDate debut, @Param("fin") LocalDate fin);
}
