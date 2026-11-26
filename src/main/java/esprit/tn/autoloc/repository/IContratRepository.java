package esprit.tn.autoloc.repository;

import esprit.tn.autoloc.domain.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IContratRepository extends JpaRepository<Contrat,Long> {
}
