package esprit.tn.autoloc.repository;

import esprit.tn.autoloc.domain.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeRepository extends JpaRepository<Employe, Long> {
}
