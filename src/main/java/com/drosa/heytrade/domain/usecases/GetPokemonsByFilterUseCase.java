package com.drosa.heytrade.domain.usecases;

import com.drosa.heytrade.api.rest.dtos.SearchRequestDTO;
import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.enums.PokemonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPokemonsByFilterUseCase {

  public Page<Pokemon> dispatch(String text, PokemonType pokemonType, Boolean favourite, Pageable page);
}
