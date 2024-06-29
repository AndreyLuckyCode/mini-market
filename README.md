# Project Overview

This project is an application for managing store inventory with the potential to scale into a full-fledged online marketplace for customer use.

## Technology Stack

- **Spring Boot**
- **PostgreSQL**
- **OAuth2.0, Keycloak**
- **Swagger**
- **Spring Boot Admin**

## Project Structure

The project consists of three modules, each of which is autonomous and independent in use.

---

### Manager-App Module

**Server Port:** 8080

This module represents the client side of the application. It formulates requests to the REST API module using the `RestClient` class. The controller includes input data validation to ensure that requests sent to the REST API are already valid, reducing the load on our API. Exception handling is configured with internationalization support (using the `messages.properties` file).

This module acts as a client to the REST API module. With OAuth2.0, the module undergoes authentication and validation, enabling communication with the API.

Keycloak is deployed in Docker. Tests are written for the controller of this module.

![image](https://github.com/AndreyLuckyCode/mini-market/assets/132314710/0241ce6c-1c51-4177-8d39-973c15889707)

---

### Product-Rest-Api Module

**Server Port:** 8081

This module represents the REST API that handles incoming requests from the manager-app module and interacts with the database.

An entity mapper is provided to return a DTO entity to the client with a specific set of information.

Swagger is used for documenting and testing this module.

![image](https://github.com/AndreyLuckyCode/mini-market/assets/132314710/493ffe8d-7094-426d-9b79-04983de16a3e)

![image](https://github.com/AndreyLuckyCode/mini-market/assets/132314710/213340e8-c424-4735-8622-466765c8c5ad)

---

### Admin-Server Module

**Server Port:** 8085

This module is the administration module and contains the configuration for Spring Boot Admin.

![image](https://github.com/AndreyLuckyCode/mini-market/assets/132314710/ea206d59-e5ea-413d-a052-57b6cf5b133c)

![image](https://github.com/AndreyLuckyCode/mini-market/assets/132314710/6262f5ef-f378-42f6-8663-fed030c7f341)

---

## Testing with Postman

During the development of this project, Postman was used for testing both the client-side application and the API. Postman collections and environment configurations can be found in the `postman` directory.

![image](https://github.com/AndreyLuckyCode/mini-market/assets/132314710/9fc9c55f-9395-4f80-b847-93ae87fadbf8)



