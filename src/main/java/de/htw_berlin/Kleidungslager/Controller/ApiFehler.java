package de.htw_berlin.Kleidungslager.Controller;

import java.util.Map;

public record ApiFehler(
        String meldung,
        Map<String, String> feldfehler
) {
}
