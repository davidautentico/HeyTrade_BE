package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.api.rest.dtos.SearchRequestDTO;
import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.enums.PokemonType;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetPokemonsByFilterUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import com.drosa.heytrade.infrastructure.configuration.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
public class GetPokemonsByFilterImpl implements GetPokemonsByFilterUseCase {

  private final PokemonRepository pokemonRepository;

  public GetPokemonsByFilterImpl(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Cacheable(cacheNames = CacheNames.POKEMON_PAGEABLE_LIST_CACHE, key = "{ #root.targetClass, #text, #pokemonType, #page }")
  @Override
  public Page<Pokemon> dispatch(String text, PokemonType pokemonType, Boolean favourite, Pageable page) {
    return pokemonRepository.findByNameStartingWithAndTypeAndFavourite(text, pokemonType, favourite, page);
  }
}
