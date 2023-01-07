package com.drosa.heytrade.api.rest.exceptionResponses;

import com.drosa.heytrade.domain.exceptions.PokemonNotFoundException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler {

  @ExceptionHandler(ConversionFailedException.class)
  public ResponseEntity<String> handleConversionException(RuntimeException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PokemonNotFoundException.class)
  public ResponseEntity<String> handlePokemonNotFoundException(RuntimeException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }
}
