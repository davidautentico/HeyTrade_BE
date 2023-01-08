package com.drosa.heytrade.api.rest.controllers;

import java.util.UUID;

import com.drosa.heytrade.api.rest.dtos.PokemonDetailsDTO;
import com.drosa.heytrade.api.rest.dtos.SearchRequestDTO;
import com.drosa.heytrade.api.rest.mappers.PokemonMapper;
import com.drosa.heytrade.domain.usecases.GetAllPokemonsUseCase;
import com.drosa.heytrade.domain.usecases.GetPokemonUseCase;
import com.drosa.heytrade.domain.usecases.GetPokemonsByFilterUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PokemonController.path)
public class PokemonController {

  public static final String path = "/heytrade/api/v1/pokemons";

  private final GetAllPokemonsUseCase getAllPokemonsUseCase;

  private final GetPokemonUseCase getPokemonUseCase;

  private final GetPokemonsByFilterUseCase getPokemonsByFilterUseCase;

  private final PokemonMapper pokemonMapper;

  private final Logger log = LoggerFactory.getLogger(getClass());

  public PokemonController(GetAllPokemonsUseCase getAllPokemonsUseCase, GetPokemonUseCase getPokemonUseCase,
      GetPokemonsByFilterUseCase getPokemonsByFilterUseCase, PokemonMapper pokemonMapper) {
    this.getAllPokemonsUseCase = getAllPokemonsUseCase;
    this.getPokemonUseCase = getPokemonUseCase;
    this.getPokemonsByFilterUseCase = getPokemonsByFilterUseCase;
    this.pokemonMapper = pokemonMapper;
  }

  @GetMapping(value = "/", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Page<PokemonDetailsDTO>> getAllPokemons(Pageable page) {

    log.info("[Retrieve all pokemon request received]");

    var pokemonDetailsDTOPage = getAllPokemonsUseCase.dispatch(page).map(pokemonMapper::fromEntity);

    return new ResponseEntity<>(pokemonDetailsDTOPage, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<PokemonDetailsDTO> getPokemonDetails(@PathVariable("id") final UUID pokemonId) {

    log.info("[Retrieve pokemon details request received] pokemonId: " + pokemonId);

    return new ResponseEntity<>(pokemonMapper.fromEntity(getPokemonUseCase.dispatch(pokemonId)), HttpStatus.OK);
  }

  /*@GetMapping(value = "/", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Page<PokemonDetailsDTO>> findPokemonByTextAndType(
      @RequestParam(value = "text", required = false) String text,
      @RequestParam(value = "pokemon-type", required = false) PokemonType pokemonType,
      @RequestParam(value = "favourite", required = false) Boolean favourite,
      @PageableDefault(size = 20) Pageable page) {
    log.info("[Retrieve pokemons by text and type request received] text <{}>, type {}", text, pokemonType);

    var pokemonDetailsDTOPage = getPokemonsByTextAndTypeUseCase.dispatch(text, pokemonType, favourite, page)
        .map(pokemonMapper::fromEntity);

    return new ResponseEntity<>(pokemonDetailsDTOPage, HttpStatus.OK);
  }*/

  @GetMapping(value = "/search", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Page<PokemonDetailsDTO>> findPokemonByFilter(
      SearchRequestDTO searchRequestDTO,
      @PageableDefault(size = 20) Pageable page) {
    log.info("[Retrieve pokemons by filter request received] request <{}>", searchRequestDTO);

    var pokemonDetailsDTOPage =
        getPokemonsByFilterUseCase.dispatch(searchRequestDTO.getText(), searchRequestDTO.getPokemonType(), searchRequestDTO.getFavourite(),
                page)
            .map(pokemonMapper::fromEntity);

    return new ResponseEntity<>(pokemonDetailsDTOPage, HttpStatus.OK);
  }

}
