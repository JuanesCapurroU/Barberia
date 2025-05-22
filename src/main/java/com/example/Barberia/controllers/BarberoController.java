package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Barbero;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.BarberoServiceImpl;
import com.example.Barberia.services.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/barberos")
public class BarberoController {

    @Autowired
    private BarberoServiceImpl barberoServiceImpl;

    @Autowired
    private HorarioDisponibleService horarioDisponibleService;

    @Autowired
    private AdministradorService administradorService;

    @PostMapping
    public Barbero guardarBarbero(
            @RequestBody Barbero barbero,
            @RequestParam Long idAdministrador
    ) {
        // Buscar el administrador
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null) {
            throw new RuntimeException("Administrador no encontrado con ID: " + idAdministrador);
        }

        // Asociar el administrador al barbero
        barbero.setAdministrador(admin);

        // Guardar el barbero
        Barbero nuevoBarbero = barberoServiceImpl.guardarBarbero(barbero);

        // Crear horarios para los próximos 7 días
        for (int i = 0; i < 7; i++) {
            LocalDate fecha = LocalDate.now().plusDays(i);
            horarioDisponibleService.crearHorariosParaDiaYBarbero(nuevoBarbero.getIdBarbero(), fecha);
        }

        return nuevoBarbero;
    }

    @DeleteMapping("/{id}")
    public void eliminarBarbero(
            @PathVariable Long id,
            @RequestParam Long idAdministrador
    ) {
        // Validar que el administrador existe
        administradorService.obtenerAdministradorPorId(idAdministrador);

        barberoServiceImpl.eliminarBarbero(id);
    }

    @GetMapping
    public List<Barbero> obtenerTodosLosBarberos() {
        return barberoServiceImpl.listarBarberos();
    }
}
