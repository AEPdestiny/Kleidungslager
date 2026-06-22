package de.htw_berlin.Kleidungslager.Controller;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import de.htw_berlin.Kleidungslager.Repository.KleidungRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class KleidungControllerTests {

    private KleidungRepository repository;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        repository = mock(KleidungRepository.class);
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.afterPropertiesSet();

        mockMvc = standaloneSetup(new KleidungController(repository))
                .setControllerAdvice(new ApiExceptionHandler())
                .setValidator(validator)
                .build();
    }

    // Prüft, dass ein ungültiger POST den Status 400 und verständliche Feldfehler liefert.
    @Test
    void postGibtVerstaendlicheValidierungsfehlerZurueck() throws Exception {
        mockMvc.perform(post("/api/kleidung")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "bezeichnung": "",
                                  "farbe": "",
                                  "lager": 0,
                                  "lagerbestand": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.meldung").value("Die Eingaben sind nicht gültig."))
                .andExpect(jsonPath("$.feldfehler.bezeichnung").exists())
                .andExpect(jsonPath("$.feldfehler.lagerbestand").exists());
    }

    // Prüft, dass der Barcode-Endpunkt das vom Repository gelieferte Produkt zurückgibt.
    @Test
    void artikelnummerFindetDasPassendeProdukt() throws Exception {
        Kleidungsstuecke hemd = new Kleidungsstuecke();
        hemd.setId(7L);
        hemd.setArtikelnummer("4006381333931");
        hemd.setBezeichnung("Hemd");
        hemd.setFarbe("Blau");
        hemd.setLager(1L);
        hemd.setLagerbestand(4);
        hemd.setKategorie(Kleidungsstuecke.Kategorie.HEMD);
        when(repository.findFirstByArtikelnummerIgnoreCase("4006381333931"))
                .thenReturn(Optional.of(hemd));

        mockMvc.perform(get("/api/kleidung/artikelnummer/4006381333931"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.bezeichnung").value("Hemd"));
    }

    // Prüft, dass ein negativer Bestand bei einer PUT-Anfrage abgelehnt wird.
    @Test
    void putLehntNegativenBestandAb() throws Exception {
        mockMvc.perform(put("/api/kleidung/7/bestand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "lager": 1,
                                  "lagerbestand": -1
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.feldfehler.lagerbestand").exists());
    }

    // Prüft, dass ein unbekannter Barcode mit dem HTTP-Status 404 beantwortet wird.
    @Test
    void unbekannteArtikelnummerGibt404Zurueck() throws Exception {
        when(repository.findFirstByArtikelnummerIgnoreCase("999"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/kleidung/artikelnummer/999"))
                .andExpect(status().isNotFound());
    }
}
