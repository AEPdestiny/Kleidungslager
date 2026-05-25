package de.htw_berlin.Kleidungslager.Repository;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KleidungRepository
        extends JpaRepository<Kleidungsstuecke, Long> {
}