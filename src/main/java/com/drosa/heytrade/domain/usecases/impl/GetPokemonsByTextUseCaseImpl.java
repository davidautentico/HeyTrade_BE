package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetPokemonsByTextUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GetPokemonsByTextUseCaseImpl implements GetPokemonsByTextUseCase {

  private final PokemonRepository pokemonRepository;

  public GetPokemonsByTextUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  public Page<Pokemon> dispatch(final String text, final Pageable page){
    return pokemonRepository.findByText(text, page);
  }
}
