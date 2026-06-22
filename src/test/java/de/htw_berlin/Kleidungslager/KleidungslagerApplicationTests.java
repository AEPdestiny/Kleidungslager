package de.htw_berlin.Kleidungslager;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KleidungslagerApplicationTests {

	// Prüft die Bean-Validation-Regeln direkt am Entity-Objekt ohne Datenbank.
	@Test
	void ungueltigeEingabenWerdenAbgelehnt() {
		Validator validator = Validation
				.buildDefaultValidatorFactory()
				.getValidator();
		Kleidungsstuecke kleidungsstueck = new Kleidungsstuecke();
		kleidungsstueck.setBezeichnung(" ");
		kleidungsstueck.setFarbe("");
		kleidungsstueck.setLager(0L);
		kleidungsstueck.setLagerbestand(-1);

		var felder = validator.validate(kleidungsstueck).stream()
				.map(fehler -> fehler.getPropertyPath().toString())
				.collect(java.util.stream.Collectors.toSet());

		assertThat(felder).contains(
				"bezeichnung",
				"farbe",
				"lager",
				"lagerbestand",
				"kategorie"
		);
	}

}
