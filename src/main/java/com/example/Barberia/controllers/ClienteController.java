package com.example.Barberia.controllers;

import com.example.Barberia.models.Cliente;
import com.example.Barberia.services.ClienteService;
import com.example.Barberia.services.AdministradorService;
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
}
