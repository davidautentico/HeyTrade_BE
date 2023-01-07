package com.drosa.heytrade.domain.usecases;

import com.drosa.heytrade.domain.entities.Pokemon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetAllFavouritePokemonsUseCase {

  Page<Pokemon> dispatch(final Pageable page);
}
