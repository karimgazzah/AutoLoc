package esprit.tn.autoloc.service;

import esprit.tn.autoloc.domain.Reservation;

import java.util.List;

public interface IReservationService {
    Reservation create (Reservation reservation);
    Reservation findById (Long id);
    List<Reservation> findAll();
    void deleteById (Long id);
    Reservation update (Long id, Reservation reservation);
}