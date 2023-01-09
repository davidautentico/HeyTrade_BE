package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.enums.PokemonType;
import com.drosa.heytrade.domain.exceptions.PokemonInvalidSearchFiltersException;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetPokemonsByFilterUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import com.drosa.heytrade.infrastructure.configuration.CacheNames;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@UseCase
public class GetPokemonsByFilterImpl implements GetPokemonsByFilterUseCase {

  public static final String KEY = "filterCacheKey";

  private final PokemonRepository pokemonRepository;

  public GetPokemonsByFilterImpl(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Cacheable(value = CacheNames.POKEMON_PAGEABLE_LIST_CACHE,
      key = "{#root.target.KEY, #text, #pokemonType, #favourite, #page}"
  )
  @Override
  public Page<Pokemon> dispatch(String text, PokemonType pokemonType, Boolean favourite, Pageable page) {
    if (text == null && pokemonType == null && favourite == null) {
      throw new PokemonInvalidSearchFiltersException("Invalid pokemon search filters");
    }
    return pokemonRepository.findByNameStartingWithAndTypeAndFavourite(text, pokemonType, favourite, page);
  }
}
