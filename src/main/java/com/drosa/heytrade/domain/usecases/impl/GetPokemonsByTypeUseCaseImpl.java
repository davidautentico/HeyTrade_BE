package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.enums.PokemonType;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetPokemonsByTypeUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GetPokemonsByTypeUseCaseImpl implements GetPokemonsByTypeUseCase {

  private final PokemonRepository pokemonRepository;

  public GetPokemonsByTypeUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  public Page<Pokemon> dispatch(final PokemonType pokemonType, final Pageable page){
    return pokemonRepository.findByType(pokemonType, page);
  }
}
