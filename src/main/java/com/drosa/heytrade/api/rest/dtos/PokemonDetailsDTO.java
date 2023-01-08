package com.drosa.heytrade.api.rest.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class PokemonDetailsDTO implements Serializable {

  @NotNull
  private UUID id;

  private int number;

  @NotNull
  private String name;

  private boolean favourite;

  private int combatPower;

  private int hitPoints;

  @NotNull
  private String pokemonTypeLine;

  @NotNull
  private String weighRangeLine;

  @NotNull
  private String heighRangeLine;

  private List<PokemonDetailsDTO> evolutions;

  public PokemonDetailsDTO(UUID id, int number, String name, Boolean favourite, int combatPower, int hitPoints, String pokemonTypeLine, String weighRangeLine,
      String heighRangeLine, List<PokemonDetailsDTO> evolutions){
    this.id = id;
    this.number = number;
    this.name = name;
    this.favourite = favourite;
    this.combatPower = combatPower;
    this.hitPoints = hitPoints;
    this.pokemonTypeLine = pokemonTypeLine;
    this.weighRangeLine = weighRangeLine;
    this.heighRangeLine = heighRangeLine;
    this.evolutions = evolutions;
  }

  public PokemonDetailsDTO(){}
}
