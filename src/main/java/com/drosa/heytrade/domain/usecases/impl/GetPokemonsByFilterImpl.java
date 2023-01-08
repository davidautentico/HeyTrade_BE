package com.drosa.heytrade.domain.usecases.impl;

import com.drosa.heytrade.api.rest.dtos.SearchRequestDTO;
import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.enums.PokemonType;
import com.drosa.heytrade.domain.exceptions.PokemonInvalidSearchFiltersException;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.drosa.heytrade.domain.usecases.GetPokemonsByFilterUseCase;
import com.drosa.heytrade.domain.usecases.stereotypes.UseCase;
import com.drosa.heytrade.infrastructure.configuration.CacheNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
public class GetPokemonsByFilterImpl implements GetPokemonsByFilterUseCase {

  public static final String KEY = "filterCacheKey";
  private final Logger log = LoggerFactory.getLogger(getClass());
  private final PokemonRepository pokemonRepository;

  public GetPokemonsByFilterImpl(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  @Cacheable(value = CacheNames.POKEMON_PAGEABLE_LIST_CACHE,
      key = "{#root.target.KEY, #text, #pokemonType, #favourite, #page}"
  )
  @Override
  public Page<Pokemon> dispatch(String text, PokemonType pokemonType, Boolean favourite, Pageable page) {
    log.info("Dispatch parameters: <{}>, <{}>, <{}>", text, pokemonType, favourite);
    if (text == null && pokemonType == null && favourite == null) {
      throw new PokemonInvalidSearchFiltersException("Invalid pokemon search filters");
    }
    return pokemonRepository.findByNameStartingWithAndTypeAndFavourite(text, pokemonType, favourite, page);
  }
}
