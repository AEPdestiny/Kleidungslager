package de.htw_berlin.Kleidungslager.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiFehler handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> feldfehler = new LinkedHashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(fehler ->
                feldfehler.putIfAbsent(fehler.getField(), fehler.getDefaultMessage())
        );

        return new ApiFehler(
                "Die Eingaben sind nicht gültig.",
                feldfehler
        );
    }
}
