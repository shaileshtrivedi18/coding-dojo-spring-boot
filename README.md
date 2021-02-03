Weather App
---
### Introduction

This is a simple application that requests the weather data from [OpenWeather](https://openweathermap.org/) and stores the result in a database.

### Build the application
####Software Requirements:

To build the application, following programs needs to be installed on the machine.
- Java / JDK 11 : https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
- Apache Maven : https://maven.apache.org/download.cgi
- Docker : https://www.docker.com/products) : this will be required only if database needs to be run locally

Checkout the project from git repo and build it from the project root directory using the command:

`mvn clean install`

### Run the application

Application needs connection to Postgres database. If the Postgres database is already setup, then the database connection properties in `application.yml` under `src/main/resources` can be modified to connect to that database.

It is also possible to start a separate Postgres database as docker container. To do so, from the root directory, go to the docker folder and start the postgres container using following command:

`docker-compose up -d`

Once the database is setup, execute the following command to run the application:

`java -jar target/weather-app-spring-boot-0.0.1-SNAPSHOT.jar`


### Use the application

Open the following URL in any browser or execute the curl command to call weather API

`curl http://localhost:8081/api/weather?city={City}`

Provide the valid City name in place of {City}

Sample API response for Amsterdam city would look like:

`{ "id":2759794,
   "city":"Amsterdam",
   "country":"NL",
   "temperature":281.16
  }`


### Changes / Enhancements done

- Refactored the code to move classes to relevant packages
- Moved external API configurations to application.yml
- Created WebClient from Spring Reactive framework for making external API calls
- Created WeatherService that uses webClient to fetch the weather details and uses Repository to save the weather entity into database
- Implemented exception handling
- Added Swagger config for API documentation
- Added jUnit test cases
- Dependencies added - Spring Webflux, JUnit, Lombok, H2 etc.