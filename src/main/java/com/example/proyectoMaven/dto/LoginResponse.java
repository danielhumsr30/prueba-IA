package com.example.proyectoMaven.dto;

public class LoginResponse {

    private String token;
    private String usuario;
    private boolean success;
    private String mensaje;

    public LoginResponse() {
    }

    public LoginResponse(String token, String usuario, boolean success, String mensaje) {
        this.token = token;
        this.usuario = usuario;
        this.success = success;
        this.mensaje = mensaje;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
