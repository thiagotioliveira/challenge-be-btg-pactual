# challenge-be-btg-pactual

- This project is a proposed solution for the challenge [https://github.com/buildrun-tech/buildrun-desafio-backend-btg-pactual/tree/main](https://github.com/buildrun-tech/buildrun-desafio-backend-btg-pactual/tree/main).
- The application is an example of a Java application using Spring Boot 3.3.1, Maven 3.6.3, and Docker. 
- The application is divided into several modules for better organization and separation of concerns.

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

For local environments, there is a `docker-compose.yml` file in the `local` folder. You can bring up the necessary containers with the following command:

```plaintext
docker network create app-network
docker-compose up
```

To create image (Optional):

```plaintext
docker build -t {dockerhub-user}/{image-name}:{image-tag} .
```

To run as container (Optional):

```plaintext
docker run -p 8080:8080 --network app-network -e SPRING_DATA_MONGODB_HOST=mongodb -e SPRING_RABBITMQ_HOST=rabbitmq {dockerhub-user}/{image-name}:{image-tag}
```

## Endpoints
- The application is configured to use the default port `8080`.

The application has the following endpoint:

- Returns the orders of a specified customer:
```plaintext
GET /v1/customers/{customerId}/orders
```

Example:
```plaintext
curl --location --request GET 'http://localhost:8080/v1/customers/1/orders?page=0&size=10'
```

## Messaging
To publish a message to the `order-created-queue` queue in RabbitMQ, access the RabbitMQ web interface at http://localhost:15672/#/queues/%2F/order-created-queue.

- User and pass is `guest`

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

The project uses [spotless](https://github.com/diffplug/spotless/tree/main/plugin-maven) formatting plugin. So it may be necessary to apply formatting after any file changes.  

```plaintext
mvn spotless:apply
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