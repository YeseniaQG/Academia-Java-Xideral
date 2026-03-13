# Arquitectura del Proyecto

Este archivo documenta la arquitectura de la aplicación, las decisiones de diseño y las reglas que rigen la construcción del código.

---

## 🏛️ Patrón Arquitectónico

**Arquitectura en Capas (Layered Architecture)** sobre protocolo **HTTP REST**.

```
Cliente HTTP
     ↓
  Controller          ← Recibe peticiones HTTP y delega al Service
     ↓
  Service (interfaz)  ← Define el contrato de la lógica de negocio
     ↓
  ServiceImpl         ← Implementa la lógica de negocio y las reglas
     ↓
  Repository          ← Acceso a datos mediante JPA
     ↓
  Model / Entity      ← Representación de las tablas en base de datos
```

De forma transversal:
```
  ControllerAdvice    ← Manejo global de excepciones
  Excepciones         ← Excepciones personalizadas por dominio
```

---

## 📁 Estructura de Paquetes

```
com.biblioteca
├── controller
│   ├── UsuarioController.java
│   ├── LibroController.java
│   └── PrestamoController.java
├── service
│   ├── UsuarioService.java        (interfaz)
│   ├── LibroService.java          (interfaz)
│   └── PrestamoService.java       (interfaz)
├── service/impl
│   ├── UsuarioServiceImpl.java
│   ├── LibroServiceImpl.java
│   └── PrestamoServiceImpl.java
├── repository
│   ├── UsuarioRepository.java
│   ├── LibroRepository.java
│   └── PrestamoRepository.java
├── model
│   ├── Usuario.java
│   ├── Libro.java
│   ├── Prestamo.java
│   └── enums
│       ├── Rol.java
│       ├── Genero.java
│       └── EstadoPrestamo.java
├── exception
│   ├── GlobalExceptionHandler.java
│   ├── UsuarioNotFoundException.java
│   ├── UsuarioInactivoException.java
│   ├── EmailDuplicadoException.java
│   ├── LibroNotFoundException.java
│   ├── LibroNoDisponibleException.java
│   ├── IsbnDuplicadoException.java
│   ├── PrestamoNotFoundException.java
│   ├── LimitePrestamosException.java
│   ├── PrestamoRetrasadoException.java
│   ├── LibroYaPrestadoException.java
│   ├── RolNoPermitidoException.java
│   └── EmpleadoNoValidoException.java
└── BibliotecaApplication.java
```

---

## 🔁 Responsabilidad de cada Capa

| Capa | Clase | Responsabilidad |
|---|---|---|
| **Controller** | `*Controller` | Recibir peticiones HTTP, validar entrada con `@Valid`, delegar al Service y retornar respuesta HTTP |
| **Service** | `*Service` | Definir el contrato (interfaz) de la lógica de negocio |
| **ServiceImpl** | `*ServiceImpl` | Implementar la lógica de negocio, aplicar reglas de validación y coordinar con el Repository |
| **Repository** | `*Repository` | Extender `JpaRepository` para acceso a datos. Métodos de consulta personalizados con `@Query` o convención de nombres |
| **Model** | `*Entity` | Representar las entidades de la base de datos con anotaciones JPA (`@Entity`, `@Table`, `@Column`, etc.) |
| **ControllerAdvice** | `GlobalExceptionHandler` | Capturar excepciones lanzadas por cualquier capa y retornar respuestas HTTP estandarizadas |

---

## 🗂️ Modelo de Datos (Entidades)

> Ver detalle completo de campos en `BUSINESS.md`.

### Relaciones

```
Usuario  ──< Prestamo (como solicitante)      [OneToMany / ManyToOne]
Usuario  ──< Prestamo (como empleado)         [OneToMany / ManyToOne]
Usuario  ──< Libro    (como registradoPor)    [OneToMany / ManyToOne]
Libro    ──< Prestamo                         [OneToMany / ManyToOne]
```

### Enums

| Enum | Valores |
|---|---|
| `Rol` | `ROLE_USER`, `ROLE_EMPLOYEE`, `ROLE_ADMIN` |
| `Genero` | `FICCION`, `NO_FICCION`, `CIENCIA`, `HISTORIA`, `TECNOLOGIA`, `ARTE`, `INFANTIL`, `OTRO` |
| `EstadoPrestamo` | `ACTIVO`, `DEVUELTO`, `RETRASADO` |

---

## 🧪 Metodología — TDD (Test-Driven Development)

Este proyecto sigue la metodología **TDD**. Las pruebas se escriben **antes** de implementar el código.

**Ciclo TDD:**
```
🔴 RED      → Escribir la prueba. Falla porque el código no existe aún.
🟢 GREEN    → Escribir el mínimo código necesario para que la prueba pase.
🔵 REFACTOR → Limpiar y mejorar el código. Las pruebas deben seguir pasando.
```

**¿Por qué TDD?**
- Fuerza a pensar en el comportamiento esperado antes de codificar.
- Cada regla de negocio queda cubierta por una prueba desde el inicio.
- Da confianza al refactorizar — si las pruebas pasan, nada está roto.
- Las pruebas son documentación viva del código.

> Ver `TESTING.md` para el detalle completo de los casos de prueba.

---

## 📡 API — Endpoints

### 👤 `/usuarios`

| Método | Endpoint | Descripción | Rol requerido |
|---|---|---|---|
| `GET` | `/usuarios` | Listar todos los usuarios | `ROLE_ADMIN` |
| `GET` | `/usuarios/{id}` | Buscar usuario por id | `ROLE_ADMIN` |
| `POST` | `/usuarios` | Registrar nuevo usuario | `ROLE_ADMIN` |
| `PUT` | `/usuarios/{id}` | Actualizar usuario | `ROLE_ADMIN` |
| `DELETE` | `/usuarios/{id}` | Desactivar usuario (baja lógica) | `ROLE_ADMIN` |

### 📗 `/libros`

| Método | Endpoint | Descripción | Rol requerido |
|---|---|---|---|
| `GET` | `/libros` | Listar todos los libros | Todos |
| `GET` | `/libros/{id}` | Buscar libro por id | Todos |
| `POST` | `/libros` | Registrar nuevo libro | `ROLE_ADMIN`, `ROLE_EMPLOYEE` |
| `PUT` | `/libros/{id}` | Actualizar libro | `ROLE_ADMIN` |
| `DELETE` | `/libros/{id}` | Desactivar libro | `ROLE_ADMIN` |

### 🔄 `/prestamos`

| Método | Endpoint | Descripción | Rol requerido |
|---|---|---|---|
| `GET` | `/prestamos` | Listar todos los préstamos | `ROLE_ADMIN`, `ROLE_EMPLOYEE` |
| `GET` | `/prestamos/{id}` | Buscar préstamo por id | `ROLE_ADMIN`, `ROLE_EMPLOYEE` |
| `GET` | `/prestamos/usuario/{id}` | Listar préstamos por usuario | `ROLE_ADMIN`, `ROLE_EMPLOYEE` |
| `POST` | `/prestamos` | Registrar nuevo préstamo | `ROLE_ADMIN`, `ROLE_EMPLOYEE` |
| `PUT` | `/prestamos/{id}/devolucion` | Registrar devolución de libro | `ROLE_ADMIN`, `ROLE_EMPLOYEE` |

> 📌 **Nota:** Los roles se verifican manualmente en el Service mediante el `id` del usuario que realiza la acción. JWT/Spring Security se implementará en una versión futura sin necesidad de cambiar la lógica actual.

---

## ⚠️ Manejo de Excepciones

Todas las excepciones son capturadas por `GlobalExceptionHandler` anotado con `@ControllerAdvice`.
Cada excepción retorna una respuesta HTTP estandarizada con código de estado y mensaje descriptivo.

| Excepción | HTTP Status | Cuándo se lanza |
|---|---|---|
| `UsuarioNotFoundException` | `404 Not Found` | Usuario no encontrado por id |
| `UsuarioInactivoException` | `400 Bad Request` | Usuario con `activo = false` intenta operar |
| `EmailDuplicadoException` | `409 Conflict` | Email ya registrado en el sistema |
| `LibroNotFoundException` | `404 Not Found` | Libro no encontrado por id |
| `LibroNoDisponibleException` | `400 Bad Request` | Libro con `disponible = false` o `cantidad = 0` |
| `IsbnDuplicadoException` | `409 Conflict` | ISBN ya registrado en el sistema |
| `PrestamoNotFoundException` | `404 Not Found` | Préstamo no encontrado por id |
| `LimitePrestamosException` | `400 Bad Request` | Usuario ya tiene 3 préstamos activos |
| `PrestamoRetrasadoException` | `400 Bad Request` | Usuario tiene préstamos con estado `RETRASADO` |
| `LibroYaPrestadoException` | `400 Bad Request` | Usuario ya tiene ese libro en préstamo activo |
| `RolNoPermitidoException` | `403 Forbidden` | Usuario no tiene el rol requerido para realizar la acción |
| `EmpleadoNoValidoException` | `400 Bad Request` | Empleado inactivo o es el mismo que el solicitante |

---

## 🧱 Principios SOLID Aplicados

| Principio | Cómo se aplica |
|---|---|
| **S** — Single Responsibility | Cada clase tiene una única responsabilidad: Controller solo maneja HTTP, ServiceImpl solo aplica lógica de negocio, Repository solo accede a datos |
| **O** — Open/Closed | Las interfaces `*Service` permiten agregar nuevas implementaciones sin modificar el código existente |
| **L** — Liskov Substitution | `*ServiceImpl` implementa `*Service` y puede sustituirla sin alterar el comportamiento esperado |
| **I** — Interface Segregation | Cada entidad tiene su propia interfaz de servicio (`UsuarioService`, `LibroService`, `PrestamoService`) en lugar de una interfaz gigante |
| **D** — Dependency Inversion | Los Controllers y ServiceImpl dependen de interfaces (`*Service`, `*Repository`), no de implementaciones concretas |

---

## 📝 Convenciones de Nomenclatura

| Elemento | Convención | Ejemplo |
|---|---|---|
| Clases | `PascalCase` | `UsuarioServiceImpl` |
| Métodos | `camelCase` | `registrarPrestamo()` |
| Variables | `camelCase` | `fechaEntregaEstimada` |
| Constantes / Enums | `UPPER_SNAKE_CASE` | `ROLE_USER`, `ACTIVO` |
| Paquetes | `lowercase` | `com.biblioteca.service` |
| Endpoints REST | `kebab-case` en plural | `/usuarios`, `/libros`, `/prestamos` |
| Repositorios | `*Repository` | `UsuarioRepository` |
| Servicios (interfaz) | `*Service` | `UsuarioService` |
| Implementaciones | `*ServiceImpl` | `UsuarioServiceImpl` |
| Excepciones | `*Exception` | `UsuarioNotFoundException` |

---

## 🗄️ Base de Datos — H2

Base de datos **en memoria** para desarrollo y pruebas. Los datos **no persisten** al detener la aplicación. Cuando el proyecto se conecte a MySQL en el futuro, solo se cambiará la configuración sin tocar el código.

| Propiedad | Valor | Descripción |
|---|---|---|
| **Nombre** | `bibliotecaV1` | Nombre de la base de datos en memoria |
| **URL** | `jdbc:h2:mem:bibliotecaV1` | Conexión en memoria, sin archivo físico |
| **Consola H2** | Habilitada | Accesible desde el navegador vía `localhost` |
| **Ruta consola** | `http://localhost:8080/h2-console` | URL para acceder a la consola visual |
| **Persistencia** | ❌ No persiste | Los datos se borran al detener la aplicación |
| **Creación de tablas** | Automática | JPA genera las tablas al arrancar (`ddl-auto=create-drop`) |

> 📌 **Migración futura:** Al conectar con MySQL, se cambia `application.properties` agregando el driver, URL, usuario y contraseña de MySQL. El código de la aplicación no requiere cambios.

> 📋 **Guía de ejecución:** Se documentará en `README.md` cuando el proyecto esté listo, incluyendo cómo arrancar la app y cómo acceder a la consola H2.

---

> 📝 Última actualización: 2026-03-11
