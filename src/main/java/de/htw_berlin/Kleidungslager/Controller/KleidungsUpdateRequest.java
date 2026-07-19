package de.htw_berlin.Kleidungslager.Controller;

import de.htw_berlin.Kleidungslager.Entity.Kleidungsstuecke;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class KleidungsUpdateRequest {

    @Pattern(regexp = ".*\\S.*", message = "Bezeichnung darf nicht leer sein.")
    @Size(max = 100, message = "Bezeichnung darf höchstens 100 Zeichen enthalten.")
    private String bezeichnung;

    @Size(max = 100, message = "Artikelnummer darf höchstens 100 Zeichen enthalten.")
    private String artikelnummer;

    @Size(max = 10, message = "Größe darf höchstens 10 Zeichen enthalten.")
    private String size;

    @Pattern(regexp = ".*\\S.*", message = "Farbe darf nicht leer sein.")
    @Size(max = 50, message = "Farbe darf höchstens 50 Zeichen enthalten.")
    private String farbe;

    private Kleidungsstuecke.Kategorie kategorie;

    @NotNull(message = "Lager ist erforderlich.")
    @Min(value = 1, message = "Lager muss mindestens 1 sein.")
    private Long lager;

    @NotNull(message = "Bestand ist erforderlich.")
    @Min(value = 0, message = "Bestand darf nicht negativ sein.")
    private Integer lagerbestand;

    private String bild;

    public String getBezeichnung() { return bezeichnung; }
    public void setBezeichnung(String bezeichnung) { this.bezeichnung = bezeichnung; }

    public String getArtikelnummer() { return artikelnummer; }
    public void setArtikelnummer(String artikelnummer) { this.artikelnummer = artikelnummer; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getFarbe() { return farbe; }
    public void setFarbe(String farbe) { this.farbe = farbe; }

    public Kleidungsstuecke.Kategorie getKategorie() { return kategorie; }
    public void setKategorie(Kleidungsstuecke.Kategorie kategorie) { this.kategorie = kategorie; }

    public Long getLager() { return lager; }
    public void setLager(Long lager) { this.lager = lager; }

    public Integer getLagerbestand() { return lagerbestand; }
    public void setLagerbestand(Integer lagerbestand) { this.lagerbestand = lagerbestand; }

    public String getBild() { return bild; }
    public void setBild(String bild) { this.bild = bild; }
}
