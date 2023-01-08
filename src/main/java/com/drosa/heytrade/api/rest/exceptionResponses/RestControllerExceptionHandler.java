package com.drosa.heytrade.api.rest.exceptionResponses;

import com.drosa.heytrade.domain.exceptions.PokemonInvalidSearchFiltersException;
import com.drosa.heytrade.domain.exceptions.PokemonNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(ConversionFailedException.class)
  public ResponseEntity<String> handleConversionException(RuntimeException ex) {
    log.error("Conversion exception ", ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PokemonNotFoundException.class)
  public ResponseEntity<String> handlePokemonNotFoundException(RuntimeException ex) {
    log.error("Pokemon Not Found Exception ", ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PokemonInvalidSearchFiltersException.class)
  public ResponseEntity<String> handlePokemonInvalidSearchFiltersException(RuntimeException ex) {
    log.error("Pokemon invalid search filter exception ", ex);
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
