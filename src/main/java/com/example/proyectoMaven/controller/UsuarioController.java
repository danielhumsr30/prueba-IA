package com.example.proyectoMaven.controller;

import com.example.proyectoMaven.model.Usuario;
import com.example.proyectoMaven.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /usuarios - Listar todos los usuarios
    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarTodos();
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("mensaje", "Usuarios listados exitosamente");
            response.put("data", usuarios);
            response.put("total", usuarios.size());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", "Error al listar usuarios: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // POST /usuarios/{id} - Obtener usuario específico
    @PostMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("mensaje", "El ID del usuario debe ser mayor a 0");
                return ResponseEntity.badRequest().body(error);
            }

            return usuarioService.obtenerPorId(id)
                .map(usuario -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("mensaje", "Usuario encontrado");
                    response.put("data", usuario);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, Object> error = new HashMap<>();
                    error.put("success", false);
                    error.put("mensaje", "No se encontró un usuario con el ID: " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
                });
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", "Error al obtener usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // POST /usuarios/editar/{id} - Editar usuario
    @PostMapping("/editar/{id}")
    public ResponseEntity<?> editarUsuario(
            @PathVariable Long id,
            @RequestBody Usuario usuarioActualizado) {
        try {
            // Validar que el ID sea válido
            if (id == null || id <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("mensaje", "El ID del usuario debe ser mayor a 0");
                return ResponseEntity.badRequest().body(error);
            }

            // Validar que el usuario exista
            if (!usuarioService.existePorId(id)) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("mensaje", "No se encontró un usuario con el ID: " + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }

            // Obtener el usuario existente
            Usuario usuarioExistente = usuarioService.obtenerPorId(id).get();

            // Actualizar campos (solo si se proporcionan)
            if (usuarioActualizado.getUsuario() != null && !usuarioActualizado.getUsuario().trim().isEmpty()) {
                // Validar que el nuevo usuario no exista (si es diferente al actual)
                if (!usuarioExistente.getUsuario().equals(usuarioActualizado.getUsuario()) 
                    && usuarioService.existePorUsuario(usuarioActualizado.getUsuario())) {
                    Map<String, Object> error = new HashMap<>();
                    error.put("success", false);
                    error.put("mensaje", "Ya existe un usuario con ese nombre de usuario");
                    return ResponseEntity.badRequest().body(error);
                }
                usuarioExistente.setUsuario(usuarioActualizado.getUsuario());
            }

            if (usuarioActualizado.getNombre() != null && !usuarioActualizado.getNombre().trim().isEmpty()) {
                usuarioExistente.setNombre(usuarioActualizado.getNombre());
            }

            if (usuarioActualizado.getCorreo() != null && !usuarioActualizado.getCorreo().trim().isEmpty()) {
                // Validar que el nuevo correo no exista (si es diferente al actual)
                if (!usuarioExistente.getCorreo().equals(usuarioActualizado.getCorreo()) 
                    && usuarioService.existePorCorreo(usuarioActualizado.getCorreo())) {
                    Map<String, Object> error = new HashMap<>();
                    error.put("success", false);
                    error.put("mensaje", "Ya existe un usuario con ese correo");
                    return ResponseEntity.badRequest().body(error);
                }
                usuarioExistente.setCorreo(usuarioActualizado.getCorreo());
            }

            if (usuarioActualizado.getPassword() != null && !usuarioActualizado.getPassword().trim().isEmpty()) {
                usuarioExistente.setPassword(usuarioActualizado.getPassword());
            }

            if (usuarioActualizado.getEstado() != null && usuarioActualizado.getEstado().getId() != null) {
                usuarioExistente.setEstado(usuarioActualizado.getEstado());
            }

            // Guardar cambios
            Usuario usuarioGuardado = usuarioService.guardar(usuarioExistente);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("mensaje", "Usuario actualizado exitosamente");
            response.put("data", usuarioGuardado);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", "Error al editar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    // POST /usuarios/eliminar/{id} - Eliminar usuario
    @PostMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        try {
            // Validar que el ID sea válido
            if (id == null || id <= 0) {
                Map<String, Object> error = new HashMap<>();
                error.put("success", false);
                error.put("mensaje", "El ID del usuario debe ser mayor a 0");
                return ResponseEntity.badRequest().body(error);
            }

            // Eliminar usuario
            usuarioService.eliminar(id);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("mensaje", "Usuario eliminado exitosamente");

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("mensaje", "Error al eliminar usuario: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
