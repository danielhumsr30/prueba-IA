# Demo - Spring Boot Application

Proyecto base de Spring Boot para aprendizaje y desarrollo de aplicaciones Java con persistencia JPA.

---

## 📋 Estado del Proyecto

| Estado | Versión | Última actualización |
|--------|---------|---------------------|
| ✅ Funcional | 0.0.1-SNAPSHOT | 2026-05-01 |

**Última sesión:** Se creó proyecto base, compiló exitosamente y se verificó levantamiento de la aplicación con H2 Database.

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

---

## 📁 Estructura del Proyecto

```
proyectoMaven/
├── pom.xml                          # Configuración Maven + dependencias
├── README.md                        # Este archivo (documentación y contexto)
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   └── DemoApplication.java # Clase principal (entry point)
│   │   └── resources/
│   │       └── application.properties # Configuración de la app
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
spring.jpa.hibernate.ddl-auto=create-drop  # ⚠️ Crea y elimina tablas al iniciar/detener

# H2 Console - Acceso web a la BD
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Servidor
server.port=8080
```

### Dependencias activas (pom.xml)

- `spring-boot-starter-web` - Web MVC, REST, Tomcat
- `spring-boot-starter-data-jpa` - Persistencia JPA + Hibernate
- `h2` - Base de datos en memoria
- `spring-boot-devtools` - Hot reload en desarrollo
- `spring-boot-starter-test` - Testing (JUnit, Mockito, etc.)

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

## ✅ Verificaciones Realizadas

| Fecha | Verificación | Resultado |
|-------|-------------|-----------|
| 2026-05-01 | Java 17 instalado | ✅ OpenJDK 17.0.19 |
| 2026-05-01 | Maven instalado | ✅ 3.9.15 |
| 2026-05-01 | `mvn compile` | ✅ BUILD SUCCESS (40s) |
| 2026-05-01 | `mvn spring-boot:run` | ✅ App inició en 0.741s |
| 2026-05-01 | H2 Database conectada | ✅ HikariPool activo |
| 2026-05-01 | Tomcat en puerto 8080 | ✅ Escuchando HTTP |

---

## 📝 Próximos Pasos Sugeridos

### Prioridad Alta (siguiente sesión)
- [ ] **Crear primer endpoint REST** - Controlador `@RestController` con `@GetMapping`
- [ ] **Crear primera entidad JPA** - Ejemplo: `Usuario` o `Producto`
- [ ] **Crear Repository** - Interface que extiende `JpaRepository`
- [ ] **Agregar datos de prueba** - Usar `CommandLineRunner` o `data.sql`

### Prioridad Media
- [ ] **Validaciones** - Agregar `spring-boot-starter-validation`
- [ ] **DTOs** - Separar entidades de objetos de transferencia
- [ ] **Service Layer** - Mover lógica de negocio a servicios
- [ ] **Tests unitarios** - JUnit + Mockito para servicios

### Prioridad Baja
- [ ] **Cambiar a PostgreSQL/MySQL** - Para persistencia real (reemplazar H2)
- [ ] **Docker** - Contenerizar la aplicación
- [ ] **Swagger/OpenAPI** - Documentación automática de endpoints
- [ ] **Lombok** - Reducir boilerplate en entidades

---

## 🔧 Problemas Conocidos / Notas

1. **H2Dialect warning:** Hibernate reporta que `H2Dialect` no necesita especificarse explícitamente. Se puede remover `spring.jpa.database-platform` del properties.

2. **H2 en memoria:** Los datos se pierden al reiniciar la app. Para persistencia temporal en archivo:
   ```properties
   spring.datasource.url=jdbc:h2:file:./data/testdb
   ```

3. **ddl-auto=create-drop:** Elimina tablas al detener la app. Para desarrollo con persistencia:
   ```properties
   spring.jpa.hibernate.ddl-auto=update
   ```

---

## 📚 Recursos de Aprendizaje

- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **Spring Initializr:** https://start.spring.io/
- **H2 Database:** http://www.h2database.com/
- **Maven Reference:** https://maven.apache.org/guides/

---

## 📞 Contexto para Futuras Sesiones con IA

**Si estás leyendo esto en una nueva sesión:**

1. **Ubicación del proyecto:** `/Users/danielhumsr/Documents/proyectos/proyectoMaven/`
2. **Estado:** Proyecto base funcional, sin endpoints ni entidades personalizadas
3. **Objetivo:** Aprender Spring Boot progresivamente con Daniel
4. **Próximo paso natural:** Crear primer endpoint REST + entidad JPA
5. **Preferencias de Daniel:**
   - Siempre pedir autorización antes de crear/editar archivos o ejecutar comandos
   - Explicar claramente qué se va a hacer y por qué
   - Mantener documentación actualizada para contexto

**Para continuar el desarrollo:**
- Revisar sección "Próximos Pasos Sugeridos"
- Preguntar a Daniel qué funcionalidad quiere implementar
- Actualizar este README después de cada sesión significativa

---

*Última actualización: 2026-05-01 por Hope (OpenClaw)*
