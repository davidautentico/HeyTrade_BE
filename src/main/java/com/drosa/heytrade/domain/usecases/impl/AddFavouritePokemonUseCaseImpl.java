package com.drosa.heytrade.domain.usecases.impl;

import java.util.UUID;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.exceptions.PokemonNotFoundException;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.AddFavouritePokemonUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import com.drosa.heytrade.infrastructure.configuration.CacheNames;
import javax.persistence.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class AddFavouritePokemonUseCaseImpl implements AddFavouritePokemonUseCase {

  private final PokemonRepository pokemonRepository;

  public AddFavouritePokemonUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Transactional
  @Caching(
      put = {
          @CachePut(cacheNames = CacheNames.POKEMON_CACHE, key = "#pokemonId")
      },
      evict = {
          @CacheEvict(value = CacheNames.POKEMON_PAGEABLE_LIST_CACHE, allEntries = true),
          @CacheEvict(value = CacheNames.POKEMON_FAVOURITES_PAGEABLE_LIST_CACHE, allEntries = true)
      }
  )
  public Pokemon dispatch(final UUID pokemonId) {

    var rowsAffected = pokemonRepository.addFavouritePokemon(pokemonId);
    if (rowsAffected == 0) {
      throw new PokemonNotFoundException(String.format("Pokemon with id <%s> not found after added to favourites", pokemonId));
    }

    return pokemonRepository.findById(pokemonId)
        .orElseThrow(() -> new PokemonNotFoundException(String.format("Pokemon with id <%s> not found", pokemonId)));
  }
}
