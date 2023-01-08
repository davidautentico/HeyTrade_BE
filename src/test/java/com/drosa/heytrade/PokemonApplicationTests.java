package com.drosa.heytrade;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import java.util.UUID;

import com.drosa.heytrade.api.rest.controllers.PokemonController;
import com.drosa.heytrade.api.rest.controllers.PokemonFavouriteController;
import com.drosa.heytrade.api.rest.dtos.PokemonDetailsDTO;
import com.drosa.heytrade.api.rest.mappers.PokemonMapper;
import com.drosa.heytrade.configuration.RestPageImpl;
import com.drosa.heytrade.configuration.TestApplication;
import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.entities.PokemonCharacteristicsVO;
import com.drosa.heytrade.domain.enums.PokemonType;
import com.drosa.heytrade.domain.repositories.PokemonRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@RunWith(SpringRunner.class)
@SpringBootTest(
    properties = {
        "spring.jpa.generate-ddl=true",
        "spring.datasource.url=jdbc:tc:mysql:5.7.34:///test"
    },
    webEnvironment = RANDOM_PORT,
    classes = TestApplication.class
)
public class PokemonApplicationIntegrationTests {

  private static final String POKEMON_NAME_1 = "POKEMON NAME 1";

  private static final String POKEMON_NAME_2 = "POKEMON NAME 2";

  private static final String POKEMON_NAME_3 = "POKEMON NAME 3";

  private static final Integer POKEMON_NUMBER_1 = 1;

  private static final Integer POKEMON_NUMBER_2 = 2;

  private static final Integer POKEMON_NUMBER_3 = 3;

  private static final PokemonType POKEMON_TYPE_1 = PokemonType.BUG;

  private static final PokemonType POKEMON_TYPE_2 = PokemonType.DARK;

  private static final PokemonType POKEMON_TYPE_3 = PokemonType.FLYING;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private PokemonRepository pokemonRepository;

  @Autowired
  private PokemonMapper pokemonMapper;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @AfterEach
  void deleteEntities() {
    pokemonRepository.deleteAll();
  }

  @Test
  @DisplayName("Non favourite entity set as favourite should be persisted as favourite")
  public void whenPokemonIsSetFavourite_shouldSaveItAsFavourite() {
    // given
    var pokemon = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.FALSE);
    var savedPokemon = pokemonRepository.save(pokemon);
    var url = PokemonFavouriteController.path + "/" + savedPokemon.getId();

    // call
    restTemplate.put(url, null);

    // verify
    var favouritePokemon = pokemonRepository.findById(savedPokemon.getId());
    assertNotNull(favouritePokemon.get());
    assertThat(favouritePokemon.get().getFavourite()).isEqualTo(true);
  }

  @Test
  @DisplayName("Favourite entity removed as favourite should be persisted as not favourite")
  public void whenPokemonIsRemovedAsFavourite_shouldSavedItAsNotFavourite() {
    // given
    var pokemon = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.TRUE);
    var savedPokemon = pokemonRepository.save(pokemon);
    var url = PokemonFavouriteController.path + "/" + savedPokemon.getId();

    // call
    restTemplate.delete(url);

    // verify
    var nonFavouritePokemon = pokemonRepository.findById(savedPokemon.getId());
    assertNotNull(nonFavouritePokemon.get());
    assertThat(nonFavouritePokemon.get().getFavourite()).isEqualTo(false);
  }

  @Test
  @DisplayName("Favourite entity removed as favourite should be persisted as not favourite")
  @SneakyThrows
  public void whenCallForAllFavourites_shouldReturnAllFavourites() {
    // given
    pokemonRepository.deleteAll();
    var pokemon1 = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.FALSE);
    var savedPokemon1 = pokemonRepository.save(pokemon1);
    var pokemon2 = getPokemon(POKEMON_NAME_2, POKEMON_NUMBER_2, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon2 = pokemonRepository.save(pokemon2);
    var pokemon3 = getPokemon(POKEMON_NAME_3, POKEMON_NUMBER_3, POKEMON_TYPE_3, null, Boolean.TRUE);
    var savedPokemon3 = pokemonRepository.save(pokemon3);
    var url = PokemonFavouriteController.path + "/";

    // call
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(url, String.class);

    // verify
    Page<PokemonDetailsDTO> page = objectMapper.readValue(responseEntity.getBody(),
        new TypeReference<RestPageImpl<PokemonDetailsDTO>>() {
        });

    assertThat(page.getTotalElements()).isEqualTo(1);
    assertTrue(page.stream().toList().contains(pokemonMapper.fromEntity(pokemon3)));
  }

  @Test
  public void whenCallForExistingPokemon_shouldReturnOK() {
    // given
    var pokemon = getPokemon(POKEMON_NAME_3, POKEMON_NUMBER_3, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.TRUE);
    var savedPokemon = pokemonRepository.save(pokemon);
    var url = PokemonController.path + "/" + savedPokemon.getId();

    // call
    ResponseEntity<PokemonDetailsDTO> responseEntity =
        restTemplate.getForEntity(url, PokemonDetailsDTO.class);

    // verify
    var pokemonDetails = responseEntity.getBody();
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(pokemonDetails.getName()).isEqualTo(POKEMON_NAME_3);
    assertThat(pokemonDetails.getNumber()).isEqualTo(POKEMON_NUMBER_3);
  }

  @Test
  @DisplayName("Non favourite entity set as favourite should be persisted as favourite")
  @SneakyThrows
  public void whenCallForAllPokemons_shouldReturnOk() {
    // given
    pokemonRepository.deleteAll();
    var pokemon1 = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.FALSE);
    var savedPokemon1 = pokemonRepository.save(pokemon1);
    var pokemon2 = getPokemon(POKEMON_NAME_2, POKEMON_NUMBER_2, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon2 = pokemonRepository.save(pokemon2);
    var url = PokemonController.path + "/";

    // call
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(url, String.class);

    // verify
    Page<PokemonDetailsDTO> page = objectMapper.readValue(responseEntity.getBody(),
        new TypeReference<RestPageImpl<PokemonDetailsDTO>>() {
        });

    assertThat(page.getTotalElements()).isEqualTo(2);
    assertTrue(page.stream().toList().containsAll(List.of(pokemonMapper.fromEntity(pokemon1), pokemonMapper.fromEntity(pokemon2))));
  }

  @Test
  public void whenCallForNonUUIDPokemonId_shouldReturnBadRequest() {

    var url = PokemonController.path + "/" + "1234-4567-2345-1234";
    ResponseEntity<PokemonDetailsDTO> responseEntity =
        restTemplate.getForEntity(url, PokemonDetailsDTO.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  public void whenCallFoNonExistingPokemon_shouldReturnNotFound() {

    var url = PokemonController.path + "/" + UUID.randomUUID();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private Pokemon getPokemon(String name, Integer number, PokemonType pokemonType1, PokemonType pokemonType2, Boolean isFavourite) {
    var pokemonCharacteristics = new PokemonCharacteristicsVO();
    pokemonCharacteristics.setCombatPower(100);
    pokemonCharacteristics.setHitPoints(75);
    pokemonCharacteristics.setHeightMax(1.2);
    pokemonCharacteristics.setHeightMin(1.0);
    pokemonCharacteristics.setWeightMax(1.45);
    pokemonCharacteristics.setWeightMin(1.20);

    var pokemon = new Pokemon();
    pokemon.setName(name);
    pokemon.setNumber(number);
    pokemon.setFavourite(isFavourite);
    pokemon.setType1(pokemonType1);
    pokemon.setType2(pokemonType2);
    pokemon.setPokemonCharacteristicsVO(pokemonCharacteristics);

    return pokemon;
  }

}