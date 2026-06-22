package de.htw_berlin.Kleidungslager;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import de.htw_berlin.Kleidungslager.Repository.KleidungRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class KleidungCrudIntegrationTests {

    @Container
    static final PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine")
            .withDatabaseName("kleidungslager_test")
            .withUsername("test_user")
            .withPassword("test_password");

    @DynamicPropertySource
    static void datenbankKonfigurieren(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private KleidungRepository repository;

    private RestClient restClient;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        restClient = RestClient.create("http://localhost:" + port);
    }

    // Prüft POST, GET, Barcode-Suche, PUT und DELETE gegen eine echte temporäre PostgreSQL-Datenbank.
    @Test
    void vollstaendigerCrudAblaufMitPostgreSqlUndBarcodeSuche() {
        Kleidungsstuecke neu = neuesKleidungsstueck();

        Kleidungsstuecke erstellt = restClient.post()
                .uri("/api/kleidung")
                .contentType(MediaType.APPLICATION_JSON)
                .body(neu)
                .retrieve()
                .body(Kleidungsstuecke.class);

        assertThat(erstellt).isNotNull();
        assertThat(erstellt.getId()).isNotNull();

        List<Kleidungsstuecke> nachErstellen = alleKleidungsstuecke();
        assertThat(nachErstellen)
                .extracting(Kleidungsstuecke::getArtikelnummer)
                .containsExactly("4006381333931");

        Kleidungsstuecke ueberBarcode = restClient.get()
                .uri("/api/kleidung/artikelnummer/{artikelnummer}", "4006381333931")
                .retrieve()
                .body(Kleidungsstuecke.class);

        assertThat(ueberBarcode).isNotNull();
        assertThat(ueberBarcode.getId()).isEqualTo(erstellt.getId());

        String updateJson = """
                {
                  "bezeichnung": "Sneaker",
                  "artikelnummer": "4006381333931",
                  "size": "42,5",
                  "kategorie": "SCHUHE",
                  "farbe": "Weiß",
                  "lager": 2,
                  "lagerbestand": 7,
                  "bild": ""
                }
                """;

        Kleidungsstuecke aktualisiert = restClient.put()
                .uri("/api/kleidung/{id}/bestand", erstellt.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(updateJson)
                .retrieve()
                .body(Kleidungsstuecke.class);

        assertThat(aktualisiert).isNotNull();
        assertThat(aktualisiert.getLagerbestand()).isEqualTo(7);
        assertThat(aktualisiert.getLager()).isEqualTo(2L);
        assertThat(aktualisiert.getSize()).isEqualTo("42,5");

        ResponseEntity<Void> deleteAntwort = restClient.delete()
                .uri("/api/kleidung/{id}", erstellt.getId())
                .retrieve()
                .toBodilessEntity();

        assertThat(deleteAntwort.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(alleKleidungsstuecke()).isEmpty();
    }

    // Prüft, dass die Validierung auch im vollständig gestarteten System ungültige Daten verhindert.
    @Test
    void ungueltigerPostWirdAuchImVollstaendigenSystemAbgelehnt() {
        String ungueltig = """
                {
                  "bezeichnung": "",
                  "kategorie": "HEMD",
                  "farbe": "",
                  "lager": 0,
                  "lagerbestand": -1
                }
                """;

        assertThatThrownBy(() -> restClient.post()
                .uri("/api/kleidung")
                .contentType(MediaType.APPLICATION_JSON)
                .body(ungueltig)
                .retrieve()
                .toBodilessEntity())
                .isInstanceOf(RestClientResponseException.class)
                .satisfies(exception -> assertThat(
                        ((RestClientResponseException) exception).getStatusCode()
                ).isEqualTo(HttpStatus.BAD_REQUEST));

        assertThat(repository.count()).isZero();
    }

    private List<Kleidungsstuecke> alleKleidungsstuecke() {
        return restClient.get()
                .uri("/api/kleidung")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    private Kleidungsstuecke neuesKleidungsstueck() {
        Kleidungsstuecke teil = new Kleidungsstuecke();
        teil.setArtikelnummer("4006381333931");
        teil.setBezeichnung("Sneaker");
        teil.setSize("42");
        teil.setKategorie(Kleidungsstuecke.Kategorie.SCHUHE);
        teil.setFarbe("Weiß");
        teil.setLager(1L);
        teil.setLagerbestand(3);
        teil.setBild("");
        return teil;
    }
}
