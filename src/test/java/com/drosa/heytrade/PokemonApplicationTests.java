package com.drosa.heytrade;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import java.util.UUID;

import com.drosa.heytrade.api.rest.controllers.PokemonController;
import com.drosa.heytrade.api.rest.controllers.features.PokemonFavouriteFeatureController;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest(
    properties = {
        "spring.jpa.generate-ddl=true",
        "spring.datasource.url=jdbc:tc:mysql:5.7.34:///test"
    },
    webEnvironment = RANDOM_PORT,
    classes = TestApplication.class
)
public class PokemonApplicationTests {

  private static final String POKEMON_NAME_1 = "ABRA";

  private static final String POKEMON_NAME_1_SUFFIX_2 = "AB";

  private static final String POKEMON_NAME_2 = "ABSOL";

  private static final String POKEMON_NAME_2_SUFFIX_2 = "AB";

  private static final String POKEMON_NAME_3 = "PIKACHU";

  private static final String POKEMON_NAME_3_SUFFIX_2 = "PI";

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
  @DisplayName("Set a pokemon as favourite, it should be persisted as favourite")
  public void whenPokemonIsSetFavourite_shouldSaveItAsFavourite() {
    // given
    var pokemon = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.FALSE);
    var savedPokemon = pokemonRepository.save(pokemon);
    var url = PokemonFavouriteFeatureController.PATH + "/" + savedPokemon.getId();

    // call
    restTemplate.put(url, null);

    // verify
    var favouritePokemon = pokemonRepository.findById(savedPokemon.getId());
    assertTrue(favouritePokemon.isPresent());
    assertThat(favouritePokemon.get().getFavourite()).isEqualTo(true);
  }

  @Test
  @DisplayName("Remove a favourite pokemon as favourite, it should be persisted as not favourite")
  public void whenPokemonIsRemovedAsFavourite_shouldSavedItAsNotFavourite() {
    // given
    var pokemon = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.TRUE);
    var savedPokemon = pokemonRepository.save(pokemon);
    var url = PokemonFavouriteFeatureController.PATH + "/" + savedPokemon.getId();

    // call
    restTemplate.delete(url);

    // verify
    var nonFavouritePokemon = pokemonRepository.findById(savedPokemon.getId());
    assertTrue(nonFavouritePokemon.isPresent());
    assertThat(nonFavouritePokemon.get().getFavourite()).isEqualTo(false);
  }

  @Test
  @DisplayName("Call for all favourites, it should return the correct list and status ok")
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
    var url = PokemonController.PATH + "/search?favourite=true";

    // call
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(url, String.class);

    // verify
    Page<PokemonDetailsDTO> page = objectMapper.readValue(responseEntity.getBody(),
        new TypeReference<RestPageImpl<PokemonDetailsDTO>>() {
        });

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(page.getTotalElements()).isEqualTo(1);
    assertTrue(page.stream().toList().contains(pokemonMapper.fromEntity(pokemon3)));
  }

  @Test
  @DisplayName("Call for an existing pokemn, it should return the correct one and status ok")
  public void whenCallForExistingPokemon_shouldReturnOK() {
    // given
    var pokemon = getPokemon(POKEMON_NAME_3, POKEMON_NUMBER_3, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.TRUE);
    var savedPokemon = pokemonRepository.save(pokemon);
    var url = PokemonController.PATH + "/" + savedPokemon.getId();

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
  @DisplayName("Call for all pokemons, it should return the full collection and status ok")
  @SneakyThrows
  public void whenCallForAllPokemons_shouldReturnOk() {
    // given
    pokemonRepository.deleteAll();
    var pokemon1 = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.TRUE);
    var savedPokemon1 = pokemonRepository.save(pokemon1);
    var pokemon2 = getPokemon(POKEMON_NAME_2, POKEMON_NUMBER_2, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon2 = pokemonRepository.save(pokemon2);
    var url = PokemonController.PATH + "/";

    // call
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(url, String.class);

    // verify
    Page<PokemonDetailsDTO> page = objectMapper.readValue(responseEntity.getBody(),
        new TypeReference<RestPageImpl<PokemonDetailsDTO>>() {
        });

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(page.getTotalElements()).isEqualTo(2);
    assertTrue(page.stream().toList().containsAll(List.of(pokemonMapper.fromEntity(pokemon1), pokemonMapper.fromEntity(pokemon2))));
  }

  @Test
  @DisplayName("Call for all pokemons that start with a given name, it should return the full matching collection and status ok")
  @SneakyThrows
  public void whenCallForAllPokemonsStartedWithName_shouldReturnCorrectListOk() {
    // given
    pokemonRepository.deleteAll();
    var pokemon1 = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.FALSE);
    var savedPokemon1 = pokemonRepository.save(pokemon1);
    var pokemon2 = getPokemon(POKEMON_NAME_2, POKEMON_NUMBER_2, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon2 = pokemonRepository.save(pokemon2);
    var pokemon3 = getPokemon(POKEMON_NAME_3, POKEMON_NUMBER_3, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon3 = pokemonRepository.save(pokemon2);
    var url = PokemonController.PATH + "/search?text=" + POKEMON_NAME_2_SUFFIX_2;

    // call
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(url, String.class);

    // verify
    Page<PokemonDetailsDTO> page = objectMapper.readValue(responseEntity.getBody(),
        new TypeReference<RestPageImpl<PokemonDetailsDTO>>() {
        });

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(page.getTotalElements()).isEqualTo(2);
    assertTrue(page.stream().toList().containsAll(List.of(pokemonMapper.fromEntity(pokemon1), pokemonMapper.fromEntity(pokemon2))));
  }

  @Test
  @DisplayName("Call for all pokemons with a given type, it should return the full matching collection and status ok")
  @SneakyThrows
  public void whenCallForAllPokemonsWithGivenType_shouldReturnCorrectListOk() {
    // given
    pokemonRepository.deleteAll();
    var pokemon1 = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.FALSE);
    var savedPokemon1 = pokemonRepository.save(pokemon1);
    var pokemon2 = getPokemon(POKEMON_NAME_2, POKEMON_NUMBER_2, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon2 = pokemonRepository.save(pokemon2);
    var pokemon3 = getPokemon(POKEMON_NAME_3, POKEMON_NUMBER_3, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon3 = pokemonRepository.save(pokemon3);
    var url = PokemonController.PATH + "/search?pokemonType=" + POKEMON_TYPE_1;

    // call
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(url, String.class);

    // verify
    Page<PokemonDetailsDTO> page = objectMapper.readValue(responseEntity.getBody(),
        new TypeReference<RestPageImpl<PokemonDetailsDTO>>() {
        });

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(page.getTotalElements()).isEqualTo(1);
    assertTrue(page.stream().toList().contains(pokemonMapper.fromEntity(pokemon1)));
  }

  @Test
  @DisplayName("Call for all pokemons with a given type and text, it should return the full matching collection and status ok")
  @SneakyThrows
  public void whenCallForAllPokemonsWithGivenTextAndType_shouldReturnCorrectListOk() {
    // given
    pokemonRepository.deleteAll();
    var pokemon1 = getPokemon(POKEMON_NAME_1, POKEMON_NUMBER_1, POKEMON_TYPE_1, POKEMON_TYPE_2, Boolean.FALSE);
    var savedPokemon1 = pokemonRepository.save(pokemon1);
    var pokemon2 = getPokemon(POKEMON_NAME_2, POKEMON_NUMBER_2, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon2 = pokemonRepository.save(pokemon2);
    var pokemon3 = getPokemon(POKEMON_NAME_3, POKEMON_NUMBER_3, POKEMON_TYPE_2, POKEMON_TYPE_3, Boolean.FALSE);
    var savedPokemon3 = pokemonRepository.save(pokemon3);
    var url = PokemonController.PATH + "/search?text=" + POKEMON_NAME_1_SUFFIX_2 + "&pokemonType=" + POKEMON_TYPE_3;

    // call
    ResponseEntity<String> responseEntity =
        restTemplate.getForEntity(url, String.class);

    // verify
    Page<PokemonDetailsDTO> page = objectMapper.readValue(responseEntity.getBody(),
        new TypeReference<RestPageImpl<PokemonDetailsDTO>>() {
        });

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(page.getTotalElements()).isEqualTo(1);
    assertTrue(page.stream().toList().contains(pokemonMapper.fromEntity(pokemon2)));
  }

  @Test
  @DisplayName("Call for a single pokemon with a wrong UUID it should return bad request")
  public void whenCallForNonUUIDPokemonId_shouldReturnBadRequest() {

    var url = PokemonController.PATH + "/" + "1234-4567-2345-1234";
    ResponseEntity<PokemonDetailsDTO> responseEntity =
        restTemplate.getForEntity(url, PokemonDetailsDTO.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  @DisplayName("Call for a single pokemon that not exists it should return not found")
  public void whenCallFoNonExistingPokemon_shouldReturnNotFound() {

    var url = PokemonController.PATH + "/" + UUID.randomUUID();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  @DisplayName("Call for search using a wrong filter it should return bad request")
  public void whenCallFoNonExistingFilter_shouldReturnNotFound() {

    var url = PokemonController.PATH + "/search?anothervariable=" + UUID.randomUUID();

    ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
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