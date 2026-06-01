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
import org.springframework.web.bind.annotation.PutMapping;
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
        Kleidungsstuecke vorhandenesKleidungsstueck =
                kleidungRepository
                        .findByBezeichnungIgnoreCaseAndSizeAndLagerAndKategorieAndFarbeIgnoreCase(
                                kleidungsstueck.getBezeichnung(),
                                kleidungsstueck.getSize(),
                                kleidungsstueck.getLager(),
                                kleidungsstueck.getKategorie(),
                                kleidungsstueck.getFarbe()
                        )
                        .orElse(null);

        if (vorhandenesKleidungsstueck != null) {
            vorhandenesKleidungsstueck.setLagerbestand(
                    vorhandenesKleidungsstueck.getLagerbestand()
                            + kleidungsstueck.getLagerbestand()
            );

            if (kleidungsstueck.getBild() != null && !kleidungsstueck.getBild().isBlank()) {
                vorhandenesKleidungsstueck.setBild(kleidungsstueck.getBild());
            }

            if (kleidungsstueck.getArtikelnummer() != null && !kleidungsstueck.getArtikelnummer().isBlank()) {
                vorhandenesKleidungsstueck.setArtikelnummer(kleidungsstueck.getArtikelnummer());
            }

            return kleidungRepository.save(vorhandenesKleidungsstueck);
        }

        return kleidungRepository.save(kleidungsstueck);
    }

    @PutMapping("/{id}/bestand")
    public Kleidungsstuecke updateBestand(
            @PathVariable Long id,
            @RequestBody Kleidungsstuecke kleidungsstueck
    ) {
        Kleidungsstuecke vorhandenesKleidungsstueck =
                kleidungRepository.findById(id).orElseThrow();

        if (kleidungsstueck.getBezeichnung() != null) {
            vorhandenesKleidungsstueck.setBezeichnung(
                    kleidungsstueck.getBezeichnung()
            );
        }

        if (kleidungsstueck.getSize() != null) {
            vorhandenesKleidungsstueck.setSize(
                    kleidungsstueck.getSize()
            );
        }

        if (kleidungsstueck.getKategorie() != null) {
            vorhandenesKleidungsstueck.setKategorie(
                    kleidungsstueck.getKategorie()
            );
        }

        if (kleidungsstueck.getFarbe() != null) {
            vorhandenesKleidungsstueck.setFarbe(
                    kleidungsstueck.getFarbe()
            );
        }

        vorhandenesKleidungsstueck.setLagerbestand(
                kleidungsstueck.getLagerbestand()
        );

        vorhandenesKleidungsstueck.setLager(
                kleidungsstueck.getLager()
        );

        if (kleidungsstueck.getBild() != null) {
            vorhandenesKleidungsstueck.setBild(kleidungsstueck.getBild());
        }

        if (kleidungsstueck.getArtikelnummer() != null) {
            vorhandenesKleidungsstueck.setArtikelnummer(kleidungsstueck.getArtikelnummer());
        }

        return kleidungRepository.save(vorhandenesKleidungsstueck);
    }

    @DeleteMapping("/{id}")
    public void deleteKleidung(
            @PathVariable Long id
    ) {
        kleidungRepository.deleteById(id);
    }
}
