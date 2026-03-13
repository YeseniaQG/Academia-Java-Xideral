# Contexto del Proyecto - ejercicioTres

Este archivo es leído automáticamente por GitHub Copilot CLI al iniciar una sesión en esta carpeta.
Contiene el contexto clave del proyecto para mantener continuidad entre sesiones.

> **Instrucción para Copilot:** Al iniciar sesión, leer también `BUSINESS.md` y `arquitectura.md`
> para tener el contexto completo de reglas de negocio y estructura de código.

---

## 📌 Descripción del Proyecto

REST API de gestión de préstamos de biblioteca, construida con **Spring Boot 3.5.0 + Java 17**.  
Proyecto de demostración de desarrollo asistido por IA con metodología **TDD**.  
Usa **H2 en memoria** para pruebas. Migración a MySQL pendiente para fase productiva.

- **Repositorio:** `github.com:aaron5630/ejerciciciosXideral2026C1.git`, rama `master`
- **Ruta del proyecto:** `Semana 5/ejercicioTres/biblioteca/`
- **Package base:** `com.biblioteca`
- **Puerto:** `8080` | H2 console: `http://localhost:8080/h2-console`

---

## 📖 Archivos de Contexto — Leer al iniciar sesión

| Prioridad | Archivo | Contenido |
|---|---|---|
| 1 | `AGENTS.md` | Este archivo — contexto rápido y convenciones |
| 2 | `BUSINESS.md` | **Fuente de verdad:** entidades, 17 reglas de negocio, 15 casos de uso, endpoints |
| 3 | `arquitectura.md` | Arquitectura en capas, paquetes, excepciones, convenciones de nomenclatura |
| 4 | `TESTING.md` | 40 casos de prueba documentados (TDD) |
| 5 | `CHANGELOG.md` | Historial de commits y cambios |
| 6 | `API_GUIDE.md` | Ejemplos JSON probados en vivo de los 15 endpoints |

---

## 🛠️ Stack Tecnológico

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje |
| Spring Boot | 3.5.0 | Framework principal |
| Spring Web | — | REST API |
| Spring Data JPA | — | Persistencia |
| H2 | — | BD en memoria (pruebas) |
| JUnit 5 | — | Pruebas unitarias |
| Mockito | — | Mocks en pruebas |
| Maven | — | Build tool |

---

## 🏛️ Arquitectura

Arquitectura en capas: `Controller → Service → ServiceImpl → Repository → Model`

### Estructura de paquetes (`com.biblioteca`)

```
model/
  enums/      → Rol, Genero, EstadoPrestamo
  Usuario.java, Libro.java, Prestamo.java
repository/   → UsuarioRepository, LibroRepository, PrestamoRepository
service/
  UsuarioService.java, LibroService.java, PrestamoService.java (interfaces)
  impl/       → UsuarioServiceImpl, LibroServiceImpl, PrestamoServiceImpl
controller/   → UsuarioController, LibroController, PrestamoController
exception/    → 12 excepciones + GlobalExceptionHandler
```

---

## 📦 Entidades y Campos Clave

### `Usuario`
`id, nombre, telefono, email (unique), password, fechaRegistro (auto), activo (default true), rol (Enum)`

### `Libro`
`id, titulo, isbn (unique), autor, genero (Enum), cantidad, disponible (default true), fechaRegistro (auto), registradoPor (Usuario)`

### `Prestamo`
`id, usuario, empleado, libro, fechaPrestamo (auto), fechaEntregaEstimada (auto = +10 días), fechaEntregaReal (null hasta devolución), estado (Enum: ACTIVO/DEVUELTO/RETRASADO)`

---

## 📡 Endpoints (15 en total)

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/usuarios` | Registrar usuario |
| GET | `/api/usuarios` | Listar usuarios |
| GET | `/api/usuarios/{id}` | Obtener por ID |
| PUT | `/api/usuarios/{id}` | Actualizar |
| DELETE | `/api/usuarios/{id}` | Desactivar (soft) |
| POST | `/api/libros?idEmpleado={}` | Registrar libro |
| GET | `/api/libros` | Listar libros |
| GET | `/api/libros/{id}` | Obtener por ID |
| PUT | `/api/libros/{id}` | Actualizar |
| DELETE | `/api/libros/{id}` | Desactivar (soft) |
| POST | `/api/prestamos?idUsuario={}&idEmpleado={}&idLibro={}` | Registrar préstamo |
| PUT | `/api/prestamos/{id}/devolucion` | Registrar devolución |
| GET | `/api/prestamos` | Listar préstamos |
| GET | `/api/prestamos/{id}` | Obtener por ID |
| GET | `/api/prestamos/usuario/{id}` | Préstamos de un usuario |

---

## ✅ Reglas y Convenciones

- Responder siempre en **español**.
- Verificación de roles se hace **manualmente en ServiceImpl** (no hay JWT/Spring Security aún).
- Solo `ROLE_USER` puede solicitar préstamos; `ROLE_EMPLOYEE` y `ROLE_ADMIN` pueden registrar libros.
- `fechaEntregaEstimada` siempre se calcula automáticamente (`+10 días`) — el empleado **NO** la envía.
- `diasRetardo` NO es campo de BD — se calcula dinámicamente en Service.
- Soft delete: usuarios se desactivan (`activo=false`), libros se desactivan (`disponible=false`).
- Hacer cambios precisos y quirúrgicos; no modificar código no relacionado.
- Nombres de métodos de prueba: `metodo_condicion_resultadoEsperado`.

---

## 🔄 Regla de Sincronización de Documentación

**Cada vez que se realice un cambio significativo, Copilot debe actualizar todos los archivos de documentación afectados antes de hacer el commit.**

### ¿Qué es un cambio significativo?
- Se agrega, modifica o elimina una entidad, campo o relación.
- Se agrega, cambia o elimina un endpoint.
- Se agrega o modifica una regla de negocio.
- Se agrega una nueva tecnología o dependencia.
- Se cambia la arquitectura o estructura de paquetes.
- Se define o completa una funcionalidad nueva.
- Se toma una decisión de diseño importante.

### ¿Qué archivos actualizar según el tipo de cambio?

| Tipo de cambio | Archivos a actualizar |
|---|---|
| Nueva entidad o campo | `BUSINESS.md`, `arquitectura.md`, `AGENTS.md` (sección entidades), `TESTING.md` |
| Nuevo endpoint | `arquitectura.md`, `AGENTS.md` (tabla endpoints), `API_GUIDE.md` |
| Nueva regla de negocio | `BUSINESS.md`, `AGENTS.md` (si es regla clave), `TESTING.md` |
| Nueva tecnología | `tecnologias.md`, `AGENTS.md` (stack) |
| Cambio de arquitectura | `arquitectura.md`, `AGENTS.md` (estructura de paquetes) |
| Funcionalidad completada | `AGENTS.md` (tabla de estado), `CHANGELOG.md` |
| Decisión de diseño | `AGENTS.md` (historial de decisiones) |
| Cualquier push | `CHANGELOG.md` (siempre, sin excepción) |

### Flujo de trabajo obligatorio al completar una tarea:
1. Implementar el cambio en código.
2. Actualizar todos los archivos de documentación afectados (tabla anterior).
3. Actualizar `AGENTS.md` → sección "Estado Actual" si corresponde.
4. Registrar en "Historial de Decisiones" si fue un cambio de diseño.
5. Actualizar `CHANGELOG.md`.
6. Hacer `git commit` y `git push` con código + documentación en el mismo commit.

---

## 📋 Responsabilidad sobre CHANGELOG.md

**Copilot es responsable de mantener `CHANGELOG.md` actualizado.**  
Después de cada `push`, agregar entrada con: fecha, commit SHA, archivos cambiados y descripción.  
Formato Conventional Commits: `feat:`, `fix:`, `docs:`, `refactor:`, `chore:`, `test:`.

---

## 📝 Historial de Decisiones

| Fecha | Decisión |
|---|---|
| 2026-03-10 | Stack: Java 17 + Spring Boot 3.5.0 + H2 + JUnit 5 + Mockito |
| 2026-03-10 | Arquitectura en capas; roles verificados en ServiceImpl (sin JWT) |
| 2026-03-10 | TDD: pruebas escritas antes del código (ciclo 🔴🟢🔵) |
| 2026-03-11 | `fechaEntregaEstimada` es automática (+10 días) — no la envía el empleado |
| 2026-03-11 | Test `registrarPrestamo_fechaInvalida` eliminado — no aplica con la regla anterior |
| 2026-03-11 | H2: `bibliotecaV1` en memoria, `ddl-auto=create-drop`, consola habilitada |
| 2026-03-11 | `Prestamo.@PrePersist` auto-asigna fechas y `estado=ACTIVO` |

---

## 🚀 Estado Actual del Proyecto

✅ **COMPLETADO** — La aplicación está funcional y testeada.

| Componente | Estado |
|---|---|
| Documentación (BUSINESS, arquitectura, tecnologias, TESTING) | ✅ Completo |
| Enums (Rol, Genero, EstadoPrestamo) | ✅ Completo |
| Modelos JPA (Usuario, Libro, Prestamo) | ✅ Completo |
| Repositorios (3) | ✅ Completo |
| Excepciones (12 + GlobalExceptionHandler) | ✅ Completo |
| Pruebas TDD (40 pruebas — 🟢 todas pasan) | ✅ Completo |
| Services + ServiceImpl (3 cada uno) | ✅ Completo |
| Controllers (3, 15 endpoints) | ✅ Completo |
| API_GUIDE.md (ejemplos probados en vivo) | ✅ Completo |
| README.md | ✅ Completo |
| Migración a MySQL | ⏳ Pendiente (fase futura) |
| Autenticación JWT / Spring Security | ⏳ Pendiente (fase futura) |
