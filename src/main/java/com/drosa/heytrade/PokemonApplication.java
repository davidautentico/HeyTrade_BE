package com.drosa.heytrade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
public class PokemonApplication {

  public static void main(String[] args) {

    SpringApplication.run(PokemonApplication.class, args);

    Runtime.getRuntime().addShutdownHook(new Thread(PokemonApplication::shutdown));

    log.info("***************** PokemonApplication Started *******************");
  }

  private static void shutdown() {
    log.info("***************** PokemonApplication Stopped *******************");
  }
}
