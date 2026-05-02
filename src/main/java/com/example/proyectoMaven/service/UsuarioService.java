package com.example.proyectoMaven.service;

import com.example.proyectoMaven.model.Usuario;
import com.example.proyectoMaven.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos los usuarios
    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Obtener usuario por ID con validación
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }
        return usuarioRepository.findById(id);
    }

    // Validar si existe un usuario por ID
    @Transactional(readOnly = true)
    public boolean existePorId(Long id) {
        return id != null && id > 0 && usuarioRepository.existsById(id);
    }

    // Validar si ya existe un usuario con ese nombre de usuario
    @Transactional(readOnly = true)
    public boolean existePorUsuario(String usuario) {
        return usuario != null && !usuario.trim().isEmpty() 
            && usuarioRepository.findByUsuario(usuario).isPresent();
    }

    // Validar si ya existe un usuario con ese correo
    @Transactional(readOnly = true)
    public boolean existePorCorreo(String correo) {
        return correo != null && !correo.trim().isEmpty() 
            && usuarioRepository.findByCorreo(correo).isPresent();
    }

    // Guardar usuario (crear o actualizar)
    public Usuario guardar(Usuario usuario) {
        // Validaciones para creación
        if (usuario.getId() == null) {
            if (usuario.getUsuario() == null || usuario.getUsuario().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre de usuario es obligatorio");
            }
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre completo es obligatorio");
            }
            if (usuario.getCorreo() == null || usuario.getCorreo().trim().isEmpty()) {
                throw new IllegalArgumentException("El correo es obligatorio");
            }
            if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
                throw new IllegalArgumentException("La contraseña es obligatoria");
            }
            if (usuario.getEstado() == null || usuario.getEstado().getId() == null) {
                throw new IllegalArgumentException("El estado es obligatorio");
            }

            // Validar que no exista el usuario
            if (existePorUsuario(usuario.getUsuario())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese nombre de usuario");
            }

            // Validar que no exista el correo
            if (existePorCorreo(usuario.getCorreo())) {
                throw new IllegalArgumentException("Ya existe un usuario con ese correo");
            }
        }

        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario por ID
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID del usuario debe ser mayor a 0");
        }

        if (!existePorId(id)) {
            throw new IllegalArgumentException("No existe un usuario con el ID: " + id);
        }

        usuarioRepository.deleteById(id);
        return true;
    }
}
