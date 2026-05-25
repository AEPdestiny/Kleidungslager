package de.htw_berlin.Kleidungslager.Controller;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import de.htw_berlin.Kleidungslager.Repository.KleidungRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/kleidung")
public class KleidungController {

    private final KleidungRepository kleidungRepository;

    public KleidungController(KleidungRepository kleidungRepository) {
        this.kleidungRepository = kleidungRepository;
    }

    @GetMapping
    public List<Kleidungsstuecke> getKleidung() {
        return kleidungRepository.findAll();
    }

    @PostMapping
    public Kleidungsstuecke createKleidung(
            @RequestBody Kleidungsstuecke kleidungsstueck
    ) {
        return kleidungRepository.save(kleidungsstueck);
    }

    @DeleteMapping("/{id}")
    public void deleteKleidung(
            @PathVariable Long id
    ) {
        kleidungRepository.deleteById(id);
    }
}