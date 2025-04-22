package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.HorarioDisponible;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/horarios")
public class HorarioDisponibleController {

    @Autowired
    private HorarioDisponibleService horarioDisponibleService;

    @Autowired
    private AdministradorService administradorService;

    private void validarAdministrador(Long idAdministrador) {
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null || !"ADMIN".equals(admin.getRol())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.");
        }
    }

    @PostMapping
    public HorarioDisponible guardarHorario(@RequestBody HorarioDisponible horario, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        return horarioDisponibleService.guardarHorarioDisponible(horario);
    }

    @DeleteMapping("/{id}")
    public void eliminarHorario(@PathVariable Long id, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        horarioDisponibleService.eliminarHorarioDisponible(id);
    }
}