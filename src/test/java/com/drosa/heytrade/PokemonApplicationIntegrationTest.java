package com.drosa.heytrade;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(
    properties = {
        "spring.jpa.generate-ddl=true",
        "spring.datasource.url=jdbc:tc:mysql:8:///test"
    },
    webEnvironment = RANDOM_PORT
)
class PokemonApplicationIntegrationTest {

}