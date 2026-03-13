# Tecnologías del Proyecto

Este archivo documenta el stack tecnológico utilizado en el proyecto, junto con el rol que desempeña cada tecnología.

---

| Tecnología | Versión | Rol en el proyecto |
|---|---|---|
| **Java** | 17 (LTS) | Lenguaje de programación principal del proyecto. |
| **Spring Boot** | Latest stable | Framework base que simplifica la configuración y arranque de la aplicación. |
| **Spring Web** | (incluido en Spring Boot) | Habilita la creación de endpoints REST mediante Spring MVC y servidor Tomcat embebido. |
| **Spring Data JPA** | (incluido en Spring Boot) | Capa de acceso a datos. Abstrae las operaciones CRUD con la base de datos mediante repositorios. |
| **H2 Database** | (incluido en Spring Boot) | Base de datos relacional en memoria. Usada para desarrollo y pruebas sin necesidad de instalar un motor externo. |
| **JUnit 5** | (incluido en Spring Boot) | Framework estándar para la escritura y ejecución de pruebas unitarias en Java. |
| **Mockito** | (incluido en Spring Boot) | Librería para simular dependencias en pruebas unitarias, complemento natural de JUnit 5. |
| **Spring Boot Validation** | (incluido en Spring Boot) | Permite validar los datos de entrada a la API (campos requeridos, formatos, rangos, etc.). |

---

## 🧪 Metodología de Desarrollo

| Metodología | Descripción |
|---|---|
| **TDD (Test-Driven Development)** | Las pruebas unitarias se escriben **antes** de implementar el código. Ciclo: 🔴 prueba falla → 🟢 código mínimo para que pase → 🔵 refactor. Garantiza que cada regla de negocio esté cubierta por una prueba desde el inicio. |

---

> 📝 Última actualización: 2026-03-11
