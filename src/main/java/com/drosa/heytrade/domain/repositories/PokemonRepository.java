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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PokemonRepository extends PagingAndSortingRepository<Pokemon, UUID> {

  @Query(
      "SELECT p FROM Pokemon p WHERE (:favourite is NULL or favourite = :favourite) "
          + " and (:text is NULL or UPPER(p.name) LIKE CONCAT(UPPER(:text),'%')) "
          + " and (:pokemonType is NULL or (p.type1 = :pokemonType or p.type2 = :pokemonType))")
  Page<Pokemon> findByNameStartingWithAndTypeAndFavourite(String text, PokemonType pokemonType, Boolean favourite, Pageable pageable);

  Page<Pokemon> findByFavouriteTrue(Pageable pageable);

  @Modifying(flushAutomatically = true)
  @Query("UPDATE Pokemon SET favourite = true WHERE id = :pokemonId")
  int addFavouritePokemon(@Param("pokemonId") UUID pokemonId);

  @Modifying(flushAutomatically = true)
  @Query("UPDATE Pokemon SET favourite = false WHERE id = :pokemonId")
  int removeFavouritePokemon(@Param("pokemonId") UUID pokemonId);
}
