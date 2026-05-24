package de.htw_berlin.Kleidungslager.Controller;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/kleidung")
public class KleidungController {

    @GetMapping
    public List<Kleidungsstuecke> getKleidung() {
        Kleidungsstuecke shirt = new Kleidungsstuecke();
        shirt.setId(1L);
        shirt.setBezeichnung("T-Shirt Basic");
        shirt.setSize(Kleidungsstuecke.Size.M);
        shirt.setFarbe("Schwarz");
        shirt.setKategorie(Kleidungsstuecke.Kategorie.HEMD);
        shirt.setLagerbestand(24);

        Kleidungsstuecke jeans = new Kleidungsstuecke();
        jeans.setId(2L);
        jeans.setBezeichnung("Jeans Regular");
        jeans.setSize(Kleidungsstuecke.Size.L);
        jeans.setFarbe("Blau");
        jeans.setKategorie(Kleidungsstuecke.Kategorie.HOSE);
        jeans.setLagerbestand(12);

        return List.of(shirt, jeans);
    }
}
