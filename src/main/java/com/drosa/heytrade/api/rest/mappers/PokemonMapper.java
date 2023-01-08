package com.drosa.heytrade.api.rest.mappers;

import java.util.Arrays;

import com.drosa.heytrade.api.rest.dtos.PokemonDetailsDTO;
import com.drosa.heytrade.domain.entities.Pokemon;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class PokemonMapper {

  public PokemonDetailsDTO fromEntity(final Pokemon pokemon) {
    var pokemonCharacteristicsVO = pokemon.getPokemonCharacteristicsVO();
    var pokemonTypeLine =
        pokemon.getType2() == null
            ? pokemon.getType1().name()
            : Strings.join(Arrays.asList(pokemon.getType1(), pokemon.getType2()), ',');

    return PokemonDetailsDTO.builder()
        .id(pokemon.getId())
        .number(pokemon.getNumber())
        .name(pokemon.getName())
        .favourite(pokemon.getFavourite())
        .combatPower(pokemonCharacteristicsVO.getCombatPower())
        .hitPoints(pokemonCharacteristicsVO.getHitPoints())
        .pokemonTypeLine(pokemonTypeLine)
        .weighRangeLine(String.format("%.2fkg - %.2fkg", pokemonCharacteristicsVO.getWeightMin(), pokemonCharacteristicsVO.getWeightMax()))
        .heighRangeLine(String.format("%.1fm - %.1fm", pokemonCharacteristicsVO.getHeightMin(), pokemonCharacteristicsVO.getHeightMax()))
        .build();
  }

}
