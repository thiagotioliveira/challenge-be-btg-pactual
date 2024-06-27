# challenge-be-btg-pactual

- This project is a proposed solution for the challenge [https://github.com/buildrun-tech/buildrun-desafio-backend-btg-pactual/tree/main](https://github.com/buildrun-tech/buildrun-desafio-backend-btg-pactual/tree/main). The application is an example of a Java application using Spring Boot 3.3.1, Maven 3.6.3, and Docker. The application is divided into several modules for better organization and separation of concerns.

- This project is an example of a Java application using Spring Boot 3.3.1, Maven 3.6.3, and Docker. The application is divided into several modules for better organization and separation of concerns.

## Modules

- **core**: Contains the business logic.
- **data-mongo**: Responsible for data persistence using MongoDB.
- **messaging-rabbitmq**: RabbitMQ queue listener.
- **spec**: OpenAPI specifications.
- **impl**: Spring Boot application implementation.

## Project Structure

```plaintext
project
│   README.md
│   pom.xml
│
├───core
│   └───src
├───data-mongo
│   └───src
├───messaging-rabbitmq
│   └───src
├───spec
│   └───src
├───impl
│   └───src
└───local
    └───docker-compose.yml
```

## Docker

For testing environments, there is a docker-compose.yml file in the `local folder`. You can bring up the necessary containers with the following command:

```plaintext
docker-compose up
```

## Endpoints
The application has the following endpoint:

```plaintext
GET /v1/customers/{customerId}/orders
```
Returns the orders of a specified customer.

## Messaging
To publish a message to the order-created-queue queue in RabbitMQ, access the RabbitMQ web interface at http://localhost:15672/#/queues/%2F/order-created-queue.

Example Message:

```json
 {
  "codigoPedido": 1001,
  "codigoCliente":1,
  "itens": [
    {
      "produto": "lápis",
      "quantidade": 100,
      "preco": 1.10
    },
    {
      "produto": "caderno",
      "quantidade": 10,
      "preco": 1.00
    }
  ]
}
```

## Build and Run
To compile and build the project, use the following command:

```plaintext
mvn clean install
```
To start the Spring Boot application, use the command into `impl` folder:

```plaintext
mvn spring-boot:run
```

## Dependencies
* Java 21
* Spring Boot 3.3.1
* Maven 3.6.3
* Docker

## Contribution
If you wish to contribute to this project, please fork the repository, create a branch for your changes, and submit a pull request. All contributions are welcome!

## License
This project is licensed under the MIT License.