package com.drosa.heytrade.api.rest.mappers;

import java.util.Arrays;

import com.drosa.heytrade.api.rest.dtos.PokemonDetailsDTO;
import com.drosa.heytrade.domain.entities.Pokemon;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class PokemonMapper {

  public PokemonDetailsDTO fromEntity(final Pokemon pokemon){
    return PokemonDetailsDTO.builder()
        .id(pokemon.getId())
        .number(pokemon.getNumber())
        .name(pokemon.getName())
        .combatPower(pokemon.getCombatPower())
        .hitPoints(pokemon.getHitPoints())
        .pokemonTypeLine(Strings.join(Arrays.asList(pokemon.getType1(),pokemon.getType2()),','))
        .weighRangeLine(String.format("%.2fkg - %.2fkg",pokemon.getWeightMin(),pokemon.getWeightMax()))
        .heighRangeLine(String.format("%.1fm - %.1fm",pokemon.getHeightMin(),pokemon.getHeightMax()))
        .build();
  }

}
