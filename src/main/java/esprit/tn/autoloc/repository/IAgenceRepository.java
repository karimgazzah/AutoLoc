package esprit.tn.autoloc.repository;

import esprit.tn.autoloc.domain.Agence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IAgenceRepository extends JpaRepository<Agence,Long> {
    Agence findByNom(String nom);
    List<Agence> findByVilleContainingIgnoreCaseAndAdresseContainingIgnoreCase(String ville, String adresse);
}