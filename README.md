# Demo - Spring Boot Application

Proyecto base de Spring Boot para aprendizaje y desarrollo de aplicaciones Java con persistencia JPA.

**Nombre del Proyecto:** proyectoMaven  
**Propósito:** Sistema de gestión de tareas (en desarrollo)  
**Rama actual:** `primerPaso`

---

## 📋 Estado del Proyecto

| Estado | Versión | Última actualización | Rama |
|--------|---------|---------------------|------|
| ✅ CRUD Usuarios Funcional | 0.0.1-SNAPSHOT | 2026-05-01 | primerPaso |

**Última sesión:** Se implementó CRUD completo de usuarios con validaciones, datos de prueba y pruebas exitosas de todos los endpoints.

---

## 🛠 Stack Tecnológico

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| **Java** | 17 | Lenguaje base |
| **Spring Boot** | 3.2.0 | Framework principal |
| **Maven** | 3.9.15 | Gestor de dependencias y build |
| **H2 Database** | Runtime | Base de datos en memoria (desarrollo) |
| **Hibernate** | 6.3.1.Final | ORM para persistencia JPA |
| **Tomcat** | Embedido | Servidor web embebido |
| **Jackson** | Runtime | Serialización JSON |

---

## 📁 Estructura del Proyecto

```
proyectoMaven/
├── pom.xml                          # Configuración Maven + dependencias
├── README.md                        # Este archivo (documentación y contexto)
├── src/
│   ├── main/
│   │   ├── java/com/
│   │   │   └── example/
│   │   │       ├── demo/
│   │   │       │   └── DemoApplication.java      # Clase principal
│   │   │       └── proyectoMaven/
│   │   │           ├── model/
│   │   │           │   ├── Usuario.java          # Entidad JPA Usuario
│   │   │           │   └── Estado.java           # Entidad JPA Estado
│   │   │           ├── repository/
│   │   │           │   └── UsuarioRepository.java # Repository JPA
│   │   │           ├── service/
│   │   │           │   └── UsuarioService.java   # Service con lógica
│   │   │           └── controller/
│   │   │               └── UsuarioController.java # REST Controller
│   │   └── resources/
│   │       ├── application.properties             # Configuración
│   │       └── import.sql                         # Datos de prueba
│   └── test/                        # Tests (pendiente)
└── target/                          # Build output (generado por Maven)
```

---

## ⚙️ Configuración Actual

### application.properties

```properties
# H2 Database - En memoria (se pierde al reiniciar)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop

# H2 Console - Acceso web a la BD
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Servidor
server.port=8080
```

### import.sql - Datos de Prueba

Se ejecuta automáticamente al iniciar la aplicación:

```sql
-- Estados
INSERT INTO ESTADOS (ID, ESTADO, CATEGORIA) VALUES (1, 'activo', 'usuario');
INSERT INTO ESTADOS (ID, ESTADO, CATEGORIA) VALUES (2, 'inactivo', 'usuario');

-- Usuarios de prueba
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) 
VALUES (1, 'usuario1', 'Usuario Uno', 'usuario1@test.com', 'usuario1', 1);
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) 
VALUES (2, 'usuario2', 'Usuario Dos', 'usuario2@test.com', 'usuario2', 2);
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) 
VALUES (3, 'usuario3', 'Usuario Tres', 'usuario3@test.com', 'usuario3', 1);
```

---

## 📦 Modelos de Datos

### Entidad: Estado

Representa los estados posibles de un usuario (activo/inactivo).

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | Long | ID auto-generado |
| `estado` | String | Nombre del estado ("activo", "inactivo") |
| `categoria` | String | Categoría del estado ("usuario") |

**Tabla:** `estados`

### Entidad: Usuario

Representa un usuario del sistema con relación a Estado.

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | Long | ID auto-generado |
| `usuario` | String | Nombre de usuario (único) |
| `nombre` | String | Nombre completo |
| `correo` | String | Email (único) |
| `password` | String | Contraseña (en texto plano por ahora) |
| `estado` | Estado | Relación ManyToOne a Estado |

**Tabla:** `usuarios`  
**Relación:** `@ManyToOne(fetch = FetchType.LAZY)` con Estado

---

## 🌐 Endpoints REST Disponibles

### 1. GET /usuarios - Listar todos los usuarios

**Descripción:** Retorna lista completa de usuarios con sus estados.

**Respuesta exitosa (200):**
```json
{
    "total": 3,
    "data": [
        {
            "id": 1,
            "usuario": "usuario1",
            "nombre": "Usuario Uno",
            "correo": "usuario1@test.com",
            "password": "***",
            "estado": {
                "id": 1,
                "estado": "activo",
                "categoria": "usuario"
            }
        }
    ],
    "success": true,
    "mensaje": "Usuarios listados exitosamente"
}
```

---

### 2. POST /usuarios/{id} - Obtener usuario por ID

**Descripción:** Retorna un usuario específico por su ID.

**Parámetros:**
- `id` (path) - ID del usuario

**Respuesta exitosa (200):**
```json
{
    "data": {
        "id": 1,
        "usuario": "usuario1",
        "nombre": "Usuario Uno",
        "correo": "usuario1@test.com",
        "password": "***",
        "estado": {
            "id": 1,
            "estado": "activo",
            "categoria": "usuario"
        }
    },
    "success": true,
    "mensaje": "Usuario encontrado"
}
```

**Respuesta error (404):**
```json
{
    "success": false,
    "mensaje": "No se encontró un usuario con el ID: 999"
}
```

---

### 3. POST /usuarios/editar/{id} - Editar usuario

**Descripción:** Actualiza los datos de un usuario existente.

**Parámetros:**
- `id` (path) - ID del usuario a editar

**Body (JSON):**
```json
{
    "nombre": "Nuevo Nombre",
    "correo": "nuevo@email.com",
    "password": "nuevaPassword",
    "estado": { "id": 2 }
}
```

**Respuesta exitosa (200):**
```json
{
    "data": { ... },
    "success": true,
    "mensaje": "Usuario actualizado exitosamente"
}
```

**Validaciones:**
- ✅ El ID debe existir
- ✅ Usuario y correo deben ser únicos (si se cambian)
- ✅ Campos opcionales: solo se actualizan los proporcionados

---

### 4. POST /usuarios/eliminar/{id} - Eliminar usuario

**Descripción:** Elimina un usuario por su ID.

**Parámetros:**
- `id` (path) - ID del usuario a eliminar

**Respuesta exitosa (200):**
```json
{
    "success": true,
    "mensaje": "Usuario eliminado exitosamente"
}
```

**Respuesta error (404):**
```json
{
    "success": false,
    "mensaje": "No existe un usuario con el ID: 999"
}
```

---

## 🚀 Comandos Útiles

### Compilar proyecto
```bash
cd /Users/danielhumsr/Documents/proyectos/proyectoMaven
mvn clean compile
```

### Empaquetar (crear JAR)
```bash
mvn clean package
```

### Ejecutar aplicación
```bash
mvn spring-boot:run
```

### Ejecutar tests
```bash
mvn test
```

### Acceder a H2 Console (con app corriendo)
1. Inicia la aplicación: `mvn spring-boot:run`
2. Abre navegador: http://localhost:8080/h2-console
3. Credenciales:
   - **JDBC URL:** `jdbc:h2:mem:testdb`
   - **Username:** `sa`
   - **Password:** (vacío)

---

## 🧪 Pruebas de Endpoints (curl)

### Listar todos los usuarios
```bash
curl http://localhost:8080/usuarios
```

### Obtener usuario por ID
```bash
curl -X POST http://localhost:8080/usuarios/1
```

### Editar usuario
```bash
curl -X POST http://localhost:8080/usuarios/editar/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Usuario Actualizado", "correo": "nuevo@test.com"}'
```

### Eliminar usuario
```bash
curl -X POST http://localhost:8080/usuarios/eliminar/2
```

---

## ✅ Verificaciones Realizadas

| Fecha | Verificación | Resultado |
|-------|-------------|-----------|
| 2026-05-01 | Java 17 instalado | ✅ OpenJDK 17.0.19 |
| 2026-05-01 | Maven instalado | ✅ 3.9.15 |
| 2026-05-01 | `mvn compile` | ✅ BUILD SUCCESS |
| 2026-05-01 | `mvn spring-boot:run` | ✅ App inició en ~1s |
| 2026-05-01 | GET /usuarios | ✅ Retorna 3 usuarios |
| 2026-05-01 | POST /usuarios/1 | ✅ Obtiene usuario específico |
| 2026-05-01 | POST /usuarios/editar/1 | ✅ Edita usuario exitosamente |
| 2026-05-01 | POST /usuarios/eliminar/2 | ✅ Elimina usuario exitosamente |
| 2026-05-01 | GET /usuarios (post-eliminación) | ✅ Confirma 2 usuarios restantes |

---

## 📝 Próximos Pasos Sugeridos

### Prioridad Alta (siguiente sesión)
- [ ] **Crear entidad Tarea** - Con relación a Usuario
- [ ] **CRUD de Tareas** - Controller, Service, Repository
- [ ] **Validaciones de negocio** - Ej: usuario activo para crear tareas
- [ ] **Tests unitarios** - JUnit + Mockito para servicios

### Prioridad Media
- [ ] **DTOs** - Separar entidades de objetos de transferencia
- [ ] **Password encriptado** - Usar BCrypt
- [ ] **Autenticación básica** - Spring Security
- [ ] **Swagger/OpenAPI** - Documentación automática de endpoints

### Prioridad Baja
- [ ] **Cambiar a PostgreSQL** - Para persistencia real
- [ ] **Docker** - Contenerizar la aplicación
- [ ] **Lombok** - Reducir boilerplate en entidades
- [ ] **Manejo de excepciones global** - @ControllerAdvice

---

## 🔧 Problemas Conocidos / Notas

1. **Password en texto plano:** Actualmente se almacena sin encriptar. Pendiente implementar BCrypt.

2. **H2 en memoria:** Los datos se pierden al reiniciar la app. Para persistencia temporal en archivo:
   ```properties
   spring.datasource.url=jdbc:h2:file:./data/testdb
   ```

3. **ddl-auto=create-drop:** Elimina tablas al detener la app. Para desarrollo con persistencia:
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   ```

4. **LazyInitializationException:** Las entidades tienen `@JsonIgnoreProperties` para evitar problemas de serialización con Hibernate Lazy Loading.

5. **Configuración de paquetes:** La clase principal usa `@ComponentScan`, `@EnableJpaRepositories` y `@EntityScan` porque los paquetes están fuera del paquete base `com.example.demo`.

---

## 📚 Recursos de Aprendizaje

- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **Spring Initializr:** https://start.spring.io/
- **H2 Database:** http://www.h2database.com/
- **Maven Reference:** https://maven.apache.org/guides/
- **Spring Data JPA:** https://spring.io/projects/spring-data-jpa

---

## 📞 Contexto para Futuras Sesiones con IA

**Si estás leyendo esto en una nueva sesión:**

1. **Ubicación del proyecto:** `/Users/danielhumsr/Documents/proyectos/proyectoMaven/`
2. **Estado:** CRUD de usuarios completo y funcional con datos de prueba
3. **Rama:** `primerPaso` (rama de desarrollo)
4. **Objetivo:** Sistema de gestión de tareas
5. **Preferencias de Daniel:**
   - Siempre pedir autorización antes de crear/editar archivos o ejecutar comandos
   - Explicar claramente qué se va a hacer y por qué
   - Mantener documentación actualizada para contexto

**Para continuar el desarrollo:**
- Revisar sección "Próximos Pasos Sugeridos"
- El siguiente paso natural es crear la entidad `Tarea` con relación a `Usuario`
- Actualizar este README después de cada sesión significativa

**GitHub:** https://github.com/danielhumsr30/prueba-IA

---

*Última actualización: 2026-05-01 por Hope (OpenClaw)*
