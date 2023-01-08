package com.drosa.heytrade.domain.usecases;

import java.util.UUID;

import com.drosa.heytrade.domain.entities.Pokemon;

public interface DeleteFavouritePokemonUseCase {

  public Pokemon dispatch(final UUID pokemonId);
}
