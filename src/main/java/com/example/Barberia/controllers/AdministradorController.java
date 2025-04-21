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

    @GetMapping
    public List<Administrador> obtenerTodos() {
        return administradorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Administrador obtenerPorId(@PathVariable Long id) {
        return administradorService.obtenerPorId(id);
    }

    @PostMapping
    public Administrador crearAdministrador(@RequestBody Administrador administrador) {
        return administradorService.guardar(administrador);
    }

    @PutMapping("/{id}")
    public Administrador actualizarAdministrador(@PathVariable Long id, @RequestBody Administrador administrador) {
        Administrador existente = administradorService.obtenerPorId(id);
        if (existente != null) {
            existente.setNombre(administrador.getNombre());
            existente.setUsuario(administrador.getUsuario());
            existente.setContrasenia(administrador.getContrasenia());
            existente.setCorreo(administrador.getCorreo());
            return administradorService.guardar(existente);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void eliminarAdministrador(@PathVariable Long id) {
        administradorService.eliminar(id);
    }
}
