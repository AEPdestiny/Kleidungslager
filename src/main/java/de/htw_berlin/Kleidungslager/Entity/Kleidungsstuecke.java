package de.htw_berlin.Kleidungslager.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Kleidungsstuecke {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lager;

    private Integer lagerbestand=0;

    private String bezeichnung;

    @Enumerated(EnumType.STRING)
    private Size size;

    private String farbe;

    @Enumerated(EnumType.STRING)
    private Kategorie kategorie;

    private String beschreibung;

    @Transient
    private Benutzer benutzer;

    public enum Size {
        XS, S, M, L, XL, XXL, XXXL
    }

    public enum Kategorie {
        HEMD, HOSE, KLEID, JACKE, SCHUHE, ACCESSOIRES, SONSTIGES
    }



    // Konstruktoren

    public Kleidungsstuecke() {
    }

    public Kleidungsstuecke(String bezeichnung, Size size, Long lager, Integer lagerbestand, Benutzer benutzer) {
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

    public Size getSize() { return size; }
    public void setSize(Size size) { this.size = size; }

    public Long getLager() { return lager; }
    public void setLager(Long lager) { this.lager = lager; }

    public Integer getLagerbestand() { return lagerbestand; }
    public void setLagerbestand(Integer lagerbestand) { this.lagerbestand = lagerbestand; }

    public String getBeschreibung() { return beschreibung; }
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }

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

