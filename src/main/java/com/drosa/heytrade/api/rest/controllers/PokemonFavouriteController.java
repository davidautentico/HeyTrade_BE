package com.drosa.heytrade.api.rest;

import java.util.UUID;

import com.drosa.heytrade.api.rest.dtos.PokemonDetailsDTO;
import com.drosa.heytrade.api.rest.mappers.PokemonMapper;
import com.drosa.heytrade.domain.usecases.AddFavouritePokemonUseCase;
import com.drosa.heytrade.domain.usecases.DeleteFavouritePokemonUseCase;
import com.drosa.heytrade.domain.usecases.GetAllFavouritePokemonsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PokemonFavouriteController.path)
public class PokemonFavouriteController {

  public static final String path = "/heytrade/api/v1/pokemons/pokemon-favourites";

  private final GetAllFavouritePokemonsUseCase getAllFavouritePokemonsUseCase;

  private final AddFavouritePokemonUseCase addFavouritePokemonUseCase;

  private final DeleteFavouritePokemonUseCase deleteFavouritePokemonUseCase;

  private final PokemonMapper pokemonMapper;

  private final Logger log = LoggerFactory.getLogger(getClass());

  public PokemonFavouriteController(GetAllFavouritePokemonsUseCase getAllFavouritePokemonsUseCase,
      AddFavouritePokemonUseCase addFavouritePokemonUseCase, DeleteFavouritePokemonUseCase deleteFavouritePokemonUseCase,
      PokemonMapper pokemonMapper) {
    this.getAllFavouritePokemonsUseCase = getAllFavouritePokemonsUseCase;
    this.addFavouritePokemonUseCase = addFavouritePokemonUseCase;
    this.deleteFavouritePokemonUseCase = deleteFavouritePokemonUseCase;
    this.pokemonMapper = pokemonMapper;
  }

  @GetMapping(value = "/", produces = "application/json")
  public ResponseEntity<Page<PokemonDetailsDTO>> getFavouritePokemons(Pageable page){

    log.info("[Retrieve all favourite pokemons request received]");

    var pokemonDetailsDTOPage = getAllFavouritePokemonsUseCase.dispatch(page)
        .map(pokemonMapper::fromEntity);

    return new ResponseEntity<>(pokemonDetailsDTOPage, HttpStatus.OK);
  }

  @PutMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void addFavouritePokemon(@PathVariable("id") final UUID pokemonId){
    log.info("[Add pokemon to favourites request received] pokemon <{}>", pokemonId);

    addFavouritePokemonUseCase.dispatch(pokemonId);
  }


  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void removeFavouritePokemon(@PathVariable("id") final UUID pokemonId){
    log.info("[Remove pokemon to favourites request received] pokemon <{}>", pokemonId);

    deleteFavouritePokemonUseCase.dispatch(pokemonId);
  }
}
