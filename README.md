# proyectoMaven

> Sistema de gestión de tareas con Spring Boot 3.2.0 y autenticación JWT.

[![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-6DB33F?logo=spring-boot)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## 📖 ¿Qué es este proyecto?

Aplicación Spring Boot para aprendizaje y desarrollo de un sistema de gestión de tareas. Implementa autenticación JWT, CRUD de usuarios y está preparada para expandirse con gestión de tareas relacionadas a usuarios.

**Estado actual:** ✅ CRUD de usuarios completo + autenticación JWT implementada  
**Próximo hito:** 📋 Entidad `Tarea` con relación a `Usuario`

---

## 🚀 Inicio Rápido

### Prerrequisitos

```bash
java -version      # Requiere Java 17+
mvn -version       # Requiere Maven 3.9+
```

### Ejecutar

```bash
# 1. Clonar y entrar al proyecto
git clone https://github.com/danielhumsr30/prueba-IA.git
cd prueba-IA

# 2. Compilar
mvn clean compile

# 3. Ejecutar
mvn spring-boot:run
```

La aplicación estará disponible en **http://localhost:8080**

### Verificación rápida

```bash
# Listar usuarios (endpoint público)
curl http://localhost:8080/usuarios

# Login y obtener JWT
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usuario":"usuario1","password":"usuario1"}'
```

---

## 📋 Endpoints Disponibles

| Método | Ruta | Autenticación | Descripción |
|--------|------|---------------|-------------|
| `POST` | `/auth/login` | ❌ No | Login, devuelve JWT |
| `GET` | `/usuarios` | ❌ No | Listar todos los usuarios |
| `POST` | `/usuarios/{id}` | ✅ JWT | Obtener usuario por ID |
| `POST` | `/usuarios/editar/{id}` | ✅ JWT | Editar usuario |
| `POST` | `/usuarios/eliminar/{id}` | ✅ JWT | Eliminar usuario |

<details>
<summary><strong>Ver ejemplos de uso con curl</strong></summary>

### Autenticación

```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"usuario":"usuario1","password":"usuario1"}'
```

Respuesta:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "usuario": "usuario1",
  "success": true,
  "mensaje": "Autenticación exitosa"
}
```

### Usuario protegido (requiere token)

```bash
TOKEN="eyJhbGciOiJIUzUxMiJ9..."

# Obtener usuario por ID
curl -X POST http://localhost:8080/usuarios/1 \
  -H "Authorization: Bearer $TOKEN"

# Editar usuario
curl -X POST http://localhost:8080/usuarios/editar/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Nuevo Nombre", "correo": "nuevo@email.com"}'

# Eliminar usuario
curl -X POST http://localhost:8080/usuarios/eliminar/2 \
  -H "Authorization: Bearer $TOKEN"
```

</details>

---

## 🛠 Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje base |
| Spring Boot | 3.2.0 | Framework principal |
| Spring Security | 6.2.0 | Autenticación y autorización |
| Spring Data JPA | - | Persistencia de datos |
| Hibernate | 6.3.1.Final | ORM |
| H2 Database | Runtime | BD en memoria (desarrollo) |
| JJWT | 0.12.3 | Generación y validación de JWT |
| Maven | 3.9.15 | Build y dependencias |

---

## 📁 Estructura del Proyecto

```
proyectoMaven/
├── pom.xml                          # Configuración Maven
├── README.md                        # Documentación principal
├── src/main/java/com/example/
│   ├── demo/
│   │   └── DemoApplication.java     # Clase principal
│   └── proyectoMaven/
│       ├── controller/              # Endpoints REST
│       ├── service/                 # Lógica de negocio
│       ├── repository/              # Acceso a datos
│       ├── model/                   # Entidades JPA
│       ├── dto/                     # Objetos de transferencia
│       └── security/                # JWT y configuración Security
└── src/main/resources/
    ├── application.properties       # Configuración
    └── import.sql                   # Datos de prueba
```

---

## ⚙️ Configuración

### Base de datos (H2 Console)

Accede a la consola web de H2 en **http://localhost:8080/h2-console**

| Parámetro | Valor |
|-----------|-------|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | (vacío) |

### Usuarios de prueba

| Usuario | Password | Estado |
|---------|----------|--------|
| usuario1 | usuario1 | Activo |
| usuario2 | usuario2 | Inactivo |
| usuario3 | usuario3 | Activo |

> ⚠️ **Nota:** Las contraseñas están en texto plano para desarrollo. Ver sección de seguridad.

---

## 📌 Próximos Pasos (Roadmap)

### Prioridad Alta
- [ ] Crear entidad `Tarea` con relación a `Usuario`
- [ ] Implementar CRUD de tareas
- [ ] Asociar tareas al usuario autenticado (vía JWT)
- [ ] Tests unitarios con JUnit + Mockito

### Prioridad Media
- [ ] Encriptar passwords con BCrypt
- [ ] DTOs para separar entidades de respuestas
- [ ] Manejo global de excepciones (`@ControllerAdvice`)
- [ ] Documentación Swagger/OpenAPI

### Prioridad Baja
- [ ] Migrar a PostgreSQL para persistencia real
- [ ] Dockerizar la aplicación
- [ ] Implementar refresh token

---

## 🔒 Seguridad - Consideraciones de Desarrollo

Este proyecto está configurado para **desarrollo y aprendizaje**. Antes de usar en producción:

| Configuración actual | Recomendación producción |
|---------------------|-------------------------|
| `PasswordEncoder` en texto plano | Usar `BCryptPasswordEncoder` |
| JWT Secret hardcoded | Variable de entorno |
| CORS: `*` (abierto) | Restringir a dominios específicos |
| H2 en memoria | PostgreSQL/MySQL persistente |
| `ddl-auto=create-drop` | `ddl-auto=validate` o migraciones |

---

## 📚 Documentación Adicional

- [Guía de contribución](CONTRIBUTING.md) *(pendiente)*
- [Historial de cambios](CHANGELOG.md) *(pendiente)*

### Recursos externos
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [JJWT Library](https://github.com/jwtk/jjwt)

---

## 📄 Licencia

MIT © [Daniel Humsr](https://github.com/danielhumsr30/prueba-IA)

---

<details>
<summary><strong>Comandos útiles de Maven</strong></summary>

```bash
mvn clean compile        # Compilar proyecto
mvn clean package        # Crear JAR ejecutable
mvn spring-boot:run      # Ejecutar aplicación
mvn test                 # Ejecutar tests
mvn test -Dtest=Clase    # Ejecutar test específico
```

</details>
