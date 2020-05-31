# Objective-Partners-App

## Requirements
For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
- [Docker (19.03.9)](https://docs.docker.com/engine/install/)
- [Docker Compose (1.21.2)](https://docs.docker.com/compose/install/)


# Docker containers.

## Create containers 
To create punkapi database, we are using Docker-compose to build a mysql container (also an 'adminer container' to have access to the database. you can see the credential in docker-compose.yml).

To create the containers, please run the follow command (inside of the docker folder)

- docker-compose up -d


# REST API, PunkApi application

This is a example of a Spring-Boot application providing a REST API.

## Run the app

    mvn spring-boot:run

## Run the tests

    mvn test

# REST API

The REST API to the punkapi app is described below.

## Get all beers (325 records)

### Request

`GET /v2/beers/getAllBeers`

    curl -i -H 'Accept: application/json' http://localhost:8080/v2/beers/getAllBeers

### Response

    Request URL: http://localhost:8080/v2/beers/getAllBeers
    Request Method: GET
    Status Code: 200 
    Remote Address: [::1]:8080

    [{
    "id": 1,
    "name": "Buzz",
    "description": "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
    "abv": 4.5,
    "tagline": "A Real Bitter Experience.",
    "first_brewed": "09/2007",
    "image_url": "https://images.punkapi.com/v2/keg.png"
    }.....
    

## Get beer by ID
### Request

`GET /v2/beers/30

    curl -i -H 'Accept: application/json' http://localhost:8080/v2/beers/30

### Response

    Request URL: http://localhost:8080/v2/beers/30
    Request Method: GET
    Status Code: 200
    Remote Address: [::1]:8080
    
    [{
    "id": 30,
    "name": "Dana - IPA Is Dead",
    "description": "Hailing from Slovenia, Dana was originally cross bred from the German Hallertau Magnum and native            Slovenian varieties. Like any good faux noble hop should, it infuses a rustic, musty spiciness into a toasty beast of a      malt base.",
    "abv": 6.7,
    "tagline": "Single Hop India Pale Ale.",
    "first_brewed": "02/2013",
    "image_url": "https://images.punkapi.com/v2/30.png"
    }...


## Get list of beers paginated

### Request

`GET /v2/beers?page=1&per_page=10`

    curl -i -H 'Accept: application/json' http://localhost:8080/v2/beers?page=1&per_page=10

### Response

    Request URL: http://localhost:8080/v2/beers?page=1&per_page=10
    Request Method: GET
    Status Code: 200 
    Remote Address: [::1]:8080

    [{
    "id": 1,
    "name": "Buzz",
    "description": "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once.",
    "abv": 4.5,
    "tagline": "A Real Bitter Experience.",
    "first_brewed": "09/2007",
    "image_url": "https://images.punkapi.com/v2/keg.png"
    }.....


## Get list of beers by name

### Request

`GET /v2/beers?beer_name=IPA`

    curl -i -H 'Accept: application/json' http://localhost:8080/v2/beers?beer_name=IPA

### Response

    Request URL: http://localhost:8080/v2/beers?beer_name=IPA
    Request Method: GET
    Status Code: 200 
    Remote Address: [::1]:8080
    
    [{
    "id": 30,
    "name": "Dana - IPA Is Dead",
    "description": "Hailing from Slovenia, Dana was originally cross bred from the German Hallertau Magnum and native            Slovenian varieties. Like any good faux noble hop should, it infuses a rustic, musty spiciness into a toasty beast of a      malt base.",
    "abv": 6.7,
    "tagline": "Single Hop India Pale Ale.",
    "first_brewed": "02/2013",
    "image_url": "https://images.punkapi.com/v2/30.png"
    }...


## Get list of beers by description

### Request

`GET /v2/beers?description=Hailing from Slovenia`

    curl -i -H 'Accept: application/json' http://localhost:8080/v2/beers?description=Hailing from Slovenia

### Response

    Request URL: http://localhost:8080/v2/beers?description=Hailing from Slovenia
    Request Method: GET
    Status Code: 200
    Remote Address: [::1]:8080
    
    [{
    "id": 30,
    "name": "Dana - IPA Is Dead",
    "description": "Hailing from Slovenia, Dana was originally cross bred from the German Hallertau Magnum and native            Slovenian varieties. Like any good faux noble hop should, it infuses a rustic, musty spiciness into a toasty beast of a      malt base.",
    "abv": 6.7,
    "tagline": "Single Hop India Pale Ale.",
    "first_brewed": "02/2013",
    "image_url": "https://images.punkapi.com/v2/30.png"
    }...
