package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Barbero;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.BarberoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/barberos")
public class BarberoController {

    @Autowired
    private BarberoService barberoService;

    @Autowired
    private AdministradorService administradorService;

    private void validarAdministrador(Long idAdministrador) {
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null || !"ADMIN".equals(admin.getRol())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.");
        }
    }

    @PostMapping
    public Barbero guardarBarbero(@RequestBody Barbero barbero, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        return barberoService.guardarBarbero(barbero);
    }

    @DeleteMapping("/{id}")
    public void eliminarBarbero(@PathVariable Long id, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        barberoService.eliminarBarbero(id);
    }
}