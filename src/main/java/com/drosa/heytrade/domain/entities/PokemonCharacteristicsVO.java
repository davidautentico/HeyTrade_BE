package com.drosa.heytrade.domain.entities;

import javax.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;


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
