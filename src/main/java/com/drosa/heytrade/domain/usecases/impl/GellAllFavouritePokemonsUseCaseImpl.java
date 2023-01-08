package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetAllFavouritePokemonsUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import com.drosa.heytrade.infrastructure.configuration.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
public class GellAllFavouritePokemonsUseCaseImpl implements GetAllFavouritePokemonsUseCase {

  private final PokemonRepository pokemonRepository;

  public GellAllFavouritePokemonsUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Cacheable(cacheNames = CacheNames.POKEMON_FAVOURITES_PAGEABLE_LIST_CACHE)
  public Page<Pokemon> dispatch(final Pageable page){
    return pokemonRepository.findByFavouriteTrue(page);
  }
}
