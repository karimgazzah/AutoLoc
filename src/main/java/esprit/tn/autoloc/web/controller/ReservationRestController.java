package esprit.tn.autoloc.web.controller;

import esprit.tn.autoloc.domain.Reservation;
import esprit.tn.autoloc.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("reservations")
@RestController
public class ReservationRestController {

    private final IReservationService reservationService;

    @PostMapping
    public Reservation add(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @GetMapping("{id}")
    public Reservation getReservation(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @GetMapping
    public List<Reservation> getReservation() {
        return reservationService.findAll();
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        reservationService.deleteById(id);
    }

    @PutMapping("{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }
}