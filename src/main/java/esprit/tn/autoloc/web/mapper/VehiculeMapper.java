package esprit.tn.autoloc.web.mapper;

import esprit.tn.autoloc.domain.Equipement;
import esprit.tn.autoloc.domain.Vehicule;
import esprit.tn.autoloc.web.dto.VehiculeRequestDTO;
import esprit.tn.autoloc.web.dto.VehiculeResponseDTO;
import esprit.tn.autoloc.web.dto.VehiculeResumeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    // 1. Entité -> DTO détaillé
    @Mapping(target = "id", source = "idVehicule")
    @Mapping(target = "agenceNom", source = "agence.nom")
    @Mapping(target = "equipements", source = "equipements")
    VehiculeResponseDTO toDto(Vehicule vehicule);

    // 2. Entité -> DTO résumé
    @Mapping(target = "id", source = "idVehicule")
    VehiculeResumeDTO toResumeDto(Vehicule vehicule);

    List<VehiculeResumeDTO> toResumeDtoList(List<Vehicule> vehicules);

    // 3. DTO -> Entité
    @Mapping(target = "idVehicule", ignore = true)
    @Mapping(target = "statut", constant = "DISPONIBLE")
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    Vehicule toEntity(VehiculeRequestDTO dto);

    // 4. Conversion d'un élément de la collection d'équipements
    default String toLibelle(Equipement equipement) {
        return equipement.getLibelle();
    }
}