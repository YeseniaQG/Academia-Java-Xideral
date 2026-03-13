# Changelog

Todos los cambios notables de este proyecto serán documentados aquí.

Formato basado en [Keep a Changelog](https://keepachangelog.com/es/1.0.0/).
Los mensajes de commit siguen la convención [Conventional Commits](https://www.conventionalcommits.org/es/v1.0.0/).

---

## [Sin versión] - 2026-03-10

### Agregado
- `tecnologias.md` — archivo para documentar el stack tecnológico del proyecto.
- `arquitectura.md` — archivo para documentar la arquitectura de la aplicación.
- `AGENTS.md` — archivo de contexto e instrucciones persistentes para GitHub Copilot CLI.
- `CHANGELOG.md` — este archivo, para llevar el registro de cambios del proyecto.
- `TESTING.md` — archivo para documentar la estrategia de pruebas unitarias.

### Commit
- **`49c99b7`** — `docs: agregar estructura base y documentacion inicial de ejercicioTres`
  - Autor: aaron5630 + Copilot
  - Rama: `master` → `origin/master`
  - Archivos: `AGENTS.md`, `CHANGELOG.md`, `TESTING.md`, `arquitectura.md`, `tecnologias.md`

---

## [Sin versión] - 2026-03-10 (2)

### Cambiado
- `.gitignore` — actualizado con configuración completa para proyectos Java/Spring Boot.
  - Eliminado `*.json` para evitar ignorar archivos de configuración necesarios.
  - Agregado `target/`, `*.class`, `*.jar`, `*.war`, `*.ear` (artifacts de Maven).
  - Agregado soporte para IDEs: IntelliJ (`.idea/`, `*.iml`), Eclipse (`.settings/`, `.classpath`, `.project`), VS Code (`.vscode/`).
  - Agregado `.DS_Store`, `Thumbs.db`, `.env`, `.env.local`.

### Commit
- **`ea90228`** — `chore: actualizar .gitignore para proyectos Java/Spring Boot`
  - Autor: aaron5630 + Copilot
  - Rama: `master` → `origin/master`
  - Archivos: `.gitignore`

---

## [Sin versión] - 2026-06-17

### Agregado
- `biblioteca/src/test/java/com/biblioteca/service/UsuarioServiceImplTest.java` — 9 pruebas TDD (fase 🔴).
- `biblioteca/src/test/java/com/biblioteca/service/LibroServiceImplTest.java` — 11 pruebas TDD (fase 🔴).
- `biblioteca/src/test/java/com/biblioteca/service/PrestamoServiceImplTest.java` — 21 pruebas TDD (fase 🔴).

### Commit
- **`d81e23b`** — `test: agregar 41 pruebas TDD (fase roja - services no implementados aun)`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-06-17 (2)

### Agregado
- `UsuarioService.java`, `LibroService.java`, `PrestamoService.java` — interfaces de servicio.
- `UsuarioServiceImpl.java` — implementación con lógica de activación, email único.
- `LibroServiceImpl.java` — implementación con validación de ISBN, rol del empleado.
- `PrestamoServiceImpl.java` — implementación con las 17 reglas de negocio completas.

### Commit
- **`852bc77`** — `feat: agregar interfaces de servicio y sus implementaciones`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-06-17 (3)

### Agregado
- `UsuarioController.java` — 5 endpoints en `/api/usuarios`.
- `LibroController.java` — 5 endpoints en `/api/libros`.
- `PrestamoController.java` — 5 endpoints en `/api/prestamos`.

### Commit
- **`795f2f5`** — `feat: agregar controllers REST (15 endpoints)`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-06-17 (4)

### Cambiado
- `README.md` — completado con descripción del proyecto, instrucciones de ejecución, tabla de endpoints y guía de acceso a H2.
- `CHANGELOG.md` — registro actualizado con todos los pushes anteriores.

### Resultado TDD
- ✅ **41/41 pruebas pasaron** — BUILD SUCCESS

### Commit
- **`2ebe754`** — `docs: completar README y actualizar CHANGELOG`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-03-11 (4)

### Corregido
- `TESTING.md` — eliminado test #14 `registrarPrestamo_fechaInvalida` (no aplica: `fechaEntregaEstimada` es automática). Total corregido: **40 pruebas**.

### Commit
- **`daa1fa5`** — `docs: corregir TESTING.md - eliminar test invalido y actualizar total a 40 pruebas`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-03-11 (5)

### Agregado
- `API_GUIDE.md` — guía de endpoints con instrucciones de ejecución y 14+ ejemplos JSON probados en vivo contra `localhost:8080`. Cubre los 15 endpoints con casos de éxito y error.

### Commit
- **`7d2bfc4`** — `docs: agregar API_GUIDE.md con ejemplos probados en vivo`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-03-11 (6)

### Cambiado
- `AGENTS.md` — reemplazado contenido desactualizado ("Por definir"). Ahora contiene: stack, arquitectura, entidades, endpoints, reglas clave, tabla de estado del proyecto (COMPLETADO) e historial de decisiones técnicas.

### Commit
- **`801538c`** — `docs: actualizar AGENTS.md con contexto completo del proyecto terminado`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-03-11 (7)

### Cambiado
- `AGENTS.md` — agregada sección "Regla de Sincronización de Documentación" con tabla de qué archivos actualizar según el tipo de cambio y flujo de trabajo obligatorio antes de cada commit.

### Commit
- **`76a545b`** — `docs: agregar regla de sincronizacion de documentacion en AGENTS.md`
  - Autor: aaron5630 + Copilot | Rama: `master`

---

## [Sin versión] - 2026-03-11 (8)

### Corregido
- `BUSINESS.md` — todas las funcionalidades marcadas como `✅ Implementado` (estaban como `🔲 Pendiente`). Endpoints actualizados con prefijo `/api/` y rutas reales. `fechaEntregaEstimada` corregida como automática.
- `arquitectura.md` — endpoint corregido de `/prestamos/{id}/devolver` a `/prestamos/{id}/devolucion`.

### Commit
- **_(este push)_** — `docs: corregir inconsistencias en BUSINESS.md y arquitectura.md`
  - Autor: aaron5630 + Copilot | Rama: `master`

---
> Cada vez que se realice un `push` o `pull` en el proyecto, Copilot actualizará este registro
> con el autor, fecha y descripción del cambio correspondiente.

---

## [Sin versión] - 2026-03-11

### Agregado
- `README.md` — estructura base del proyecto con descripción, instrucciones y referencias.
- `BUSINESS.md` — documentación del negocio con descripción, 15 funcionalidades, 17 reglas de negocio, 3 entidades (Usuario, Libro, Préstamo) y 15 casos de uso.

### Cambiado
- `arquitectura.md` — completado con patrón en capas, estructura de paquetes, responsabilidades, modelo de datos, 15 endpoints, 11 excepciones, principios SOLID y convenciones de nomenclatura.
- `BUSINESS.md` — actualizado con endpoints detallados por recurso y casos de uso completos.
- `TESTING.md` — actualizado con estructura base para pruebas unitarias.

---

## [Sin versión] - 2026-03-11 (2)

### Cambiado
- `TESTING.md` — completado con 41 casos de prueba (9 Usuario, 11 Libro, 21 Préstamo), estrategia TDD, patrón AAA y convenciones de nomenclatura.
- `tecnologias.md` — agregada sección de TDD como metodología de desarrollo.
- `arquitectura.md` — agregada sección de TDD con ciclo 🔴🟢🔵 y justificación.
- `CHANGELOG.md` — registro actualizado.

---

## [Sin versión] - 2026-03-11 (3)

### Cambiado
- `BUSINESS.md` — corregida regla 5 (ROLE_EMPLOYEE puede registrar libros), regla 17 y CU-11 (fechaEntregaEstimada es automática).
- `arquitectura.md` — agregada `RolNoPermitidoException` (403 Forbidden), estructura de paquetes actualizada, sección de configuración H2 (`bibliotecaV1`, en memoria, consola en `localhost:8080/h2-console`).
- `TESTING.md` — corregido nombre de prueba #4 de Préstamo a `registrarPrestamo_usuarioSinRolUser_lanzaRolNoPermitidoException`.

### Commit
- **`6213722`** — `docs: corregir inconsistencias y agregar configuracion H2`
  - Autor: aaron5630 + Copilot
  - Rama: `master` → `origin/master`
  - Archivos: `BUSINESS.md`, `arquitectura.md`, `TESTING.md`
