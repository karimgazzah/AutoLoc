package esprit.tn.autoloc.service;

import esprit.tn.autoloc.domain.Maintenance;

import java.util.List;

public interface IMaintenanceService {
    Maintenance create (Maintenance maintenance);
    Maintenance findById (Long id);
    List<Maintenance> findAll();
    void deleteById (Long id);
    Maintenance update (Long id, Maintenance maintenance);
}