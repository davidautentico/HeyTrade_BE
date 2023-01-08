package com.drosa.heytrade.domain.usecases.impl;

import java.util.UUID;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.exceptions.PokemonNotFoundException;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetPokemonUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import com.drosa.heytrade.infrastructure.configuration.CacheNames;
import org.springframework.cache.annotation.Cacheable;

@UseCase
public class GetPokemonUseCaseImpl implements GetPokemonUseCase {

  private final PokemonRepository pokemonRepository;

  public GetPokemonUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Cacheable(cacheNames = CacheNames.POKEMON_CACHE, key = "#pokemonId")
  public Pokemon dispatch(final UUID pokemonId) {

    return pokemonRepository.findById(pokemonId)
        .orElseThrow(() -> new PokemonNotFoundException(String.format("Pokemon with id <%s> not found", pokemonId)));
  }
}
