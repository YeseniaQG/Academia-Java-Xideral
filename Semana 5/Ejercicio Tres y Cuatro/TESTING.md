# Pruebas Unitarias

Este archivo documenta la estrategia de pruebas del proyecto, los casos de prueba y las convenciones utilizadas.

> ⚠️ Este proyecto sigue la metodología **TDD (Test-Driven Development)**.
> Las pruebas se escriben **antes** de implementar el código.
> Ciclo: 🔴 Prueba falla → 🟢 Código mínimo para que pase → 🔵 Refactor

---

## 🧪 Tecnologías de Prueba

| Tecnología | Rol |
|---|---|
| **JUnit 5** | Framework principal para pruebas unitarias |
| **Mockito** | Simulación de dependencias (repositorios) en pruebas del Service |

---

## 📋 Estrategia de Pruebas

- Se prueban únicamente los **ServiceImpl** — es donde vive la lógica de negocio y las reglas.
- Los **Repository** se simulan con Mockito (`@Mock`).
- Cada prueba sigue el patrón **AAA**: `Arrange` (preparar), `Act` (ejecutar), `Assert` (verificar).
- Los nombres de los métodos de prueba siguen el patrón: `metodo_condicion_resultadoEsperado`.

---

## 🗂️ Casos de Prueba

---

### 👤 `UsuarioServiceImpl`

| # | Método de prueba | Descripción |
|---|---|---|
| 1 | `registrarUsuario_exitoso` | Registra un usuario correctamente |
| 2 | `registrarUsuario_emailDuplicado_lanzaEmailDuplicadoException` | Lanza excepción si el email ya existe |
| 3 | `obtenerUsuarioPorId_exitoso` | Retorna el usuario correctamente |
| 4 | `obtenerUsuarioPorId_noExiste_lanzaUsuarioNotFoundException` | Lanza excepción si el usuario no existe |
| 5 | `obtenerTodosLosUsuarios_exitoso` | Retorna la lista de usuarios |
| 6 | `actualizarUsuario_exitoso` | Actualiza los datos del usuario correctamente |
| 7 | `actualizarUsuario_noExiste_lanzaUsuarioNotFoundException` | Lanza excepción si el usuario no existe |
| 8 | `desactivarUsuario_exitoso` | Cambia `activo = false` correctamente |
| 9 | `desactivarUsuario_noExiste_lanzaUsuarioNotFoundException` | Lanza excepción si el usuario no existe |

---

### 📗 `LibroServiceImpl`

| # | Método de prueba | Descripción |
|---|---|---|
| 1 | `registrarLibro_exitoso` | Registra un libro correctamente |
| 2 | `registrarLibro_isbnDuplicado_lanzaIsbnDuplicadoException` | Lanza excepción si el ISBN ya existe |
| 3 | `registrarLibro_empleadoInactivo_lanzaEmpleadoNoValidoException` | Lanza excepción si quien registra está inactivo |
| 4 | `registrarLibro_empleadoSinRol_lanzaEmpleadoNoValidoException` | Lanza excepción si quien registra no tiene rol válido |
| 5 | `obtenerLibroPorId_exitoso` | Retorna el libro correctamente |
| 6 | `obtenerLibroPorId_noExiste_lanzaLibroNotFoundException` | Lanza excepción si el libro no existe |
| 7 | `obtenerTodosLosLibros_exitoso` | Retorna la lista de libros |
| 8 | `actualizarLibro_exitoso` | Actualiza los datos del libro correctamente |
| 9 | `actualizarLibro_noExiste_lanzaLibroNotFoundException` | Lanza excepción si el libro no existe |
| 10 | `desactivarLibro_exitoso` | Cambia `disponible = false` correctamente |
| 11 | `desactivarLibro_noExiste_lanzaLibroNotFoundException` | Lanza excepción si el libro no existe |

---

### 🔄 `PrestamoServiceImpl`

| # | Método de prueba | Descripción |
|---|---|---|
| 1 | `registrarPrestamo_exitoso` | Registra el préstamo correctamente y descuenta stock |
| 2 | `registrarPrestamo_usuarioNoExiste_lanzaUsuarioNotFoundException` | Lanza excepción si el usuario no existe |
| 3 | `registrarPrestamo_usuarioInactivo_lanzaUsuarioInactivoException` | Lanza excepción si el usuario está inactivo |
| 4 | `registrarPrestamo_usuarioSinRolUser_lanzaRolNoPermitidoException` | Lanza excepción si el usuario no tiene `ROLE_USER` |
| 5 | `registrarPrestamo_limiteActivos_lanzaLimitePrestamosException` | Lanza excepción si el usuario ya tiene 3 préstamos activos |
| 6 | `registrarPrestamo_conRetardo_lanzaPrestamoRetrasadoException` | Lanza excepción si el usuario tiene préstamos retrasados |
| 7 | `registrarPrestamo_empleadoNoExiste_lanzaUsuarioNotFoundException` | Lanza excepción si el empleado no existe |
| 8 | `registrarPrestamo_empleadoInactivo_lanzaEmpleadoNoValidoException` | Lanza excepción si el empleado está inactivo |
| 9 | `registrarPrestamo_empleadoEsSolicitante_lanzaEmpleadoNoValidoException` | Lanza excepción si el empleado y el solicitante son el mismo |
| 10 | `registrarPrestamo_libroNoExiste_lanzaLibroNotFoundException` | Lanza excepción si el libro no existe |
| 11 | `registrarPrestamo_libroNoDisponible_lanzaLibroNoDisponibleException` | Lanza excepción si el libro tiene `disponible = false` |
| 12 | `registrarPrestamo_sinStock_lanzaLibroNoDisponibleException` | Lanza excepción si el libro tiene `cantidad = 0` |
| 13 | `registrarPrestamo_libroYaPrestado_lanzaLibroYaPrestadoException` | Lanza excepción si el usuario ya tiene ese libro activo |
| 14 | `registrarDevolucion_exitoso` | Registra la devolución e incrementa el stock del libro |
| 15 | `registrarDevolucion_noExiste_lanzaPrestamoNotFoundException` | Lanza excepción si el préstamo no existe |
| 16 | `obtenerPrestamoPorId_exitoso` | Retorna el préstamo correctamente |
| 17 | `obtenerPrestamoPorId_noExiste_lanzaPrestamoNotFoundException` | Lanza excepción si el préstamo no existe |
| 18 | `obtenerTodosLosPrestamos_exitoso` | Retorna la lista de préstamos |
| 19 | `obtenerPrestamosPorUsuario_exitoso` | Retorna los préstamos de un usuario |
| 20 | `obtenerPrestamosPorUsuario_usuarioNoExiste_lanzaUsuarioNotFoundException` | Lanza excepción si el usuario no existe |

> ℹ️ **Nota:** El test `registrarPrestamo_fechaInvalida` fue eliminado porque `fechaEntregaEstimada`
> siempre se calcula automáticamente como `fechaPrestamo + 10 días` en el Service.
> El empleado no envía ese valor, por lo que no puede ser inválido.

---

## 📝 Convenciones para Pruebas

| Elemento | Convención | Ejemplo |
|---|---|---|
| Clase de prueba | `*ServiceImplTest` | `UsuarioServiceImplTest` |
| Método de prueba | `metodo_condicion_resultadoEsperado` | `registrarUsuario_emailDuplicado_lanzaEmailDuplicadoException` |
| Anotación de prueba | `@Test` | — |
| Prueba de excepción | `assertThrows(Excepcion.class, ...)` | — |
| Simulación | `@Mock` para repositorios, `@InjectMocks` para el Service | — |
| Patrón de prueba | `AAA` — Arrange / Act / Assert | — |

---

> 📝 Última actualización: 2026-03-11

