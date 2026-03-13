# API Guide — Biblioteca

Guía práctica con ejemplos reales de cada endpoint.  
Todos los ejemplos fueron **probados en vivo** contra la aplicación corriendo en `localhost:8080`.

---

## 🚀 Cómo correr la aplicación

### Requisitos
- Java 17+
- No necesitas instalar Maven — el proyecto incluye `mvnw`

### Pasos

```bash
# 1. Entrar a la carpeta del proyecto
cd "Semana 5/ejercicioTres/biblioteca"

# 2. Iniciar la aplicación (Windows)
.\mvnw spring-boot:run

# 3. Iniciar la aplicación (Linux/Mac)
./mvnw spring-boot:run
```

Verás en consola:
```
Started BibliotecaApplication in X.XXX seconds
Tomcat started on port 8080
```

La app estará disponible en: **`http://localhost:8080`**

### Consola H2 (base de datos en memoria)

| Campo | Valor |
|---|---|
| URL | `http://localhost:8080/h2-console` |
| JDBC URL | `jdbc:h2:mem:bibliotecaV1` |
| User Name | `sa` |
| Password | *(vacío)* |

> ⚠️ Los datos no persisten al detener la app. Esto es intencional en fase de pruebas.

### Correr las pruebas unitarias

```bash
.\mvnw test        # Windows
./mvnw test        # Linux/Mac
```

Resultado esperado: `Tests run: 40, Failures: 0, Errors: 0, Skipped: 0 — BUILD SUCCESS`

---

## 📡 Ejemplos por Endpoint

> **Convención de colores en respuestas:**
> - `201 Created` → recurso creado exitosamente
> - `200 OK` → consulta o actualización exitosa
> - `204 No Content` → operación exitosa sin cuerpo de respuesta
> - `400 Bad Request` → datos inválidos o regla de negocio violada
> - `403 Forbidden` → rol no permitido para la operación
> - `404 Not Found` → recurso no encontrado
> - `409 Conflict` → dato duplicado (email, ISBN)

---

## 👤 Usuarios — `/api/usuarios`

---

### `POST /api/usuarios` — Registrar usuario

**Headers:** `Content-Type: application/json`

---

#### ✅ Ejemplo 1 — Usuario con ROLE_USER

**Request:**
```json
{
  "nombre": "Ana Lopez",
  "telefono": "5551001",
  "email": "ana@mail.com",
  "password": "pass123",
  "rol": "ROLE_USER"
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "nombre": "Ana Lopez",
  "telefono": "5551001",
  "email": "ana@mail.com",
  "password": "pass123",
  "fechaRegistro": "2026-03-10",
  "activo": true,
  "rol": "ROLE_USER"
}
```

---

#### ✅ Ejemplo 2 — Usuario con ROLE_EMPLOYEE

**Request:**
```json
{
  "nombre": "Carlos Ruiz",
  "telefono": "5552002",
  "email": "carlos@mail.com",
  "password": "emp456",
  "rol": "ROLE_EMPLOYEE"
}
```

**Response `201 Created`:**
```json
{
  "id": 2,
  "nombre": "Carlos Ruiz",
  "telefono": "5552002",
  "email": "carlos@mail.com",
  "password": "emp456",
  "fechaRegistro": "2026-03-10",
  "activo": true,
  "rol": "ROLE_EMPLOYEE"
}
```

---

#### ✅ Ejemplo 3 — Usuario con ROLE_ADMIN

**Request:**
```json
{
  "nombre": "Admin Root",
  "telefono": "5553003",
  "email": "admin@mail.com",
  "password": "admin789",
  "rol": "ROLE_ADMIN"
}
```

**Response `201 Created`:**
```json
{
  "id": 3,
  "nombre": "Admin Root",
  "telefono": "5553003",
  "email": "admin@mail.com",
  "password": "admin789",
  "fechaRegistro": "2026-03-10",
  "activo": true,
  "rol": "ROLE_ADMIN"
}
```

---

#### ❌ Ejemplo 4 — Email duplicado

**Request:**
```json
{
  "nombre": "Ana Copia",
  "telefono": "5554004",
  "email": "ana@mail.com",
  "password": "pass",
  "rol": "ROLE_USER"
}
```

**Response `409 Conflict`:**
```json
{
  "timestamp": "2026-03-10T19:35:37.805",
  "status": 409,
  "error": "Conflict",
  "message": "El email ana@mail.com ya está registrado."
}
```

---

### `GET /api/usuarios` — Listar todos los usuarios

#### ✅ Ejemplo 5 — Lista de usuarios

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "nombre": "Ana Lopez",
    "telefono": "5551001",
    "email": "ana@mail.com",
    "fechaRegistro": "2026-03-10",
    "activo": true,
    "rol": "ROLE_USER"
  },
  {
    "id": 2,
    "nombre": "Carlos Ruiz",
    "telefono": "5552002",
    "email": "carlos@mail.com",
    "fechaRegistro": "2026-03-10",
    "activo": true,
    "rol": "ROLE_EMPLOYEE"
  }
]
```

---

### `GET /api/usuarios/{id}` — Obtener usuario por ID

#### ✅ Ejemplo 6 — Usuario encontrado

**`GET /api/usuarios/1`**

**Response `200 OK`:**
```json
{
  "id": 1,
  "nombre": "Ana Lopez",
  "telefono": "5551001",
  "email": "ana@mail.com",
  "fechaRegistro": "2026-03-10",
  "activo": true,
  "rol": "ROLE_USER"
}
```

#### ❌ Ejemplo 7 — Usuario no encontrado

**`GET /api/usuarios/99`**

**Response `404 Not Found`:**
```json
{
  "timestamp": "2026-03-10T19:35:37.863",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario con id 99 no encontrado."
}
```

---

### `PUT /api/usuarios/{id}` — Actualizar usuario

#### ✅ Ejemplo 8 — Actualización exitosa

**`PUT /api/usuarios/2`**

**Request:**
```json
{
  "nombre": "Carlos Ruiz Updated",
  "telefono": "5559999",
  "email": "carlos@mail.com",
  "password": "newpass",
  "rol": "ROLE_EMPLOYEE"
}
```

**Response `200 OK`:**
```json
{
  "id": 2,
  "nombre": "Carlos Ruiz Updated",
  "telefono": "5559999",
  "email": "carlos@mail.com",
  "password": "newpass",
  "fechaRegistro": "2026-03-10",
  "activo": true,
  "rol": "ROLE_EMPLOYEE"
}
```

---

### `DELETE /api/usuarios/{id}` — Desactivar usuario (soft delete)

#### ✅ Ejemplo 9 — Desactivación exitosa

**`DELETE /api/usuarios/1`**

**Response `204 No Content`** *(sin cuerpo)*

> El usuario no se elimina físicamente; `activo` pasa a `false`.

#### ❌ Ejemplo 10 — Usuario no existe

**`DELETE /api/usuarios/99`**

**Response `404 Not Found`:**
```json
{
  "timestamp": "2026-03-10T19:35:38.100",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario con id 99 no encontrado."
}
```

---

## 📗 Libros — `/api/libros`

---

### `POST /api/libros?idEmpleado={id}` — Registrar libro

**Headers:** `Content-Type: application/json`  
**Query param:** `idEmpleado` — ID del empleado que registra (debe ser ROLE_EMPLOYEE o ROLE_ADMIN)

---

#### ✅ Ejemplo 1 — Registro exitoso

**`POST /api/libros?idEmpleado=2`**

**Request:**
```json
{
  "titulo": "Clean Code",
  "isbn": "978-0132350884",
  "autor": "Robert C. Martin",
  "genero": "TECNOLOGIA",
  "cantidad": 5
}
```

**Response `201 Created`:**
```json
{
  "id": 1,
  "titulo": "Clean Code",
  "isbn": "978-0132350884",
  "autor": "Robert C. Martin",
  "genero": "TECNOLOGIA",
  "cantidad": 5,
  "disponible": true,
  "fechaRegistro": "2026-03-10",
  "registradoPor": {
    "id": 2,
    "nombre": "Carlos Ruiz",
    "email": "carlos@mail.com",
    "rol": "ROLE_EMPLOYEE"
  }
}
```

---

#### ✅ Ejemplo 2 — Libro de ficción

**`POST /api/libros?idEmpleado=2`**

**Request:**
```json
{
  "titulo": "El Quijote",
  "isbn": "978-8467033472",
  "autor": "Cervantes",
  "genero": "FICCION",
  "cantidad": 3
}
```

**Response `201 Created`:**
```json
{
  "id": 2,
  "titulo": "El Quijote",
  "isbn": "978-8467033472",
  "autor": "Cervantes",
  "genero": "FICCION",
  "cantidad": 3,
  "disponible": true,
  "fechaRegistro": "2026-03-10"
}
```

---

#### ✅ Ejemplo 3 — Libro de historia

**`POST /api/libros?idEmpleado=2`**

**Request:**
```json
{
  "titulo": "Sapiens",
  "isbn": "978-0062316097",
  "autor": "Yuval Noah Harari",
  "genero": "HISTORIA",
  "cantidad": 2
}
```

**Response `201 Created`:**
```json
{
  "id": 3,
  "titulo": "Sapiens",
  "isbn": "978-0062316097",
  "autor": "Yuval Noah Harari",
  "genero": "HISTORIA",
  "cantidad": 2,
  "disponible": true,
  "fechaRegistro": "2026-03-10"
}
```

---

#### ❌ Ejemplo 4 — ISBN duplicado

**`POST /api/libros?idEmpleado=2`**

**Request:**
```json
{
  "titulo": "Clean Code Segunda Edición",
  "isbn": "978-0132350884",
  "autor": "Robert C. Martin",
  "genero": "TECNOLOGIA",
  "cantidad": 1
}
```

**Response `409 Conflict`:**
```json
{
  "timestamp": "2026-03-10T19:35:58.588",
  "status": 409,
  "error": "Conflict",
  "message": "El ISBN 978-0132350884 ya está registrado."
}
```

---

#### ❌ Ejemplo 5 — Empleado con ROLE_USER (sin permiso)

**`POST /api/libros?idEmpleado=4`** *(usuario con ROLE_USER)*

**Response `400 Bad Request`:**
```json
{
  "timestamp": "2026-03-10T19:35:58.606",
  "status": 400,
  "error": "Bad Request",
  "message": "El usuario con id 4 no tiene permisos para registrar libros."
}
```

---

### `GET /api/libros` — Listar todos los libros

#### ✅ Ejemplo 6 — Lista de libros

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "titulo": "Clean Code",
    "isbn": "978-0132350884",
    "autor": "Robert C. Martin",
    "genero": "TECNOLOGIA",
    "cantidad": 5,
    "disponible": true,
    "fechaRegistro": "2026-03-10"
  },
  {
    "id": 2,
    "titulo": "El Quijote",
    "isbn": "978-8467033472",
    "autor": "Cervantes",
    "genero": "FICCION",
    "cantidad": 3,
    "disponible": true,
    "fechaRegistro": "2026-03-10"
  }
]
```

---

### `GET /api/libros/{id}` — Obtener libro por ID

#### ✅ Ejemplo 7 — Libro encontrado

**`GET /api/libros/1`**

**Response `200 OK`:**
```json
{
  "id": 1,
  "titulo": "Clean Code",
  "isbn": "978-0132350884",
  "autor": "Robert C. Martin",
  "genero": "TECNOLOGIA",
  "cantidad": 5,
  "disponible": true,
  "fechaRegistro": "2026-03-10"
}
```

#### ❌ Ejemplo 8 — Libro no encontrado

**`GET /api/libros/99`**

**Response `404 Not Found`:**
```json
{
  "timestamp": "2026-03-10T19:35:58.629",
  "status": 404,
  "error": "Not Found",
  "message": "Libro con id 99 no encontrado."
}
```

---

### `PUT /api/libros/{id}` — Actualizar libro

#### ✅ Ejemplo 9 — Actualización exitosa

**`PUT /api/libros/1`**

**Request:**
```json
{
  "titulo": "Clean Code (Ed. Revisada)",
  "isbn": "978-0132350884",
  "autor": "Robert C. Martin",
  "genero": "TECNOLOGIA",
  "cantidad": 10
}
```

**Response `200 OK`:**
```json
{
  "id": 1,
  "titulo": "Clean Code (Ed. Revisada)",
  "isbn": "978-0132350884",
  "autor": "Robert C. Martin",
  "genero": "TECNOLOGIA",
  "cantidad": 10,
  "disponible": true,
  "fechaRegistro": "2026-03-10"
}
```

---

### `DELETE /api/libros/{id}` — Desactivar libro (soft delete)

#### ✅ Ejemplo 10 — Desactivación exitosa

**`DELETE /api/libros/3`**

**Response `204 No Content`** *(sin cuerpo)*

> El libro no se elimina físicamente; `disponible` pasa a `false`.

---

## 🔄 Préstamos — `/api/prestamos`

---

### `POST /api/prestamos?idUsuario={}&idEmpleado={}&idLibro={}` — Registrar préstamo

> No lleva cuerpo JSON. Todos los datos van como **query params**.  
> La `fechaEntregaEstimada` se calcula **automáticamente** como `fechaPrestamo + 10 días`.

---

#### ✅ Ejemplo 1 — Préstamo exitoso

**`POST /api/prestamos?idUsuario=4&idEmpleado=2&idLibro=1`**

**Response `201 Created`:**
```json
{
  "id": 1,
  "usuario": {
    "id": 4,
    "nombre": "Maria Torres",
    "email": "maria@mail.com",
    "rol": "ROLE_USER"
  },
  "empleado": {
    "id": 2,
    "nombre": "Carlos Ruiz",
    "email": "carlos@mail.com",
    "rol": "ROLE_EMPLOYEE"
  },
  "libro": {
    "id": 1,
    "titulo": "Clean Code",
    "isbn": "978-0132350884",
    "cantidad": 9
  },
  "fechaPrestamo": "2026-03-10",
  "fechaEntregaEstimada": "2026-03-20",
  "fechaEntregaReal": null,
  "estado": "ACTIVO"
}
```

> ✅ Nota: El stock bajó de 10 a 9 automáticamente.

---

#### ✅ Ejemplo 2 — Segundo préstamo del mismo usuario (libro diferente)

**`POST /api/prestamos?idUsuario=4&idEmpleado=2&idLibro=2`**

**Response `201 Created`:**
```json
{
  "id": 2,
  "usuario": { "id": 4, "nombre": "Maria Torres", "rol": "ROLE_USER" },
  "libro": { "id": 2, "titulo": "El Quijote", "cantidad": 2 },
  "fechaPrestamo": "2026-03-10",
  "fechaEntregaEstimada": "2026-03-20",
  "fechaEntregaReal": null,
  "estado": "ACTIVO"
}
```

---

#### ❌ Ejemplo 3 — Libro ya prestado al mismo usuario

**`POST /api/prestamos?idUsuario=4&idEmpleado=2&idLibro=1`** *(el libro 1 ya está activo)*

**Response `400 Bad Request`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.025",
  "status": 400,
  "error": "Bad Request",
  "message": "El usuario ya tiene este libro en préstamo activo."
}
```

---

#### ❌ Ejemplo 4 — Empleado y solicitante son el mismo

**`POST /api/prestamos?idUsuario=4&idEmpleado=4&idLibro=2`**

**Response `400 Bad Request`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.047",
  "status": 400,
  "error": "Bad Request",
  "message": "El empleado autorizante no puede ser el mismo que el solicitante."
}
```

---

#### ❌ Ejemplo 5 — Usuario sin ROLE_USER solicita préstamo

**`POST /api/prestamos?idUsuario=2&idEmpleado=5&idLibro=1`** *(usuario 2 es ROLE_EMPLOYEE)*

**Response `403 Forbidden`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.054",
  "status": 403,
  "error": "Forbidden",
  "message": "Solo usuarios con ROLE_USER pueden solicitar préstamos."
}
```

---

#### ❌ Ejemplo 6 — Usuario no existe

**`POST /api/prestamos?idUsuario=99&idEmpleado=2&idLibro=1`**

**Response `404 Not Found`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.061",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario con id 99 no encontrado."
}
```

---

#### ❌ Ejemplo 7 — Límite de 3 préstamos activos

**`POST /api/prestamos?idUsuario=4&idEmpleado=2&idLibro=3`** *(si el usuario ya tiene 3 activos)*

**Response `400 Bad Request`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.070",
  "status": 400,
  "error": "Bad Request",
  "message": "El usuario ya tiene 3 préstamos activos."
}
```

---

### `GET /api/prestamos` — Listar todos los préstamos

#### ✅ Ejemplo 8 — Lista de préstamos

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "usuario": { "id": 4, "nombre": "Maria Torres", "rol": "ROLE_USER" },
    "empleado": { "id": 2, "nombre": "Carlos Ruiz", "rol": "ROLE_EMPLOYEE" },
    "libro": { "id": 1, "titulo": "Clean Code" },
    "fechaPrestamo": "2026-03-10",
    "fechaEntregaEstimada": "2026-03-20",
    "fechaEntregaReal": null,
    "estado": "ACTIVO"
  },
  {
    "id": 2,
    "usuario": { "id": 4, "nombre": "Maria Torres", "rol": "ROLE_USER" },
    "libro": { "id": 2, "titulo": "El Quijote" },
    "fechaPrestamo": "2026-03-10",
    "fechaEntregaEstimada": "2026-03-20",
    "fechaEntregaReal": null,
    "estado": "ACTIVO"
  }
]
```

---

### `GET /api/prestamos/{id}` — Obtener préstamo por ID

#### ✅ Ejemplo 9 — Préstamo encontrado

**`GET /api/prestamos/1`**

**Response `200 OK`:**
```json
{
  "id": 1,
  "usuario": { "id": 4, "nombre": "Maria Torres", "rol": "ROLE_USER" },
  "empleado": { "id": 2, "nombre": "Carlos Ruiz", "rol": "ROLE_EMPLOYEE" },
  "libro": { "id": 1, "titulo": "Clean Code", "cantidad": 9 },
  "fechaPrestamo": "2026-03-10",
  "fechaEntregaEstimada": "2026-03-20",
  "fechaEntregaReal": null,
  "estado": "ACTIVO"
}
```

#### ❌ Ejemplo 10 — Préstamo no encontrado

**`GET /api/prestamos/99`**

**Response `404 Not Found`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.087",
  "status": 404,
  "error": "Not Found",
  "message": "Préstamo con id 99 no encontrado."
}
```

---

### `GET /api/prestamos/usuario/{idUsuario}` — Préstamos de un usuario

#### ✅ Ejemplo 11 — Préstamos del usuario 4

**`GET /api/prestamos/usuario/4`**

**Response `200 OK`:**
```json
[
  {
    "id": 1,
    "libro": { "titulo": "Clean Code" },
    "fechaEntregaEstimada": "2026-03-20",
    "estado": "ACTIVO"
  },
  {
    "id": 2,
    "libro": { "titulo": "El Quijote" },
    "fechaEntregaEstimada": "2026-03-20",
    "estado": "ACTIVO"
  }
]
```

#### ❌ Ejemplo 12 — Usuario no existe

**`GET /api/prestamos/usuario/99`**

**Response `404 Not Found`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.090",
  "status": 404,
  "error": "Not Found",
  "message": "Usuario con id 99 no encontrado."
}
```

---

### `PUT /api/prestamos/{id}/devolucion` — Registrar devolución

#### ✅ Ejemplo 13 — Devolución exitosa

**`PUT /api/prestamos/1/devolucion`**

**Response `200 OK`:**
```json
{
  "id": 1,
  "usuario": { "id": 4, "nombre": "Maria Torres" },
  "libro": {
    "id": 1,
    "titulo": "Clean Code",
    "cantidad": 10
  },
  "fechaPrestamo": "2026-03-10",
  "fechaEntregaEstimada": "2026-03-20",
  "fechaEntregaReal": "2026-03-10",
  "estado": "DEVUELTO"
}
```

> ✅ Nota: El stock subió de 9 a 10. `fechaEntregaReal` se registra automáticamente. `estado` → `DEVUELTO`.

#### ❌ Ejemplo 14 — Préstamo no existe

**`PUT /api/prestamos/99/devolucion`**

**Response `404 Not Found`:**
```json
{
  "timestamp": "2026-03-10T19:36:23.108",
  "status": 404,
  "error": "Not Found",
  "message": "Préstamo con id 99 no encontrado."
}
```

---

## 🎯 Valores válidos para Enums

### `rol` (Usuario)
| Valor | Descripción |
|---|---|
| `ROLE_USER` | Usuario regular — puede solicitar préstamos |
| `ROLE_EMPLOYEE` | Empleado — puede registrar libros y autorizar préstamos |
| `ROLE_ADMIN` | Administrador — acceso total |

### `genero` (Libro)
| Valor |
|---|
| `FICCION` |
| `NO_FICCION` |
| `CIENCIA` |
| `HISTORIA` |
| `TECNOLOGIA` |
| `ARTE` |
| `INFANTIL` |
| `OTRO` |

### `estado` (Préstamo) — solo lectura, calculado por el sistema
| Valor | Significado |
|---|---|
| `ACTIVO` | Préstamo en curso |
| `DEVUELTO` | Libro devuelto |
| `RETRASADO` | Devuelto fuera de plazo |

---

> 📝 Todos los ejemplos fueron probados en vivo el **2026-03-11**.  
> La aplicación usa **H2 en memoria** — los IDs pueden variar entre ejecuciones.
