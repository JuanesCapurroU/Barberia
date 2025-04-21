package com.example.Barberia.controllers;

import com.example.Barberia.models.HorarioDisponible;
import com.example.Barberia.services.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioDisponibleController {

    @Autowired
    private HorarioDisponibleService horarioDisponibleService;

    @PostMapping
    public HorarioDisponible guardarHorarioDisponible(@RequestBody HorarioDisponible horarioDisponible) {
        return horarioDisponibleService.guardarHorarioDisponible(horarioDisponible);
    }

    @GetMapping
    public List<HorarioDisponible> listarHorariosDisponibles() {
        return horarioDisponibleService.listarHorariosDisponibles();
    }

    @GetMapping("/{id}")
    public HorarioDisponible obtenerHorarioDisponiblePorId(@PathVariable Long id) {
        return horarioDisponibleService.obtenerHorarioDisponiblePorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarHorarioDisponible(@PathVariable Long id) {
        horarioDisponibleService.eliminarHorarioDisponible(id);
    }
}
