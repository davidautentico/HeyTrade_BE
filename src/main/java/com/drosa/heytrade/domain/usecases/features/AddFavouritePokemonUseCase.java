package com.drosa.heytrade.domain.usecases.features;

import java.util.UUID;

import com.drosa.heytrade.domain.entities.Pokemon;

public interface AddFavouritePokemonUseCase {

  Pokemon dispatch(final UUID pokemonId);
}
