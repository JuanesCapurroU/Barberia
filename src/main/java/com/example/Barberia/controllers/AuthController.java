package com.example.Barberia.controllers;

import com.example.Barberia.dto.ClienteLoginDto;
import com.example.Barberia.dto.ClienteRegistroDto;
import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Cliente;
import com.example.Barberia.repositories.AdministradorRepository;
import com.example.Barberia.services.ClienteService;
import com.example.Barberia.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AdministradorRepository adminRepo;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody Map<String, String> loginData) {
        String usuario = loginData.get("username");
        String password = loginData.get("password");

        Administrador admin = adminRepo.findByUsuario(usuario);
        if (admin != null && admin.getContraseña().equals(password)) {
            String token = jwtUtil.generateToken(usuario, "ADMIN");
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "role", "ADMIN",
                "token", token,
                "message", "Login exitoso"
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Credenciales incorrectas"));
        }
    }
    
    @PostMapping("/cliente/registro")
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody ClienteRegistroDto clienteDto) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre(clienteDto.getNombre());
            cliente.setCelular(clienteDto.getCelular());
            cliente.setCorreo(clienteDto.getCorreo());
            cliente.setContraseña(clienteDto.getContraseña());
            cliente.setDireccion(clienteDto.getDireccion());
            
            Cliente clienteGuardado = clienteService.registrarCliente(cliente);
            
            // Generar token JWT
            String token = jwtUtil.generateToken(clienteDto.getCorreo(), "CLIENTE");
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cliente registrado exitosamente");
            response.put("token", token);
            response.put("cliente", Map.of(
                "id", clienteGuardado.getId_cliente(),
                "nombre", clienteGuardado.getNombre(),
                "celular", clienteGuardado.getCelular(),
                "correo", clienteGuardado.getCorreo(),
                "direccion", clienteGuardado.getDireccion() != null ? clienteGuardado.getDireccion() : "",
                "role", "CLIENTE"
            ));
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error interno del servidor"));
        }
    }
    
    @PostMapping("/cliente/login")
    public ResponseEntity<?> loginCliente(@Valid @RequestBody ClienteLoginDto loginDto) {
        try {
            boolean credencialesValidas = clienteService.validarCredenciales(
                loginDto.getCorreo(), 
                loginDto.getContraseña()
            );
            
            if (credencialesValidas) {
                String token = jwtUtil.generateToken(loginDto.getCorreo(), "CLIENTE");
                
                // Obtener datos del cliente
                var clienteOpt = clienteService.findByCorreo(loginDto.getCorreo());
                if (clienteOpt.isPresent()) {
                    Cliente cliente = clienteOpt.get();
                    
                    Map<String, Object> response = new HashMap<>();
                    response.put("success", true);
                    response.put("message", "Login exitoso");
                    response.put("token", token);
                    response.put("cliente", Map.of(
                        "id", cliente.getId_cliente(),
                        "nombre", cliente.getNombre(),
                        "celular", cliente.getCelular(),
                        "correo", cliente.getCorreo(),
                        "direccion", cliente.getDireccion() != null ? cliente.getDireccion() : "",
                        "role", "CLIENTE"
                    ));
                    
                    return ResponseEntity.ok(response);
                }
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("success", false, "message", "Credenciales incorrectas"));
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error interno del servidor"));
        }
    }
    
    @PostMapping("/cliente/verificar-token")
    public ResponseEntity<?> verificarToken(@RequestHeader("Authorization") String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String username = jwtUtil.getUsernameFromToken(token);
            boolean esValido = jwtUtil.validateToken(token, username);
            
            if (esValido) {
                var clienteOpt = clienteService.findByCorreo(username);
                if (clienteOpt.isPresent()) {
                    Cliente cliente = clienteOpt.get();
                    
                    return ResponseEntity.ok(Map.of(
                        "valid", true,
                        "cliente", Map.of(
                            "id", cliente.getId_cliente(),
                            "nombre", cliente.getNombre(),
                            "celular", cliente.getCelular(),
                            "correo", cliente.getCorreo(),
                            "direccion", cliente.getDireccion() != null ? cliente.getDireccion() : "",
                            "role", "CLIENTE"
                        )
                    ));
                }
            }
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", "Token inválido"));
                    
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("valid", false, "message", "Token inválido"));
        }
    }
}

