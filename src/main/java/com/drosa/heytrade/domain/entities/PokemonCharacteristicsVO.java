package com.drosa.heytrade.domain.entities;

import javax.persistence.Embeddable;
import lombok.Data;


@Data
@Embeddable
public class PokemonCharacteristicsVO {

  private Integer combatPower;

  private Integer hitPoints;

  private Double weightMin;

  private Double weightMax;

  private Double heightMin;

  private Double heightMax;
}
