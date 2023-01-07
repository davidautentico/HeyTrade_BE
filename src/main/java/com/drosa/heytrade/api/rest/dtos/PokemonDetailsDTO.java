package com.drosa.heytrade.api.rest.dtos;

import java.util.Arrays;
import java.util.UUID;

import com.drosa.heytrade.domain.entities.Pokemon;
import lombok.Builder;
import org.apache.logging.log4j.util.Strings;

@Builder
public class PokemonDetailsDTO {

  private final UUID id;

  private final int number;

  private final String name;

  private final int combatPower;

  private final int hitPoints;

  private final String pokemonTypeLine;

  private final String weighRangeLine;

  private final String heighRangeLine;
}
