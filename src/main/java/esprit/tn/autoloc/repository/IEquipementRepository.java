package esprit.tn.autoloc.repository;

import esprit.tn.autoloc.domain.Equipement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEquipementRepository extends JpaRepository<Equipement, Long> {
}
