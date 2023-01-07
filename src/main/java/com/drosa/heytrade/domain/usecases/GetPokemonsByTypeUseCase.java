package com.drosa.heytrade.domain.usecases;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.enums.PokemonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPokemonsByTypeUseCase {

  Page<Pokemon> dispatch(final PokemonType pokemonType, final Pageable page);
}
