# websocket-java-quarkus-app

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.


# Arquitetura

│
├── application        ← regras de uso (casos de uso)
│   ├── service
│   └── dto
│
├── domain             ← regras de negócio PURAS
│   ├── model
│   └── repository
│
├── infrastructure     ← coisas técnicas (DB, Redis, etc)
│   ├── persistence
│   ├── redis
│   └── config
│
├── interfaces         ← entrada/saída (WebSocket, REST)
│   ├── websocket
│   └── rest (opcional)
│
├── view               ← interface gráfica para testes (Html, Js)
│
└── shared             ← utilidades comuns


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

## Rodando a aplicação em modo dev

No terminal, execute o comando:

```shell script
./mvnw quarkus:dev
```

Abra o chat1.html em um navegador e chat2.html em outro navegador.


## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/websocket-java-quarkus-app-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.
