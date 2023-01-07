package com.drosa.heytrade.domain.usecases;

import com.drosa.heytrade.domain.entities.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPokemonsByTextUseCase {

  Page<Pokemon> dispatch(final String text, final Pageable page);
}
