# BIMS (Bookstore Inventory Management System)

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Building an image

```shell script
./mvnw quarkus:image-build
```

## Docker compose
Optionally adjust environment variables and postgres settings, and then run:

```shell script
docker compose up
```

## Data
In dev mode, the test business data is created, while production database spins up with ONLY users & roles data.

## Links
[Swagger UI](http://localhost:8080/q/swagger-ui/)

[Healthcheck](http://localhost:8080/q/health)

[Liveness](http://localhost:8080/q/health/live)

[Readiness](http://localhost:8080/q/health/ready)

