<img src="./images/logo.sample.png" alt="Logo of the project" align="right">

# HeyTrade BE &middot; [![Build Status](https://img.shields.io/travis/npm/npm/latest.svg?style=flat-square)](https://travis-ci.org/npm/npm) [![npm](https://img.shields.io/npm/v/npm.svg?style=flat-square)](https://www.npmjs.com/package/npm) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com) [![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?style=flat-square)](https://github.com/your/your-project/blob/master/LICENSE)
> Additional information or tag line

Small Pokedex Rest API

## Installing / Getting started

The project language is java +8-11.
Maven framework has been used in order to build it.
A docker-compose file has been provided. That file contains two services:
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

## Developing

### Built With
- java 17
- Maven 3.8
- Spring Boot 2.7
- TestContainers 1.17
- Jpa

### Prerequisites
What is needed to set up the dev environment. For instance, global dependencies or any other tools. include download links.


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

### Building

The code below should build the project. This has to be executed in the root project path.

```shell
mvn clean package
```

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

If the api is external, link to api documentation. If not describe your api including authentication methods as well as explaining all the endpoints with their required parameters.


## Database

The database used is a RDMS system. A MySql 5.7 instance. 
In any case, as the jpa interface has been used, the database is subject to be changed by another one if needed.

## Licensing

Free