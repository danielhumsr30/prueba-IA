package com.example.proyectoMaven.controller;

import com.example.proyectoMaven.dto.LoginRequest;
import com.example.proyectoMaven.dto.LoginResponse;
import com.example.proyectoMaven.security.JwtTokenProvider;
import com.example.proyectoMaven.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                         JwtTokenProvider jwtTokenProvider,
                         UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (loginRequest.getUsuario() == null || loginRequest.getUsuario().trim().isEmpty()) {
                return createErrorResponse("El nombre de usuario es obligatorio", 400);
            }

            if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().isEmpty()) {
                return createErrorResponse("La contraseña es obligatoria", 400);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsuario(),
                            loginRequest.getPassword()
                    )
            );

            String token = jwtTokenProvider.generateToken(authentication);
            String username = loginRequest.getUsuario();

            LoginResponse response = new LoginResponse(token, username, true, "Autenticación exitosa");
            return ResponseEntity.ok(response);

        } catch (org.springframework.security.core.AuthenticationException e) {
            return createErrorResponse("Credenciales inválidas", 401);
        } catch (Exception e) {
            return createErrorResponse("Error al autenticar: " + e.getMessage(), 500);
        }
    }

    private ResponseEntity<Map<String, Object>> createErrorResponse(String message, int status) {
        Map<String, Object> error = new HashMap<>();
        error.put("success", false);
        error.put("mensaje", message);
        return ResponseEntity.status(status).body(error);
    }
}
