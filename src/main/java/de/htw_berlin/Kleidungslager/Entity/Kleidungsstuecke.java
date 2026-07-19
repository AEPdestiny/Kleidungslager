package de.htw_berlin.Kleidungslager.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Kleidungsstuecke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Lager ist erforderlich.")
    @Min(value = 1, message = "Lager muss mindestens 1 sein.")
    private Long lager;

    @NotNull(message = "Bestand ist erforderlich.")
    @Min(value = 0, message = "Bestand darf nicht negativ sein.")
    private Integer lagerbestand=0;

    @NotBlank(message = "Bezeichnung ist erforderlich.")
    @Size(max = 100, message = "Bezeichnung darf höchstens 100 Zeichen enthalten.")
    private String bezeichnung;

    @Size(max = 100, message = "Artikelnummer darf höchstens 100 Zeichen enthalten.")
    private String artikelnummer;

    @Size(max = 10, message = "Größe darf höchstens 10 Zeichen enthalten.")
    private String size;

    @NotBlank(message = "Farbe ist erforderlich.")
    @Size(max = 50, message = "Farbe darf höchstens 50 Zeichen enthalten.")
    private String farbe;

    @NotNull(message = "Kategorie ist erforderlich.")
    @Enumerated(EnumType.STRING)
    private Kategorie kategorie;

    @Size(max = 1000, message = "Beschreibung darf höchstens 1000 Zeichen enthalten.")
    private String beschreibung;

    @Column(columnDefinition = "TEXT")
    private String bild;

    @Transient
    private Benutzer benutzer;

    public enum Kategorie {
        HEMD, HOSE, KLEID, JACKE, SCHUHE, ACCESSOIRES, SONSTIGES
    }



    // Konstruktoren

    public Kleidungsstuecke() {
    }

    public Kleidungsstuecke(String bezeichnung, String size, Long lager, Integer lagerbestand, Benutzer benutzer) {
        this.bezeichnung = bezeichnung;
        this.size = size;
        this.lager = lager;
        this.lagerbestand = lagerbestand;
        this.benutzer = benutzer;
    }
    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBezeichnung() { return bezeichnung; }
    public void setBezeichnung(String bezeichnung) { this.bezeichnung = bezeichnung; }

    public String getArtikelnummer() { return artikelnummer; }
    public void setArtikelnummer(String artikelnummer) { this.artikelnummer = artikelnummer; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public Long getLager() { return lager; }
    public void setLager(Long lager) { this.lager = lager; }

    public Integer getLagerbestand() { return lagerbestand; }
    public void setLagerbestand(Integer lagerbestand) { this.lagerbestand = lagerbestand; }

    public String getBeschreibung() { return beschreibung; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }

    public String getBild() { return bild; }
    public void setBild(String bild) { this.bild = bild; }

    public String getFarbe() { return farbe; }
    public void setFarbe(String farbe) { this.farbe = farbe; }

    public Kategorie getKategorie() { return kategorie; }
    public void setKategorie(Kategorie kategorie) { this.kategorie = kategorie; }

    public Benutzer getBenutzer() { return benutzer; }
    public void setBenutzer(Benutzer benutzer) { this.benutzer = benutzer; }


    // Hilfsmethoden
    public boolean istAufLager() {
        return lagerbestand > 0;
    }

    public boolean istNiedrigerLagerbestand() {
        return lagerbestand <= 5;
    }

}

