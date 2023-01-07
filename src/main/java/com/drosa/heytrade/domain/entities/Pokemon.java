package com.drosa.heytrade.domain.entities;

import java.util.List;
import java.util.UUID;

import com.drosa.heytrade.domain.enums.PokemonType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import lombok.Getter;

@Getter
@Entity
public class Pokemon {
  @Id
  @GeneratedValue
  private UUID id;

  private Integer number;

  private String name;

  @Enumerated(EnumType.STRING)
  private PokemonType type1;

  @Enumerated(EnumType.STRING)
  private PokemonType type2;

  private int combatPower;

  private int hitPoints;

  private double weightMin;

  private double weightMax;

  private double heightMin;

  private double heightMax;

  private boolean favourite;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name ="pokemon_evolution")
  private List<Pokemon> evolutions;
}
