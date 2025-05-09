package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Servicio;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private AdministradorService administradorService;

    private void validarAdministrador(Long idAdministrador) {
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null || !"ADMIN".equals(admin.getRol())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.");
        }
    }

    @PostMapping
    public Servicio guardarServicio(@RequestBody Servicio servicio, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        return servicioService.guardarServicio(servicio);
    }

    @DeleteMapping("/{id}")
    public void eliminarServicio(@PathVariable Long id, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        servicioService.eliminarServicio(id);
    }

    @GetMapping
    public List<Servicio> obtenerServicios() {
        return servicioService.obtenerTodosLosServicios();
    }

    @GetMapping("/{id}")
    public Servicio obtenerServicioPorId(@PathVariable Long id) {
        return servicioService.obtenerServicioPorId(id);
    }

}