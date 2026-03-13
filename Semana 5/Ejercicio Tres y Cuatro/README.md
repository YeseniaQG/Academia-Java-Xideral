# Biblioteca API — Sistema de Préstamo de Libros

> REST API simulando el sistema de préstamos de una biblioteca, construida con Spring Boot 3.5.0 y metodología TDD.

---

## 📌 Descripción

Aplicación backend que permite gestionar usuarios, libros y préstamos de una biblioteca.  
Incluye validaciones de reglas de negocio (límite de préstamos, roles, disponibilidad de libros, etc.).  
Actualmente usa base de datos H2 en memoria para pruebas; la migración a MySQL se realizará en una fase posterior.

---

## 🚀 ¿Cómo correr el proyecto?

### Requisitos previos

- Java 17+
- Maven (o usar el wrapper `mvnw` incluido)

### Pasos

```bash
# Clonar el repositorio
git clone https://github.com/aaron5630/ejerciciciosXideral2026C1.git

# Entrar a la carpeta del proyecto
cd "Semana 5/ejercicioTres/biblioteca"

# Correr la aplicación (Windows)
.\mvnw spring-boot:run

# Correr la aplicación (Linux/Mac)
./mvnw spring-boot:run
```

La aplicación inicia en: `http://localhost:8080`

### Consola H2 (base de datos en memoria)

1. Con la app corriendo, abre: `http://localhost:8080/h2-console`
2. Usa la siguiente configuración:
   - **JDBC URL**: `jdbc:h2:mem:bibliotecaV1`
   - **User Name**: `sa`
   - **Password**: _(dejar vacío)_
3. Haz clic en **Connect**

> ⚠️ Los datos NO persisten al detener la aplicación (comportamiento esperado en fase de pruebas).

### Correr pruebas

```bash
.\mvnw test       # Windows
./mvnw test       # Linux/Mac
```

Resultado esperado: **41 pruebas, 0 fallos**.

---

## 📡 Endpoints disponibles

### Usuarios — `/api/usuarios`

| Método | Ruta              | Descripción                    |
|--------|-------------------|--------------------------------|
| POST   | `/api/usuarios`   | Registrar nuevo usuario        |
| GET    | `/api/usuarios`   | Listar todos los usuarios      |
| GET    | `/api/usuarios/{id}` | Obtener usuario por ID      |
| PUT    | `/api/usuarios/{id}` | Actualizar usuario          |
| DELETE | `/api/usuarios/{id}` | Desactivar usuario (soft)   |

### Libros — `/api/libros`

| Método | Ruta                         | Descripción                          |
|--------|------------------------------|--------------------------------------|
| POST   | `/api/libros?idEmpleado={id}`| Registrar libro (requiere empleado)  |
| GET    | `/api/libros`                | Listar todos los libros              |
| GET    | `/api/libros/{id}`           | Obtener libro por ID                 |
| PUT    | `/api/libros/{id}`           | Actualizar libro                     |
| DELETE | `/api/libros/{id}`           | Desactivar libro (soft)              |

### Préstamos — `/api/prestamos`

| Método | Ruta                                         | Descripción                     |
|--------|----------------------------------------------|---------------------------------|
| POST   | `/api/prestamos?idUsuario={}&idEmpleado={}&idLibro={}` | Registrar préstamo |
| PUT    | `/api/prestamos/{id}/devolucion`             | Registrar devolución            |
| GET    | `/api/prestamos`                             | Listar todos los préstamos      |
| GET    | `/api/prestamos/{id}`                        | Obtener préstamo por ID         |
| GET    | `/api/prestamos/usuario/{idUsuario}`         | Préstamos de un usuario         |

---

## 🛠️ Tecnologías

Ver [`tecnologias.md`](./tecnologias.md) para el detalle completo del stack.

---

## 🏛️ Arquitectura

Ver [`arquitectura.md`](./arquitectura.md) para el detalle de la arquitectura en capas.

---

## 📋 Documentación

| Archivo | Contenido |
|---------|-----------|
| [`BUSINESS.md`](./BUSINESS.md) | Reglas de negocio, entidades, casos de uso |
| [`arquitectura.md`](./arquitectura.md) | Arquitectura, paquetes, excepciones |
| [`tecnologias.md`](./tecnologias.md) | Stack tecnológico y TDD |
| [`TESTING.md`](./TESTING.md) | 41 casos de prueba documentados |
| [`CHANGELOG.md`](./CHANGELOG.md) | Historial de cambios |

---

> 📝 Última actualización: 2026-06-17
