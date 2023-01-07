package com.drosa.heytrade.domain.repositories;

import java.util.UUID;

import com.drosa.heytrade.domain.entities.Pokemon;
import com.drosa.heytrade.domain.enums.PokemonType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PokemonRepository extends PagingAndSortingRepository<Pokemon, UUID> {

  @Query("SELECT p FROM Pokemon p WHERE p.name LIKE %?1%")
  Page<Pokemon> findByText(String text, Pageable pageable);

  @Query("SELECT p FROM Pokemon p WHERE p.type1 LIKE %?1%")
  Page<Pokemon> findByType(PokemonType pokemonType, Pageable pageable);

  Page<Pokemon> findByFavouriteTrue(Pageable pageable);

  @Modifying
  @Query("UPDATE Pokemon p SET favourite = true WHERE id = :pokemonId")
  int addFavouritePokemon(@Param("pokemonId") UUID pokemonId);

  @Modifying
  @Query("UPDATE TheEntity SET favourite = false WHERE id = :pokemonId")
  int removeFavouritePokemon(@Param("pokemonId") UUID pokemonId);
}
