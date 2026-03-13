# Reglas de Negocio y Funcionalidades

> ⚠️ Este archivo es leído por GitHub Copilot CLI al iniciar cada sesión.
> Contiene las reglas de negocio, funcionalidades y casos de uso de la aplicación.
> Debe mantenerse actualizado antes de comenzar a codificar.

---

## 📌 Descripción del Negocio

Aplicación REST que simula un sistema de préstamos de libros para una biblioteca. Permite gestionar usuarios, libros y préstamos, controlando quién solicita un libro y qué empleado lo autoriza. Los usuarios tienen roles que determinan qué acciones pueden realizar dentro del sistema.

---

## ✅ Funcionalidades

| # | Funcionalidad | Método HTTP | Endpoint | Estado |
|---|---|---|---|---|
| 1 | Listar todos los usuarios | `GET` | `/api/usuarios` | ✅ Implementado |
| 2 | Buscar usuario por id | `GET` | `/api/usuarios/{id}` | ✅ Implementado |
| 3 | Registrar usuario | `POST` | `/api/usuarios` | ✅ Implementado |
| 4 | Actualizar usuario | `PUT` | `/api/usuarios/{id}` | ✅ Implementado |
| 5 | Desactivar usuario | `DELETE` | `/api/usuarios/{id}` | ✅ Implementado |
| 6 | Listar todos los libros | `GET` | `/api/libros` | ✅ Implementado |
| 7 | Buscar libro por id | `GET` | `/api/libros/{id}` | ✅ Implementado |
| 8 | Registrar libro | `POST` | `/api/libros?idEmpleado={}` | ✅ Implementado |
| 9 | Actualizar libro | `PUT` | `/api/libros/{id}` | ✅ Implementado |
| 10 | Desactivar libro | `DELETE` | `/api/libros/{id}` | ✅ Implementado |
| 11 | Listar todos los préstamos | `GET` | `/api/prestamos` | ✅ Implementado |
| 12 | Buscar préstamo por id | `GET` | `/api/prestamos/{id}` | ✅ Implementado |
| 13 | Listar préstamos por usuario | `GET` | `/api/prestamos/usuario/{id}` | ✅ Implementado |
| 14 | Registrar préstamo | `POST` | `/api/prestamos?idUsuario={}&idEmpleado={}&idLibro={}` | ✅ Implementado |
| 15 | Registrar devolución | `PUT` | `/api/prestamos/{id}/devolucion` | ✅ Implementado |

---

## 📋 Reglas de Negocio

| # | Regla | Descripción |
|---|---|---|
| 1 | Rol obligatorio | Todo usuario debe tener un rol asignado al registrarse |
| 2 | Email único | El email es irrepetible y se usa como identificador de validación |
| 3 | Baja lógica de usuarios | Los usuarios no se eliminan físicamente, se desactivan con `activo = false` |
| 4 | Solo empleados gestionan préstamos | Solo `ROLE_EMPLOYEE` o `ROLE_ADMIN` pueden registrar y autorizar préstamos |
| 5 | Solo admin gestiona catálogo | Solo `ROLE_ADMIN` puede editar o eliminar libros y usuarios. `ROLE_ADMIN` y `ROLE_EMPLOYEE` pueden registrar libros |
| 6 | Registro de responsable | Todo préstamo debe registrar el empleado que lo autorizó |
| 7 | Máximo 3 préstamos activos | Un `ROLE_USER` no puede tener más de 3 préstamos con estado `ACTIVO` simultáneamente |
| 8 | Sin préstamos con retardo | Si el usuario tiene algún préstamo con estado `RETRASADO`, no puede solicitar otro hasta que lo devuelva |
| 9 | Usuario activo para prestar | El usuario solicitante debe tener `activo = true` para poder pedir un préstamo |
| 10 | Empleado activo para autorizar | El empleado autorizante debe tener `activo = true` para poder registrar un préstamo |
| 11 | Libro disponible y con stock | El libro debe tener `disponible = true` y `cantidad > 0` para poder prestarse |
| 12 | Solo ROLE_USER solicita préstamos | Los usuarios con `ROLE_EMPLOYEE` o `ROLE_ADMIN` no pueden ser solicitantes de préstamos |
| 13 | Stock dinámico | Al registrar un préstamo, `cantidad` del libro disminuye en 1. Al registrar la devolución, aumenta en 1 |
| 14 | Sin duplicado activo | Un usuario no puede tener 2 préstamos `ACTIVOS` del mismo libro simultáneamente |
| 15 | Fecha estimada válida | La `fechaEntregaEstimada` siempre debe ser posterior a la `fechaPrestamo` |
| 16 | Empleado no puede ser solicitante | El empleado autorizante no puede ser el mismo usuario que el solicitante del préstamo |
| 17 | Límite de préstamo | La duración máxima de un préstamo es de **10 días**. La `fechaEntregaEstimada` se calcula **automáticamente** por el sistema como `fechaPrestamo + 10 días`. El empleado no la elige. |

---

## 🗂️ Entidades del Dominio

### 📘 `Usuario`

Representa a cualquier persona registrada en el sistema. El rol determina sus permisos.

| Campo | Tipo | Descripción | Restricciones |
|---|---|---|---|
| `id` | `Long` | Identificador único | Automático, generado por JPA |
| `nombre` | `String` | Nombre completo del usuario | Requerido |
| `telefono` | `String` | Teléfono de contacto | Requerido |
| `email` | `String` | Correo electrónico | Único, requerido, formato válido |
| `password` | `String` | Contraseña del usuario | Requerido (seguridad futura) |
| `fechaRegistro` | `LocalDate` | Fecha en que se registró | Automática al crear |
| `activo` | `boolean` | Indica si el usuario está activo | `true` por defecto |
| `rol` | `Enum` | Rol del usuario en el sistema | `ROLE_USER`, `ROLE_EMPLOYEE`, `ROLE_ADMIN` |

**Roles:**
| Rol | Descripción |
|---|---|
| `ROLE_USER` | Alumno/lector — puede solicitar y devolver préstamos |
| `ROLE_EMPLOYEE` | Empleado de biblioteca — registra y autoriza préstamos |
| `ROLE_ADMIN` | Administrador — gestiona usuarios y catálogo de libros |

**Relaciones:**
- Un `Usuario` puede tener **muchos** `Prestamo` como solicitante
- Un `Usuario` con rol `ROLE_EMPLOYEE` puede aparecer en **muchos** `Prestamo` como empleado autorizante
- Un `Usuario` con rol `ROLE_EMPLOYEE` o `ROLE_ADMIN` puede registrar **muchos** `Libro` en el catálogo

---

### 📗 `Libro`

Representa los libros disponibles en el catálogo de la biblioteca.

| Campo | Tipo | Descripción | Restricciones |
|---|---|---|---|
| `id` | `Long` | Identificador único | Automático, generado por JPA |
| `titulo` | `String` | Título del libro | Requerido |
| `isbn` | `String` | Identificador universal del libro | Único, requerido |
| `autor` | `String` | Autor del libro | Requerido |
| `genero` | `Enum` | Género literario del libro | Requerido, valores controlados |
| `cantidad` | `int` | Stock disponible para préstamo | Requerido, mínimo 0 |
| `disponible` | `boolean` | Visibilidad del libro en el sistema | `true` por defecto |
| `fechaRegistro` | `LocalDate` | Fecha en que se agregó al catálogo | Automática al crear |
| `registradoPor` | `Usuario` | Empleado que registró el libro | Requerido, debe ser `ROLE_EMPLOYEE` o `ROLE_ADMIN` |

**Géneros (`Enum`):**
| Valor | Descripción |
|---|---|
| `FICCION` | Literatura de ficción |
| `NO_FICCION` | Literatura de no ficción |
| `CIENCIA` | Libros científicos |
| `HISTORIA` | Libros históricos |
| `TECNOLOGIA` | Libros de tecnología |
| `ARTE` | Libros de arte y cultura |
| `INFANTIL` | Literatura infantil |
| `OTRO` | Otros géneros |

**Relaciones:**
- Un `Libro` puede estar en **muchos** `Prestamo`
- Un `Libro` fue registrado por **un** `Usuario` (empleado o admin)

---

### 🔄 `Prestamo`

Representa el préstamo de un libro a un usuario. Es la entidad central de la aplicación.

| Campo | Tipo | Descripción | Restricciones |
|---|---|---|---|
| `id` | `Long` | Identificador único | Automático, generado por JPA |
| `usuario` | `Usuario` | Alumno que solicita el libro | Requerido, debe tener `ROLE_USER` |
| `empleado` | `Usuario` | Empleado que autoriza el préstamo | Requerido, debe tener `ROLE_EMPLOYEE` o `ROLE_ADMIN` |
| `libro` | `Libro` | Libro prestado | Requerido |
| `fechaPrestamo` | `LocalDate` | Fecha en que se realizó el préstamo | Automática al crear |
| `fechaEntregaEstimada` | `LocalDate` | Fecha límite para devolver el libro | **Automática** — calculada por el sistema como `fechaPrestamo + 10 días`. El empleado no la envía. |
| `fechaEntregaReal` | `LocalDate` | Fecha en que se devolvió el libro | `null` hasta que se registre la devolución |
| `estado` | `Enum` | Estado actual del préstamo | `ACTIVO` por defecto al crear |

**Estados (`Enum`):**
| Valor | Descripción |
|---|---|
| `ACTIVO` | Libro prestado, aún no devuelto |
| `DEVUELTO` | Libro devuelto a tiempo |
| `RETRASADO` | Libro no devuelto en la fecha estimada |

> 📌 `diasRetardo` **no es un campo** — se calcula dinámicamente en el Service como `fechaActual - fechaEntregaEstimada` cuando el estado es `RETRASADO`.

**Relaciones:**
- Un `Prestamo` pertenece a **un** `Usuario` (solicitante)
- Un `Prestamo` pertenece a **un** `Usuario` (empleado autorizante)
- Un `Prestamo` pertenece a **un** `Libro`

---

## 🔄 Casos de Uso

---

### 👤 Usuarios

**CU-01 — Registrar usuario**
```
1. Admin envía datos del nuevo usuario (nombre, email, teléfono, password, rol)
2. Sistema verifica que el email no esté registrado previamente
3. Sistema asigna fechaRegistro automáticamente
4. Sistema asigna activo = true por defecto
5. Sistema guarda el usuario y retorna los datos registrados
```

**CU-02 — Consultar usuario**
```
1. Se solicita un usuario por su id
2. Sistema verifica que el usuario exista
3. Sistema retorna los datos del usuario
```

**CU-03 — Consultar todos los usuarios**
```
1. Se solicita la lista de usuarios
2. Sistema retorna todos los usuarios registrados
```

**CU-04 — Actualizar usuario**
```
1. Admin envía los datos actualizados del usuario por su id
2. Sistema verifica que el usuario exista
3. Sistema actualiza los datos y retorna el usuario actualizado
```

**CU-05 — Desactivar usuario (baja lógica)**
```
1. Admin solicita desactivar un usuario por su id
2. Sistema verifica que el usuario exista
3. Sistema cambia activo = false
4. Sistema retorna confirmación
```

---

### 📗 Libros

**CU-06 — Registrar libro**
```
1. Admin o empleado envía datos del libro (titulo, isbn, autor, genero, cantidad, registradoPor)
2. Sistema verifica que el ISBN no esté registrado previamente
3. Sistema verifica que registradoPor sea un usuario activo con ROLE_EMPLOYEE o ROLE_ADMIN
4. Sistema asigna fechaRegistro automáticamente
5. Sistema asigna disponible = true por defecto
6. Sistema guarda el libro y retorna los datos registrados
```

**CU-07 — Consultar libro**
```
1. Se solicita un libro por su id
2. Sistema verifica que el libro exista
3. Sistema retorna los datos del libro
```

**CU-08 — Consultar todos los libros**
```
1. Se solicita el catálogo de libros
2. Sistema retorna todos los libros registrados
```

**CU-09 — Actualizar libro**
```
1. Admin envía los datos actualizados del libro por su id
2. Sistema verifica que el libro exista
3. Sistema actualiza los datos y retorna el libro actualizado
```

**CU-10 — Desactivar libro**
```
1. Admin solicita desactivar un libro por su id
2. Sistema verifica que el libro exista
3. Sistema cambia disponible = false
4. Sistema retorna confirmación
```

---

### 🔄 Préstamos

**CU-11 — Registrar préstamo**
```
1. Empleado envía solicitud de préstamo (idUsuario, idEmpleado, idLibro)
2. Sistema verifica que el usuario solicitante exista y esté activo
3. Sistema verifica que el usuario tenga ROLE_USER
4. Sistema verifica que el usuario no tenga más de 3 préstamos ACTIVOS
5. Sistema verifica que el usuario no tenga préstamos con estado RETRASADO
6. Sistema verifica que el empleado exista, esté activo y no sea el mismo que el solicitante
7. Sistema verifica que el libro exista, tenga disponible = true y cantidad > 0
8. Sistema verifica que el usuario no tenga ya un préstamo ACTIVO del mismo libro
9. Sistema calcula automáticamente fechaEntregaEstimada = fechaPrestamo + 10 días
10. Sistema registra el préstamo con estado = ACTIVO y fechaPrestamo = hoy
11. Sistema descuenta 1 a la cantidad del libro
12. Sistema retorna los datos del préstamo registrado
```

**CU-12 — Registrar devolución**
```
1. Empleado envía solicitud de devolución por idPrestamo
2. Sistema verifica que el préstamo exista y tenga estado ACTIVO o RETRASADO
3. Sistema registra fechaEntregaReal = hoy
4. Sistema cambia estado = DEVUELTO
5. Sistema incrementa 1 a la cantidad del libro
6. Sistema retorna los datos del préstamo actualizado
```

**CU-13 — Consultar préstamo por id**
```
1. Se solicita un préstamo por su id
2. Sistema verifica que el préstamo exista
3. Si estado = ACTIVO y fechaActual > fechaEntregaEstimada, Sistema calcula diasRetardo
4. Sistema retorna los datos del préstamo
```

**CU-14 — Consultar préstamos por usuario**
```
1. Se solicita la lista de préstamos de un usuario por su id
2. Sistema verifica que el usuario exista
3. Sistema retorna todos los préstamos del usuario
```

**CU-15 — Consultar todos los préstamos**
```
1. Se solicita la lista completa de préstamos
2. Sistema retorna todos los préstamos registrados
```

---

---

> 📝 Última actualización: 2026-03-11
