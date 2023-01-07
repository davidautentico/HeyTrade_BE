package com.drosa.heytrade.domain.usecases.impl;

import java.util.UUID;

import com.drosa.heytrade.domain.exceptions.PokemonNotFoundException;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.DeleteFavouritePokemonUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import org.springframework.cache.annotation.CacheEvict;

@UseCase
public class DeleteFavouritePokemonUseCaseImpl implements DeleteFavouritePokemonUseCase {

  private final PokemonRepository pokemonRepository;

  public DeleteFavouritePokemonUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @CacheEvict(value = "pokemon-favourites", key = "#pokemonId")
  public void dispatch(final UUID pokemonId) {

    var rowsAffected = pokemonRepository.removeFavouritePokemon(pokemonId);
    if (rowsAffected == 0) {
      throw new PokemonNotFoundException(String.format("Pokemon with id <%s> not found", pokemonId));
    }
  }
}
