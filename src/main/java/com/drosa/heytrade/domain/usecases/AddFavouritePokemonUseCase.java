package com.drosa.heytrade.domain.usecases;

import java.util.UUID;

public interface AddFavouritePokemonUseCase {

  void dispatch(final UUID pokemonId);
}
