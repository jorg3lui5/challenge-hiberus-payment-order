
# Challenge – Migración y Desarrollo de Órdenes de Pago

## Repositorio
[https://github.com/jorg3lui5/challenge-hiberus-payment-order](https://github.com/jorg3lui5/challenge-hiberus-payment-order)

---

## Contexto del Proyecto
Este proyecto tiene como objetivo la **migración y desarrollo de un servicio de Órdenes de Pago**, utilizando **Spring Boot 3.5.7**, arquitectura hexagonal y programación reactiva con **WebFlux**.

### Decisiones clave en la migración
- Se revisó el archivo WSDL y se validó su correspondencia con los ejemplos de request y response.
- Se identificaron dos servicios en el WSDL, aunque el archivo `postman_collection.json` tiene tres requests.
- Se alineó el proyecto con el **Service Domain PaymentOrder** de BIAN, siguiendo nomenclatura de recursos, BQs y estados.
- Se utilizó un enfoque **Contract-first** y `openapi-generator` para generar interfaces y DTOs.
- Estructura del proyecto basada en **arquitectura hexagonal**.
- Manejo de errores personalizado y mapeo de entidades con **MapStruct**.
- Uso de **SonarQube** para control de calidad, cobertura y análisis de código.

---

## Tecnologías y Herramientas
- **Java 17**
- **Spring Boot 3.5.7**
- **WebFlux**
- **Lombok**
- **MapStruct**
- **JUnit + Mockito**
- **R2DBC + PostgreSQL**
- **Docker y Docker-compose**
- **SonarQube**
- **openapi-generator**

---

## Setup Inicial

### Creación del proyecto
- Proyecto generado con [start.spring.io](https://start.spring.io/) usando Gradle y Java 17.
- Dependencias seleccionadas: WebFlux, Lombok, MapStruct, JUnit + Mockito, R2DBC, PostgreSQL.

### Estructura
- Arquitectura **hexagonal**
- Mappers con MapStruct
- Manejo de errores personalizado
- Programación reactiva

---

## Despliegue del Servicio

### Local
**Requisitos:**
- Java 17
- Gradle ≥ 4.4.1

**Configuración:**
Editar `application-local.yml` con la conexión a la base de datos:

```yaml
r2dbc:
  url: r2dbc:postgresql://tsdbadmin@hf4k1dp668.ps1ubj9bd0.tsdb.cloud.timescale.com:33285/tsdb?sslmode=require
  username: tsdbadmin
  password: f3bwc3ypw8c7azv9
```

**Comandos:**
```bash
gradle clean build
gradle spring-boot:run
```

### Con Docker-compose
**Requisitos:**
- Docker y Docker-compose

**Configuración:**  
Editar `application-local.yml` para apuntar a la BD dockerizada:

```yaml
r2dbc:
  url: r2dbc:postgresql://db:5432/database
  username: username
  password: password
```

**Comando:**
```bash
docker compose up --build -d
```

---

## Uso de IA en el Proyecto
- Generación del contrato a partir del WSDL y el SD PaymentOrder.
- Corrección de esquemas basados en request/response de Postman.
- Definición de estados y flujos de pago según BIAN.
- Optimización de código y actualización de objetos en pruebas unitarias.
- Mapeo de datos para transformar entidades a DTOs y viceversa.
- Implementación y ayuda con test unitarios
- Scripts generados para la creación de tablas en PostgreSQL, basados en las entidades del proyecto.
- Resolución de Smell code y análisis con Snyk
- Conexiones a la BD desplegada con Docker

---

## Pruebas Unitarias e Integración
- Pruebas unitarias en servicios, validaciones y flujos.
- Cobertura de código: **98.2%** según SonarQube.
- Sin issues de seguridad, fiabilidad o mantenimiento detectados.
- Uso de SonarQube Community Edition para control de calidad.

---

## Docker
- Archivos `Dockerfile` y `docker-compose.yml` disponibles en la raíz del proyecto.
- Permite levantar la aplicación y la base de datos de manera rápida y reproducible.

---

## Calidad
- Análisis de **smell code** y seguridad con **Snyk**.
- Cobertura y calidad de código verificada con **SonarQube**.

---

## Open API
- Archivo `openapi.yml` disponible para referencia y consumo de la API.
