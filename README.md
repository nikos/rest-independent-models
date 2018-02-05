# "Share Nothing" [![Build Status](https://travis-ci.org/nikos/rest-independent-models.svg?branch=master)](https://travis-ci.org/nikos/rest-independent-models)

This project demonstrates how you can build two parties (micro-services)
sharing a common data model without sharing code, and therefore only
build a loose coupling and avoiding a code dependency between them.

Each side defines an own model (here of a customer) which is transferred
using JSON. To illustrate point the customer model has on the client
side an additional attribute (birth date), which does not exist in the
data model on the server side.

Being independent in regards to common code is considered a great
benefit when it comes to deploying each module resp. building
distinct continuous deploy pipelines, without requiring
the other project to be rolled out at the same time.

For further discussion, read for example an interview with Sam Newman
about [Sharing Code](https://samnewman.io/blog/2015/06/22/answering-questions-from-devoxx-on-microservices/)


## Getting Started

These instructions will get you a copy of the project up and running
on your local machine for development and testing purposes.

### Prerequisites

To build and run the sample projects you require only a JDK of version 8.


## Running the tests

To run the automated tests for the client and server demo projects
please follow the instructions below.

### Break down into end to end tests

For building and testing you can use the following maven commands:

```
cd client
./mvnw clean package
```

Same goes for the server side, from the root project directory execute:

```
cd server
./mvnw clean package
```



### Run end-to-end integration test

The integration tests require a running instance of the server.
To start-up the Spring Boot based web application execute:

```
cd server
./mvnw spring-boot:run
```

By default this will start an Spring Boot web application on port 8081.
In a separate terminal start the client side integration tests with:

```
cd client
./mvnw test -Dtest-group=cdc-tests
```


## Built With

* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Lombok](https://projectlombok.org/) - Avoid boiler-plate code


## Authors

* **Niko Schmuck** - *Initial work* - [nikos](https://github.com/nikos)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
