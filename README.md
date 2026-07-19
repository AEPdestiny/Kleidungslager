# Kleidungslager

Dieses Projekt ist ein Backend fuer ein Kleidungslager. Es stellt eine REST-API bereit, mit der Kleidungsstuecke im Lager verwaltet werden koennen.

## Student

Ibrahim Danisman  
Matrikelnummer: 578949

## Projektidee

Das Kleidungslager dient zur Verwaltung eingelieferter Kleidung. Kleidungsstuecke koennen mit Informationen wie Bezeichnung, Artikelnummer, Groesse, Farbe, Kategorie, Lagerplatz, Lagerbestand, Beschreibung und Bild gespeichert werden.

Der Bestand kann aktualisiert werden, Kleidungsstuecke koennen in andere Lager verschoben werden und nicht mehr benoetigte Eintraege koennen geloescht werden.

## Features

- Kleidungsstuecke anzeigen: Alle gespeicherten Kleidungsstuecke koennen ueber die API abgefragt werden.
- Kleidungsstueck anlegen: Neue Kleidung kann mit Lager, Bestand, Kategorie, Farbe, Groesse, Artikelnummer und optionalem Bild gespeichert werden.
- Bestand aktualisieren: Der Lagerbestand und der Lagerplatz eines Kleidungsstuecks koennen geaendert werden.
- Kleidungsstuecke umsortieren: Durch Aenderung des Lagerfeldes kann ein Kleidungsstueck einem anderen Lager zugeordnet werden.
- Kleidungsstueck loeschen: Einzelne Kleidungsstuecke koennen aus dem Lager entfernt werden.
- Suche per Artikelnummer: Ein Kleidungsstueck kann direkt ueber seine Artikelnummer gefunden werden.
- Automatische Zusammenfassung gleicher Artikel: Wenn ein gleicher Artikel erneut angelegt wird, wird der bestehende Lagerbestand erhoeht, statt einen komplett neuen Eintrag anzulegen.
- Kategorien: Kleidungsstuecke koennen Kategorien wie HEMD, HOSE, KLEID, JACKE, SCHUHE, ACCESSOIRES oder SONSTIGES haben.
- Eingabevalidierung: Ungueltige Daten wie negativer Lagerbestand, fehlende Pflichtfelder oder zu lange Texte werden abgelehnt.
- Verstaendliche Fehlerantworten: Validierungsfehler werden als strukturierte API-Antwort mit Feldfehlern zurueckgegeben.
- CORS-Konfiguration: Lokale Frontends und das Render-Frontend duerfen auf die API zugreifen.
- Datenbankanbindung: Das Backend ist fuer PostgreSQL konfiguriert.
- Docker-Unterstuetzung: Das Projekt kann ueber den vorhandenen Dockerfile gebaut und gestartet werden.
- Automatische Tests: Backend-Tests werden ueber GitHub Actions ausgefuehrt.

## API-Endpunkte

| Methode | Endpunkt | Beschreibung |
| --- | --- | --- |
| GET | `/api/kleidung` | Gibt alle Kleidungsstuecke zurueck |
| GET | `/api/kleidung/artikelnummer/{artikelnummer}` | Sucht ein Kleidungsstueck anhand der Artikelnummer |
| POST | `/api/kleidung` | Legt ein neues Kleidungsstueck an oder erhoeht den Bestand eines gleichen Artikels |
| PUT | `/api/kleidung/{id}/bestand` | Aktualisiert Bestand, Lager und weitere Artikeldaten |
| DELETE | `/api/kleidung/{id}` | Loescht ein Kleidungsstueck |

## Technologien

- Java 25
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Bean Validation
- PostgreSQL
- H2 fuer lokale/testbezogene Nutzung
- Gradle
- Docker
- GitHub Actions

## Konfiguration

Die Anwendung nutzt Umgebungsvariablen fuer die PostgreSQL-Datenbank:

```properties
DB_HOST=localhost
DB_PORT=5432
DB_NAME=kleidungslager
DB_USER=postgres
DB_PASSWORD=passwort
PORT=8080
```

Der Server startet standardmaessig auf Port `8080`, wenn keine andere `PORT`-Variable gesetzt ist.

## Projekt starten

Unter Windows:

```bash
./gradlew.bat bootRun
```

Unter Linux oder macOS:

```bash
./gradlew bootRun
```

## Tests ausfuehren

Unter Windows:

```bash
./gradlew.bat test
```

Unter Linux oder macOS:

```bash
./gradlew test
```

## GitHub-Hinweis

Diese Datei liegt im Hauptverzeichnis des Repositories als `README.md`. Dadurch wird sie automatisch angezeigt, sobald das Repository auf GitHub geoeffnet wird.
