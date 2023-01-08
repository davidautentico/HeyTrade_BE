package com.drosa.heytrade.api.rest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.drosa.heytrade.api.rest.dtos.PokemonDetailsDTO;
import com.drosa.heytrade.api.rest.mappers.PokemonMapper;
import com.drosa.heytrade.domain.enums.PokemonType;
import com.drosa.heytrade.domain.usecases.GetAllPokemonsUseCase;
import com.drosa.heytrade.domain.usecases.GetPokemonUseCase;
import com.drosa.heytrade.domain.usecases.GetPokemonsByTextUseCase;
import com.drosa.heytrade.domain.usecases.GetPokemonsByTypeUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  private final GetPokemonsByTypeUseCase getPokemonsByTypeUseCase;

  private final GetPokemonsByTextUseCase getPokemonsByTextUseCase;

  private final PokemonMapper pokemonMapper;

  private final Logger log = LoggerFactory.getLogger(getClass());

  public PokemonController(GetAllPokemonsUseCase getAllPokemonsUseCase, GetPokemonUseCase getPokemonUseCase,
      GetPokemonsByTypeUseCase getPokemonsByTypeUseCase, GetPokemonsByTextUseCase getPokemonsByTextUseCase, PokemonMapper pokemonMapper) {
    this.getAllPokemonsUseCase = getAllPokemonsUseCase;
    this.getPokemonUseCase = getPokemonUseCase;
    this.getPokemonsByTypeUseCase = getPokemonsByTypeUseCase;
    this.getPokemonsByTextUseCase = getPokemonsByTextUseCase;
    this.pokemonMapper = pokemonMapper;
  }

  @GetMapping(value = "/", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Page<PokemonDetailsDTO>> getAllPokemons(Pageable page) {

    log.info("[Retrieve all pokemons request received]");

    var pokemonDetailsDTOPage = getAllPokemonsUseCase.dispatch(page)
        .map(pokemonMapper::fromEntity);

    return new ResponseEntity<>(pokemonDetailsDTOPage, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<PokemonDetailsDTO> getPokemonDetails(@PathVariable("id") final UUID pokemonId) {

    log.info("[Retrieve pokemon details request received] pokemonId: " + pokemonId);

    return new ResponseEntity<>(pokemonMapper.fromEntity(getPokemonUseCase.dispatch(pokemonId)), HttpStatus.OK);
  }

  @GetMapping(value = "/pokemon-types/{pokemon-type}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Page<PokemonDetailsDTO>> findPokemonByType(@PathVariable("pokemon-type") PokemonType pokemonType, Pageable page) {
    log.info("[Retrieve pokemons by type request received] pokemon type <{}>", pokemonType);

    var pokemonDetailsDTOPage = getPokemonsByTypeUseCase.dispatch(pokemonType, page)
        .map(pokemonMapper::fromEntity);

    return new ResponseEntity<>(pokemonDetailsDTOPage, HttpStatus.OK);
  }

  @GetMapping(value = "/pokemon-names/{text}", produces = "application/json")
  @ResponseBody
  public ResponseEntity<Page<PokemonDetailsDTO>> findPokemonByText(String text, Pageable page) {
    log.info("[Retrieve pokemons by text request received] text <{}>", text);

    var pokemonDetailsDTOPage = getPokemonsByTextUseCase.dispatch(text, page)
        .map(pokemonMapper::fromEntity);

    return new ResponseEntity<>(pokemonDetailsDTOPage, HttpStatus.OK);
  }
}
