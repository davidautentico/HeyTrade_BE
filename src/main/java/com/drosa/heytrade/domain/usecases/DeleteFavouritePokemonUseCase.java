package com.drosa.heytrade.domain.usecases;

import java.util.UUID;

public interface DeleteFavouritePokemonUseCase {

  public void dispatch(final UUID pokemonId);
}
