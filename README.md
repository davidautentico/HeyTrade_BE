

# HeyTrade BE

Small Pokedex Rest API

### Setting up Dev

In order to be able to recreate the dev environment:

```shell
git clone https://github.com/davidautentico/HeyTrade_BE.git
cd HeyTrade_BE/
```
Furthermore, the dev machine should have this libraries:

- docker
- java 17
- maven 3.8

### Built With
- java 17
- Maven 3.8
- Spring Boot 2.7
- TestContainers 1.17
- Jpa

## Installing / Building

The project language is java +8-11.
As a build framework, Maven has been chosen.
Furthermore, a docker-compose file has been provided. That file contains two services:
- MySql 5.7
- Spring boot app

In order to build the project, java 17 and maven 3.8 is needed.

```shell
mvn clean package
docker-compose up
```

Executing the code above a spring-boot instance will start using a Mysql database as a backend.

```shell
mvn clean tests
```

Executing the code above the test suite is launched. For integration/e2e tests the TestContainers library has been used.

```shell
docker-compose down --rmi all -v --remove-orphans
```

The command above cleans all containers, images, volumes, networks, and undefined containers created with docker-compose

### Deploying / Publishing
A dockerfile and a docker-compose files have been provided in order to deploy the project.
The code below should start the containers with the app and the mysql instance. This has to be executed in the root project path.

```shell
docker-compose up
```

This code will start containers with the spring boot app running at port 8080 and a Mysql 5.7 as a backend database.


## Tests

Some integration/e2e tests have been provided. They are located in class PokemonApplicationIntegrationTests.
These tests try to test the api funcionality:
- Retrieve all pokemons
- Retrieve all favourite pokemons
- Retrieve one single pokemon
- Add a pokemon to favourites
- Remove a pokemon from favourites.
- Search pokemons by text
- Search pokemons by type

```shell
mvn clean test
```

## Style guide

Explain your code style and show how to check it.

## Api Reference

The interface provided by the service is a REST API. The operations are as follows.

### GET /heytrade/api/v1/pokemons

Return all pokemons.

Responses:

* **200 OK** When the service is ready to receive requests.

### GET /heytrade/api/v1/pokemons/id

Return a pokemon

**Body** _required_ A url encoded form with the id as a UUID

**Content Type** `application/x-www-form-urlencoded`

Responses:

* **200 OK** With the pokemon as the payload.
* **404 Not Found** When the pokemon is not to be found.

### GET /heytrade/api/v1/pokemons/search?favourite=_true_

Return all favourite pokemons

**Body** _required_ A url encoded form with the favourite equals to true

**Content Type** `application/x-www-form-urlencoded`

Responses:

* **200 OK** With the list of pokemons as the payload.
* **400 Bad Request** If all filters (favourite, pokemonType and text) are null

### GET /heytrade/api/v1/pokemons/search?pokemonType=_type_

Retrieve all pokemons that matches the pokemonType.

Responses:

* **200 OK** With the list of pokemons as the payload.
* **400 Bad Request** If all filters (favourite, pokemonType and text) are null

### GET /heytrade/api/v1/pokemons/search?text=_text_

Retrieve all pokemons with a name that starts with text.

Responses:

* **200 OK** With the list of pokemons as the payload.
* **400 Bad Request** If all filters (favourite, pokemonType and text) are null

### PUT /heytrade/api/v1/pokemons/pokemon-favourites/{id}

Add a pokemon, identified by its id, to the favourite collection.

**Content Type** `application/x-www-form-urlencoded`

Responses:

* **200 OK** 
* **404 Not Found** When the pokemon is not to be found.

### DELETE /heytrade/api/v1/pokemons/pokemon-favourites/{id}

Remove a pokemon, identified by its id, from the favourite collection.

**Content Type** `application/x-www-form-urlencoded`

Responses:

* **200 OK**
* **404 Not Found** When the pokemon is not to be found.

## Database

The database used is a RDMS system. A MySql 5.7 instance. 
In any case, as the jpa interface has been used, the database is subject to be changed by another one if needed.

## Licensing

Free