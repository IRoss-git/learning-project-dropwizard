# My learning project

---

# Table of Contents
###  [General info](#general-info)
###  [Technologies](#technologies)
###  [Setup](#setup)
###  [Usage](#usage)

---

## General info

#### Simple web application which created for learning purpose.
#### It allows managing person, department and persons in department.

---

## Technologies
- [Dropwizard](https://www.dropwizard.io/en/latest/manual/core.html) 2.0.23
- Spring support(context, beans, jdbc)
- [Postgres](#postgres)
- [Liquibase](#liquibase)
- [Kafka](#kafka)
- [Vault](#vault)
- [openapi specification](#Openapi-specification)
- [swagger-ui](https://github.com/swagger-api/swagger-ui)

---

### Postgres

#### Relational database management system emphasizing extensibility and SQL compliance.

---

### Liquibase
#### Liquibase  is an open-source database-independent library for tracking, managing and applying database schema changes.
#### You can find changelog files [here](https://github.com/Ilya-Ross/learning-project-dropwizard/tree/master/src/main/resources/changelog/0.0.1)

---

### Kafka
#### Apache Kafka is an open-source distributed event streaming platform used by thousands of companies for high-performance data pipelines, streaming analytics, data integration, and mission-critical applications.

---

### Vault
#### Manage Secrets and Protect Sensitive Data.
#### Secure, store and tightly control access to tokens, passwords, certificates, encryption keys for protecting secrets and other sensitive data using a UI, CLI, or HTTP API.

---

### Openapi Specification
#### The OpenAPI Specification (OAS) defines a standard, language-agnostic interface to RESTful APIs which allows both humans and computers to discover and understand the capabilities of the service without access to source code, documentation, or through network traffic inspection.

#### You can find OpenApi specification for the project [here](https://github.com/Ilya-Ross/learning-project-dropwizard/blob/master/src/main/resources/apidocs/openapi.yaml)

---

## Setup

### How to run local

#### Run with docker

- use `mvn clean install -Pimage.creation` to create image of application
- use `docker-compose -f docker/docker-compose.yml up`

#### Run with maven and Intellij IDEA

- use `mvn clean install -Pliquibase.migration` in the root directory to run database migrations
- set up your credentials of database in `application.properties`
- run zookeeper.server `.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties`
- run kafka.server `.\bin\windows\kafka-server-start.bat .\config\server.properties`
- create topic `sh kafka-topics.sh --create --bootstrap-server localhost:9092 --topic person2`
- run application with IDEA

---

## Usage

### After successful run, you can open
### `localhost:8080/example/apidocs`
