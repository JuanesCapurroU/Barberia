package com.example.Barberia.controllers;

import com.example.Barberia.models.Barbero;
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

    @PostMapping
    public Barbero guardarBarbero(@RequestBody Barbero barbero, @RequestParam Long idAdministrador) {
        System.out.println("Barbero recibido: " + barbero);
        Barbero nuevoBarbero = barberoServiceImpl.guardarBarbero(barbero);

        // Crea horarios para los próximos 7 días (ajusta según lo que quieras)
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
        // opcional: validar rol de administrador
        barberoServiceImpl.eliminarBarbero(id);
    }


    @GetMapping
    public List<Barbero> obtenerTodosLosBarberos() {
        return barberoServiceImpl.listarBarberos();
    }

    @PutMapping("/{id}")
    public Barbero actualizarBarbero(@PathVariable Long id, @RequestBody Barbero barbero) {
        barbero.setIdBarbero(id);
        return barberoServiceImpl.guardarBarbero(barbero);
    }


}
