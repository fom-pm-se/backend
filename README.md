# Setup

## Benötigte Software

 - Java 17
 - Maven

### Start der Anwendung

`mvn clean package`

`mvn spring-boot:run`

### Start der Anwendung im Integrationsmodus

Der Integrationsmodus führt die Anwendung in einer produktionsnahen Umgebugn aus.
Die Anwendung erwartet eine Postgres-Datenbank, die auf localhost:5432 zur Verfügung gestellt wird.

User: `postgres`
Password: `postgres`
Datenbank: `postgres`

Die Datenbank kann z.B. mit Docker über folgenden Befehl zur Verfügung gestellt werden:
`docker run -d --p 5432:5432 --name postgres -e POSTGRES_PASSWORD=postgres postgres:latest`

Anschließend kann die Anwendung mit dem Spring-Profil `integration` gestartet werden:
`mvn spring-boot:run -Dspring-boot.run.profiles=integration`


### Starten der Anwendung im Produktionsmodus

tbd