package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.repositories.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdministradorRepository adminRepo;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String usuario = loginData.get("username");
        String password = loginData.get("password");

        Administrador admin = adminRepo.findByUsuario(usuario);
        if (admin != null && admin.getContraseña().equals(password)) {
            // Puedes generar un token JWT aquí si quieres
            return ResponseEntity.ok(Map.of("success", true, "role", "ADMIN"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Credenciales incorrectas"));
        }
    }
}

