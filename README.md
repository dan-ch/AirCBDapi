# LuftBnBApi

## Description

LuftBnBApi is a backend application(API)/a part of hotel reservation system, just like the well-known ones such as Booking.com or AirBnb, that contains core functionalities of this type of software(more in section below). The API takes care of the consistency and validation of the data coming in and sent to the frontend application that is built with React.js. Frontend app you can find on my GitHub at this link: <https://github.com/dan-ch/LuftBnBUi>

While working on this project, I learned primarily:

- how to secure and connect the Spring API with the front-end application using JWT
- how to create my own annotations in Spring and attach validation classes to them
- how to use AOP and `@ControllerAdvice` annotation to catch errors and send appropriate responses

## Features

- CRUD operation on offers
- Adding and deleting offer comments
- Calculating the rating for the offer based on the stored opinions
- Searching for offers based on the location, date range, number of people
- Booking offers and cancellation of booking
- DateService used to find offers that match a request and to check whether it is possible to book an offer on a specific date range
- User registration
- Authentication using JWT and custom filter
- Custom password validation
- Integration with Cloudinary platform to store offer images

## Built with

- Spring Boot 2.6.2:
    - Web
    - Data JPA
    - Validation
    - Security
- PostgreSQL Driver 42.3.1
- [Passy](https://www.passay.org/) 1.6.1
- [Java JWT](https://github.com/auth0/java-jwt) 3.18.2
- [Cloudinary-http44](https://cloudinary.com/documentation/cloudinary_get_started) 1.29.0

## Getting started

The API is deployed on Heroku at this link: <https://luftbnb-api.herokuapp.com>  
You can try the LuftBnB hotel reservatiuon system from the frontend application at this link: <https://dan-ch.github.io/LuftBnBUi/>

Or if you want run application locally follow instalation steps.

### Prerequisites

- Java 11
- PostgreSQL 14
- account on [Cloudinary](https://cloudinary.com/) platform

### Installation

1. Clone repository

    ```txt
    git clone https://github.com/dan-ch/LuftBnBApi
    ```

2. Provide valid database credentials in [application.properties](/src/main/resources/application.properties) file

    ```properties
    spring.datasource.url = your_database_url 
    #for ex. spring.datasource.url = jdbc:postgresql://localhost:5432/LuftBnB
    spring.datasource.username = your_database_username
    spring.datasource.password = your_database_password
    ```

3. Provide valid Cloudinary platform credentials in [application.properties](/src/main/resources/application.properties) file

    ```properties
    cloudinary.cloud_name = your_cloud_name 
    cloudinary.api_key = your_api_key
    cloudinary.api_secret = your_api_secret
    ```

4. If you want the database schema to be created automatically in [application.properties](/src/main/resources/application.properties) file, change the value of `spring.jpa.hibernate.ddl-auto` to `create` (only for the first run of application - then change it back to `validate`)

    ```properties
    spring.jpa.hibernate.ddl-auto=create
    ```

## Usage

To run the application simply paste and run the following command in your CLI

On Windows:

```txt
./mvnw.cmd spring-boot:run
```

On Linux:

```txt
./mvnw spring-boot:run
```

## Sources

When implementing JWT authentication I have follwed this tutorial: <https://www.youtube.com/watch?v=VVn9OG9nfH0>

Password validation is implemented using Passy library, which I learned about from this article: <https://www.baeldung.com/java-passay>

## License

Distributed under the MIT License. See `LICENSE.txt`
