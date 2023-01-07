package com.drosa.heytrade.domain.exceptions;

public class PokemonNotFoundException extends RuntimeException {
  public PokemonNotFoundException(String message) {
    super(message);
  }
}
