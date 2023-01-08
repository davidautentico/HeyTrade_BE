package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetAllPokemonsUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import com.drosa.heytrade.infrastructure.configuration.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
public class GetAllPokemonsUseCaseImpl implements GetAllPokemonsUseCase {

  private final PokemonRepository pokemonRepository;

  public GetAllPokemonsUseCaseImpl(final PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Cacheable(cacheNames = CacheNames.POKEMON_PAGEABLE_LIST_CACHE, key = "{ #root.targetClass, #page }")
  public Page<Pokemon> dispatch(final Pageable page){
    return pokemonRepository.findAll(page);
  }
}
