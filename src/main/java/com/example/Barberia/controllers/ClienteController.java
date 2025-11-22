package com.example.Barberia.controllers;

import com.example.Barberia.models.Cliente;
import com.example.Barberia.models.Cupon;
import com.example.Barberia.services.ClienteService;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.CuponService;
import com.example.Barberia.models.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AdministradorService administradorService;
    
    @Autowired
    private com.example.Barberia.utils.JwtUtil jwtUtil;

    @Autowired
    private CuponService cuponService;

    private void validarAdministrador(Long idAdministrador) {
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRol())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.");
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes(@RequestParam(required = false) Long idAdministrador) {
        if (idAdministrador != null) {
            validarAdministrador(idAdministrador);
        }
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/perfil")
    public ResponseEntity<?> obtenerPerfil(Authentication authentication) {
        try {
            String correo = authentication.getName();
            Optional<Cliente> clienteOpt = clienteService.findByCorreo(correo);
            
            if (clienteOpt.isPresent()) {
                Cliente cliente = clienteOpt.get();
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "cliente", Map.of(
                        "id", cliente.getId_cliente(),
                        "nombre", cliente.getNombre(),
                        "celular", cliente.getCelular(),
                        "correo", cliente.getCorreo(),
                        "direccion", cliente.getDireccion()
                    )
                ));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "Cliente no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error interno del servidor"));
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(
        @RequestBody Cliente cliente,
        @RequestParam Long idAdministrador
    ) {
        validarAdministrador(idAdministrador);
        Cliente clienteGuardado = clienteService.guardarCliente(cliente, idAdministrador);
        return ResponseEntity.ok(clienteGuardado);
    }
    
    @PostMapping("/simple")
    public ResponseEntity<?> crearClienteSimple(@RequestBody Cliente cliente) {
        try {
            Cliente clienteGuardado = clienteService.guardarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "message", "Cliente creado exitosamente",
                "cliente", Map.of(
                    "id", clienteGuardado.getId_cliente(),
                    "nombre", clienteGuardado.getNombre(),
                    "celular", clienteGuardado.getCelular(),
                    "correo", clienteGuardado.getCorreo(),
                    "direccion", clienteGuardado.getDireccion() != null ? clienteGuardado.getDireccion() : ""
                )
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", "Error al crear cliente: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        clienteService.eliminarCliente(id);
    }
    
    @PutMapping("/perfil")
    public ResponseEntity<?> actualizarPerfil(
            Authentication authentication,
            @RequestBody Map<String, String> datosPerfil
    ) {
        try {
            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "No autenticado. Por favor, inicia sesión nuevamente"));
            }
            
            String correo = authentication.getName();
            String nombre = datosPerfil.get("nombre");
            String celular = datosPerfil.get("celular");
            String direccion = datosPerfil.get("direccion");
            
            Cliente clienteActualizado = clienteService.actualizarPerfil(correo, nombre, celular, direccion);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Perfil actualizado exitosamente",
                "cliente", Map.of(
                    "id", clienteActualizado.getId_cliente(),
                    "nombre", clienteActualizado.getNombre(),
                    "celular", clienteActualizado.getCelular(),
                    "correo", clienteActualizado.getCorreo(),
                    "direccion", clienteActualizado.getDireccion() != null ? clienteActualizado.getDireccion() : ""
                )
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error interno del servidor"));
        }
    }
    
    @PutMapping("/cambiar-contraseña")
    public ResponseEntity<?> cambiarContraseña(
            Authentication authentication,
            @RequestBody Map<String, String> datosContraseña
    ) {
        try {
            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("success", false, "message", "No autenticado. Por favor, inicia sesión nuevamente"));
            }
            
            String correo = authentication.getName();
            String contraseñaActual = datosContraseña.get("contraseñaActual");
            String nuevaContraseña = datosContraseña.get("nuevaContraseña");
            
            if (contraseñaActual == null || nuevaContraseña == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "Faltan datos requeridos"));
            }
            
            if (nuevaContraseña.length() < 6) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "La nueva contraseña debe tener al menos 6 caracteres"));
            }
            
            boolean cambiado = clienteService.cambiarContraseña(correo, contraseñaActual, nuevaContraseña);
            
            if (cambiado) {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Contraseña cambiada exitosamente"
                ));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false, "message", "No se pudo cambiar la contraseña"));
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error interno del servidor"));
        }
    }

    @GetMapping("/cupones/validar")
    public ResponseEntity<?> validarCupon(@RequestParam String codigo) {
        try {
            Optional<Cupon> cuponOpt = cuponService.validarCupon(codigo);
            
            if (cuponOpt.isPresent()) {
                Cupon cupon = cuponOpt.get();
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "valido", true,
                    "cupon", Map.of(
                        "codigo", cupon.getCodigo(),
                        "porcentajeDescuento", cupon.getPorcentajeDescuento(),
                        "fechaValidez", cupon.getFechaValidez().toString()
                    ),
                    "message", "Cupón válido"
                ));
            } else {
                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "valido", false,
                    "message", "Cupón inválido o expirado"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "Error al validar cupón: " + e.getMessage()));
        }
    }
}
