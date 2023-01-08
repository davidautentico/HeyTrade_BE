package com.drosa.heytrade.api.rest.dtos;

import java.io.Serializable;

import com.drosa.heytrade.domain.enums.PokemonType;
import lombok.Data;

@Data
public class SearchRequestDTO implements Serializable {
  private String text;
  private PokemonType pokemonType;
  private Boolean favourite;
}
