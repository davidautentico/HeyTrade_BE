package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetAllFavouritePokemonsUseCase;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class GellAllFavouritePokemonsUseCaseImpl implements GetAllFavouritePokemonsUseCase {

  private final PokemonRepository pokemonRepository;

  public GellAllFavouritePokemonsUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Cacheable(cacheNames = "pokemon-favourites")
  public Page<Pokemon> dispatch(final Pageable page){
    return pokemonRepository.findByFavouriteTrue(page);
  }
}
