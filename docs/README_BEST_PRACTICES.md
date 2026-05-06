# Buenas Prácticas para README.md (2025-2026)

Guía genérica para crear READMEs efectivos en cualquier proyecto.

---

## 📐 Estructura Esencial (Orden Recomendado)

### 1. Título + Tagline

```markdown
# Nombre del Proyecto

> Descripción de una línea que explica qué hace el proyecto.
```

**Reglas:**
- Título = nombre del repositorio
- Tagline en blockquote (`>`), máximo 120 caracteres
- Lenguaje simple, sin jerga técnica

---

### 2. Badges (3-5 máximo)

```markdown
[![Badge](https://img.shields.io/badge/texto-valor-color?logo=icono)](url)
```

**Usar para:** versión, build status, license, coverage, downloads

**Evitar:** badges decorativos ("made with ❤️", "awesome")

---

### 3. Demo Visual (Opcional pero recomendado)

```markdown
![Demo](ruta-a-imagen-o-gif.gif)
```

**Reglas:**
- GIF de ~10 segundos o screenshot
- Máximo 5MB
- Mostrar el proyecto en acción

---

### 4. Descripción Corta

2-3 oraciones que respondan:
- ¿Qué es?
- ¿Para quién es?
- ¿Por qué es diferente/útil?

---

### 5. Quick Start (Obligatorio)

```markdown
## 🚀 Inicio Rápido

### Prerrequisitos

```bash
comando --version
```

### Instalación

```bash
paso 1
paso 2
paso 3
```

### Verificación

```bash
comando de prueba
```
```

**Reglas:**
- Copiar y pegar, debe funcionar
- Máximo 5 minutos para estar corriendo
- Incluir output esperado si es útil

---

### 6. Funcionalidades / Endpoints

**Para listas:**
```markdown
| Feature | Estado | Descripción |
|---------|--------|-------------|
| Feature 1 | ✅ | Descripción breve |
```

**Para APIs:**
```markdown
| Método | Ruta | Auth | Descripción |
|--------|------|------|-------------|
| `GET` | `/resource` | ❌ | Obtener recursos |
```

---

### 7. Ejemplos de Uso

```markdown
<details>
<summary><strong>Ver ejemplos</strong></summary>

### Ejemplo 1: Caso básico

```bash
comando
```

Resultado:
```
output esperado
```

</details>
```

**Reglas:**
- 3-5 ejemplos (simple → complejo)
- Escenarios realistas
- Usar `<details>` para no saturar

---

### 8. Stack Tecnológico (Opcional)

```markdown
| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Herramienta | X.Y | Para qué se usa |
```

---

### 9. Configuración (Opcional)

```markdown
## ⚙️ Configuración

| Variable | Default | Descripción |
|----------|---------|-------------|
| `VAR_NAME` | `valor` | Qué hace |
```

---

### 10. Roadmap (Opcional)

```markdown
## 📌 Próximos Pasos

- [ ] Feature pendiente 1
- [ ] Feature pendiente 2
```

---

### 11. Licencia (Obligatorio, al final)

```markdown
## 📄 Licencia

MIT © [Tu Nombre](url-al-repo)
```

---

## 📊 Métricas Recomendadas

| Métrica | Valor |
|---------|-------|
| Palabras | 200-800 |
| Líneas | 150-300 |
| Tiempo de lectura | 1-2 min |
| Badges | 3-5 máximo |

---

## ✅ Checklist Antes de Publicar

### Contenido
- [ ] Título claro
- [ ] Tagline de 1 línea
- [ ] Badges funcionan (3-5 máx)
- [ ] Comando de instalación visible
- [ ] Ejemplo funcional
- [ ] Licencia especificada

### Usabilidad
- [ ] Se entiende en 10 segundos
- [ ] Quick Start funciona en 5 min
- [ ] Ejemplos realistas
- [ ] Tablas para datos estructurados
- [ ] `<details>` para contenido secundario

### Errores a Evitar
- [ ] Sin comando de instalación
- [ ] Sin demo visual
- [ ] Versiones desactualizadas
- [ ] Sin licencia
- [ ] Demasiado largo (>300 líneas)
- [ ] Jerga técnica excesiva

---

## 🏗️ Archivos Complementarios

Mover contenido detallado a:

| Archivo | Contenido |
|---------|-----------|
| `docs/DEVELOPMENT.md` | Docs técnicas para devs |
| `docs/API.md` | Referencia completa de API |
| `CONTRIBUTING.md` | Cómo contribuir |
| `CHANGELOG.md` | Historial de cambios |
| `SECURITY.md` | Reporte de vulnerabilidades |
| `CODE_OF_CONDUCT.md` | Código de conducta |

---

## 📈 Tendencias Actuales

| Tendencia | Qué significa |
|-----------|---------------|
| **Visual First** | Imagen/GIF arriba del fold |
| **Regla 10s** | Responder qué/por qué/cómo en 10s |
| **Mobile Friendly** | Párrafos cortos, tablas scannables |
| **Mantenimiento** | Actualizar en cada release |

---

## 📝 Plantilla Vacía

```markdown
# Nombre del Proyecto

> Tagline de una línea.

[![Badge](url)]()
[![License](url)](LICENSE)

---

## ¿Qué es?

2-3 oraciones describiendo el proyecto.

---

## 🚀 Inicio Rápido

### Prerrequisitos

```bash
comando --version
```

### Instalación

```bash
paso 1
paso 2
```

---

## Funcionalidades

| Feature | Estado | Descripción |
|---------|--------|-------------|
| Feature | ✅ | Descripción |

---

## 📄 Licencia

MIT © [Tu Nombre](url)
```

---

## 🔗 Recursos

- [GitHub Docs - README](https://docs.github.com/github/creating-cloning-and-archiving-repositories/about-readmes)
- [standard-readme](https://github.com/RichardLitt/standard-readme)
- [Shields.io](https://shields.io/)
- [Keep a Changelog](https://keepachangelog.com/)
