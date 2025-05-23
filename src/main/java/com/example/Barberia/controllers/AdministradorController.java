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


    @PostMapping
    public Administrador crearAdministrador(@RequestBody Administrador administrador) {
        return administradorService.guardarAdministrador(administrador);
    }


    @GetMapping
    public List<Administrador> listarAdministradores() {
        return administradorService.listarAdministradores();
    }


    @DeleteMapping("/{id}")
    public void eliminarAdministrador(@PathVariable Long id) {
        administradorService.eliminarAdministrador(id);
    }

    @GetMapping("/{id}")
    public Administrador obtenerAdministrador(@PathVariable Long id) {
        return administradorService.obtenerAdministradorPorId(id);
    }

    @PutMapping("/{id}")
    public Administrador actualizarAdministrador(@PathVariable Long id, @RequestBody Administrador admin) {
        admin.setId_admin(id);
        return administradorService.guardarAdministrador(admin);
    }

}