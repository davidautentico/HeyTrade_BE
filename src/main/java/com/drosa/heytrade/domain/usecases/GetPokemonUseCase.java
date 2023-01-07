package com.drosa.heytrade.domain.usecases;

import java.util.UUID;

import com.drosa.heytrade.domain.entities.Pokemon;

public interface GetPokemonUseCase {

  Pokemon dispatch(final UUID pokemonId);
}
