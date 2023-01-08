package com.drosa.heytrade.configuration;

import static com.fasterxml.jackson.core.Version.unknownVersion;

import com.drosa.heytrade.PokemonApplication;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;

@Configuration
@Import(PokemonApplication.class)
public class TestApplication {

  @Bean
  public SimpleModule jacksonPageWithJsonViewModule() {
    SimpleModule module = new SimpleModule("jackson-page-with-jsonview",
        unknownVersion());
    module.addSerializer(PageImpl.class, new PageSerializer());
    return module;
  }

}
