package com.example.Barberia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
@CrossOrigin(origins = "*")
public class HorarioDisponibleControlador {

    @Autowired
    private HorarioDisponibleServicio horarioDisponibleServicio;

    @PostMapping
    public HorarioDisponible guardarHorarioDisponible(@RequestBody HorarioDisponible horarioDisponible) {
        return horarioDisponibleServicio.guardarHorarioDisponible(horarioDisponible);
    }

    @GetMapping
    public List<HorarioDisponible> listarHorariosDisponibles() {
        return horarioDisponibleServicio.listarHorariosDisponibles();
    }

    @GetMapping("/{id}")
    public HorarioDisponible obtenerHorarioDisponiblePorId(@PathVariable Long id) {
        return horarioDisponibleServicio.obtenerHorarioDisponiblePorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarHorarioDisponible(@PathVariable Long id) {
        horarioDisponibleServicio.eliminarHorarioDisponible(id);
    }
}
