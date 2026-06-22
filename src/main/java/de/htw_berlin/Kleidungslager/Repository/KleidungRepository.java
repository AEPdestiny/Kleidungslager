package de.htw_berlin.Kleidungslager.Repository;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KleidungRepository
        extends JpaRepository<Kleidungsstuecke, Long> {

    Optional<Kleidungsstuecke> findFirstByArtikelnummerIgnoreCase(String artikelnummer);

    Optional<Kleidungsstuecke> findByBezeichnungIgnoreCaseAndSizeAndLagerAndKategorieAndFarbeIgnoreCase(
            String bezeichnung,
            String size,
            Long lager,
            Kleidungsstuecke.Kategorie kategorie,
            String farbe
    );
}
