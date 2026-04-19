package de.htw_berlin.Kleidungslager.Controller;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kleidung")
public class KleidungController {

    @GetMapping
    public Kleidungsstuecke beispielKleidungsstueck() {
        Kleidungsstuecke kleidungsstueck = new Kleidungsstuecke();
        kleidungsstueck.setId(1L);
        kleidungsstueck.setBezeichnung("T-Shirt");
        kleidungsstueck.setSize(Kleidungsstuecke.Size.M);
        kleidungsstueck.setFarbe("Schwarz");
        kleidungsstueck.setKategorie(Kleidungsstuecke.Kategorie.HEMD);
        kleidungsstueck.setLagerbestand(10);

        return kleidungsstueck;
    }
}
