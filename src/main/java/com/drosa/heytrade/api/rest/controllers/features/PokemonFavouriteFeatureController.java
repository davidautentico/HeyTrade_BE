package com.drosa.heytrade.api.rest.controllers.features;

import java.util.UUID;

import com.drosa.heytrade.domain.usecases.features.AddFavouritePokemonUseCase;
import com.drosa.heytrade.domain.usecases.features.DeleteFavouritePokemonUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PokemonFavouriteFeatureController.PATH)
public class PokemonFavouriteFeatureController {

  public static final String PATH = "/heytrade/api/v1/pokemons/pokemon-favourites";

  private final AddFavouritePokemonUseCase addFavouritePokemonUseCase;

  private final DeleteFavouritePokemonUseCase deleteFavouritePokemonUseCase;

  private final Logger log = LoggerFactory.getLogger(getClass());

  public PokemonFavouriteFeatureController(
      AddFavouritePokemonUseCase addFavouritePokemonUseCase, DeleteFavouritePokemonUseCase deleteFavouritePokemonUseCase) {
    this.addFavouritePokemonUseCase = addFavouritePokemonUseCase;
    this.deleteFavouritePokemonUseCase = deleteFavouritePokemonUseCase;
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void addFavouritePokemon(@PathVariable("id") final UUID pokemonId) {
    log.info("[Add pokemon to favourites request received] pokemon <{}>", pokemonId);

    addFavouritePokemonUseCase.dispatch(pokemonId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void removeFavouritePokemon(@PathVariable("id") final UUID pokemonId) {
    log.info("[Remove pokemon from favourites request received] pokemon <{}>", pokemonId);

    deleteFavouritePokemonUseCase.dispatch(pokemonId);
  }
}
