# Guía de Desarrollo

Este documento contiene información técnica detallada para desarrolladores que trabajan en este proyecto.

---

## 🔧 Configuración del Entorno

### application.properties (Desarrollo)

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

# JWT Configuration
jwt.secret=ClaveSecretaMuySeguraParaJWT2026QueTieneMasDe256BitsDeLongitudParaHS256
jwt.expiration=86400000
```

---

## 🏗️ Arquitectura

### Organización de Paquetes

El proyecto usa dos paquetes base separados, lo que requiere configuración explícita en `DemoApplication.java`:

| Paquete | Contenido |
|---------|-----------|
| `com.example.demo` | Punto de entrada (`DemoApplication`) |
| `com.example.proyectoMaven` | Toda la lógica de negocio |

```java
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.demo", "com.example.proyectoMaven"})
@EnableJpaRepositories(basePackages = "com.example.proyectoMaven.repository")
@EntityScan(basePackages = "com.example.proyectoMaven.model")
public class DemoApplication { ... }
```

### Patrón de Respuestas

Todos los controllers retornan un `Map<String, Object>` con estructura consistente:

```java
{
    "success": true/false,     // boolean
    "mensaje": "...",          // string
    "data": {...},             // object (opcional)
    "total": 3                 // number (solo para listas)
}
```

---

## 🔐 Seguridad - Detalles Técnicos

### Configuración de SecurityConfig

```java
// PasswordEncoder para desarrollo (texto plano)
@Bean
public PasswordEncoder passwordEncoder() {
    return new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }
        
        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    };
}
```

### Migrar a BCrypt (Producción)

1. Cambiar en `SecurityConfig.java`:
```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
```

2. Actualizar `import.sql` con passwords hasheados:
```sql
-- Generar hash con BCrypt (usar generador online)
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) 
VALUES (1, 'usuario1', 'Usuario Uno', 'usuario1@test.com', '$2a$10$...', 1);
```

### Flujo JWT

1. Cliente envía credenciales a `/auth/login`
2. `AuthController` valida contra BD
3. `JwtTokenProvider` genera token con:
   - Subject: username
   - Expiración: 24 horas (86400000 ms)
   - Firma: HS256 con secreto configurado
4. Filtro `JwtAuthenticationFilter` intercepta requests
5. Si token válido → establece `Authentication` en SecurityContext

---

## 🧪 Datos de Prueba (import.sql)

Se ejecuta automáticamente al iniciar (por `spring.jpa.hibernate.ddl-auto=create-drop`):

```sql
-- Estados
INSERT INTO ESTADOS (ID, ESTADO, CATEGORIA) VALUES 
  (1, 'activo', 'usuario'),
  (2, 'inactivo', 'usuario');

-- Usuarios
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) VALUES 
  (1, 'usuario1', 'Usuario Uno', 'usuario1@test.com', 'usuario1', 1),
  (2, 'usuario2', 'Usuario Dos', 'usuario2@test.com', 'usuario2', 2),
  (3, 'usuario3', 'Usuario Tres', 'usuario3@test.com', 'usuario3', 1);
```

---

## ⚠️ Problemas Conocidos y Soluciones

### LazyInitializationException

**Problema:** Al serializar entidades con relaciones lazy.

**Solución actual:** `@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})` en entidades.

**Alternativa:** Usar DTOs o `@Transactional` en el controller.

### Pérdida de Datos al Reiniciar

**Causa:** H2 en memoria + `ddl-auto=create-drop`

**Para persistencia temporal:**
```properties
spring.datasource.url=jdbc:h2:file:./data/testdb
spring.jpa.hibernate.ddl-auto=update
```

### CORS en Producción

**Actual:** `allowedOrigins = "*"`

**Para producción:**
```java
.cors(cors -> cors.configurationSource(request -> {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("https://tudominio.com"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(List.of("*"));
    return config;
}))
```

---

## 📝 Checklist para Producción

- [ ] Cambiar `PasswordEncoder` a BCrypt
- [ ] Mover JWT secret a variable de entorno
- [ ] Restringir CORS a dominios específicos
- [ ] Cambiar H2 a PostgreSQL/MySQL
- [ ] Configurar `ddl-auto=validate`
- [ ] Habilitar HTTPS
- [ ] Configurar logging apropiado
- [ ] Agregar rate limiting
- [ ] Revisar permisos de base de datos

---

## 🔗 Recursos

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JJWT Documentation](https://github.com/jwtk/jjwt#jwt)
- [H2 Database Engine](http://www.h2database.com/html/main.html)
