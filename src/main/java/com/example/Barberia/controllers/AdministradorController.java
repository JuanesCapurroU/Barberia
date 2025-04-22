package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    // Crear nuevo administrador
    @PostMapping
    public Administrador crearAdministrador(@RequestBody Administrador administrador) {
        return administradorService.guardarAdministrador(administrador);
    }

    // Listar todos los administradores
    @GetMapping
    public List<Administrador> listarAdministradores() {
        return administradorService.listarAdministradores();
    }

    // Eliminar administrador por id
    @DeleteMapping("/{id}")
    public void eliminarAdministrador(@PathVariable Long id) {
        administradorService.eliminarAdministrador(id);
    }

    @GetMapping("/{id}")
    public Administrador obtenerAdministrador(@PathVariable Long id) {
        return administradorService.obtenerAdministradorPorId(id);
    }
}