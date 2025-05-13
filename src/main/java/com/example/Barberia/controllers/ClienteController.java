package com.example.Barberia.controllers;

import com.example.Barberia.models.Cliente;
import com.example.Barberia.services.ClienteService;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.models.Administrador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/clientes")
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


    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        return clienteService.guardarCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable Long id, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        clienteService.eliminarCliente(id);
    }
}
