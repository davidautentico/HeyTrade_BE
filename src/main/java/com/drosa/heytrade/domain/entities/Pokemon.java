package com.drosa.heytrade.domain.entities;

import java.util.List;
import java.util.UUID;

import com.drosa.heytrade.domain.enums.PokemonType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Data
public class Pokemon {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
  @Type(type = "uuid-char")
  private UUID id;

  private Integer number;

  private String name;

  @Enumerated(EnumType.STRING)
  private PokemonType type1;

  @Enumerated(EnumType.STRING)
  private PokemonType type2;

  private Boolean favourite;

  @Embedded
  private PokemonCharacteristicsVO pokemonCharacteristicsVO;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "pokemon_evolution")
  private List<Pokemon> evolutions;

  public Pokemon() {}
}
