package esprit.tn.autoloc.repository;

import esprit.tn.autoloc.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByClientEmail(String email);
}