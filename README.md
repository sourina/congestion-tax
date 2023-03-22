# Congestion Tax Calculator

This application is for calculating congestion tax fees for vehicles within the Gothenburg area

## Requirements

For building and running the application you need:

- [OpenJDK 17](https://openjdk.org/projects/jdk/17/)
- [Maven 3](https://maven.apache.org)

## Running the application locally

```shell
mvn spring-boot:run
```

## Test the application locally using curl 

```shell
curl --location 'http://localhost:9080/congestion-tax' \
--header 'Content-Type: application/json' \
--data '{
    "vehicleType":"car",
    "dates":[
        "2013-01-14 21:00:00",
        "2013-01-15 21:00:00",
        "2013-02-08 14:35:00",
        "2013-02-08 16:01:00"
    ],
    "cityCode":"GOB"
}'
```

## Sample response

```shell
{
    "congestionTaxFee": 26
}

```

## Used technologies and standards
* Followed Richardsen Maturity model when defining RESTFULL API naming convention
* Controller advice used to handle exceptions
* Lombok is used to reduce the code boiler plate
* H2 database used to store data in-memory
* Database can be access through h2 console ex: http://localhost:9080/h2-console/
* Junit and Mockito used to write test cases
* data.sql file will be executed automatically with the project build to load sample data to the database
* You can update application.property file values for configure the application according to your environment
* Used Spring Boot Actuator to expose operational information about the running application
* Used Spring Boot Devtools to provide out of box remote debugging capabilities via HTTP

