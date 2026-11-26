package esprit.tn.autoloc.web.controller;

import esprit.tn.autoloc.domain.Paiement;
import esprit.tn.autoloc.service.IPaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("paiements")
@RestController
public class PaiementRestController {

    private final IPaiementService paiementService;

    @GetMapping("{id}")
    public Paiement getPaiement(@PathVariable Long id) {
        return paiementService.findById(id);
    }

    @GetMapping
    public List<Paiement> getPaiement() {
        return paiementService.findAll();
    }
}