package com.drosa.heytrade.domain.usecases.impl;

import java.util.UUID;

import com.drosa.heytrade.domain.exceptions.PokemonNotFoundException;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.AddFavouritePokemonUseCase;
import org.springframework.cache.annotation.CachePut;

public class AddFavouritePokemonUseCaseImpl implements AddFavouritePokemonUseCase {

  private final PokemonRepository pokemonRepository;

  public AddFavouritePokemonUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @CachePut(value = "pokemon-favourites")
  public void dispatch(final UUID pokemonId) {

    var rowsAffected = pokemonRepository.addFavouritePokemon(pokemonId);
    if (rowsAffected == 0) {
      throw new PokemonNotFoundException(String.format("Pokemon with id <%s> not found", pokemonId));
    }
  }
}
