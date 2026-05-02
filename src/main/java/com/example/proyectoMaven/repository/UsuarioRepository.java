package com.example.proyectoMaven.repository;

import com.example.proyectoMaven.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por nombre de usuario
    Optional<Usuario> findByUsuario(String usuario);

    // Buscar usuario por correo
    Optional<Usuario> findByCorreo(String correo);

    // Listar usuarios por estado
    List<Usuario> findByEstadoId(Long estadoId);
}
