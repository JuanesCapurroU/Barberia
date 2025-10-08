package com.example.Barberia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClienteLoginDto {
    
    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe tener un formato válido")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String contraseña;
    
    public ClienteLoginDto() {}
    
    public ClienteLoginDto(String correo, String contraseña) {
        this.correo = correo;
        this.contraseña = contraseña;
    }
    
    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    public String getContraseña() {
        return contraseña;
    }
    
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}

