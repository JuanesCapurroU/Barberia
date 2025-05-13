package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.HorarioDisponible;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/horarios")
public class HorarioDisponibleController {

    @Autowired
    private HorarioDisponibleService horarioDisponibleService;

    @Autowired
    private AdministradorService administradorService;

    private void validarAdministrador(Long idAdministrador) {
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRol())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.");
        }
    }

    @GetMapping
    public List<HorarioDisponible> obtenerHorariosPorBarbero(@RequestParam Long idbarbero) {
        return horarioDisponibleService.obtenerPorBarberoId(idbarbero);
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

    @GetMapping("/disponibles")
    public List<LocalTime> obtenerHorariosDisponibles(
            @RequestParam Long idbarbero,
            @RequestParam String fecha // formato "yyyy-MM-dd"
    ) {

        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(18, 0);
        int intervaloMin = 60;
        List<LocalTime> todosLosSlots = new ArrayList<>();
        LocalTime hora = inicio;
        while (!hora.isAfter(fin.minusMinutes(intervaloMin))) {
            todosLosSlots.add(hora);
            hora = hora.plusMinutes(intervaloMin);
        }


        LocalDate fechaConsulta = LocalDate.parse(fecha);
        List<HorarioDisponible> reservados = horarioDisponibleService.obtenerPorBarberoYFecha(idbarbero, fechaConsulta);


        Set<LocalTime> ocupados = reservados.stream()
                .filter(h -> !h.isDisponible()) // solo los ocupados
                .map(HorarioDisponible::getHoraInicio)
                .collect(Collectors.toSet());

        List<LocalTime> libres = todosLosSlots.stream()
                .filter(slot -> !ocupados.contains(slot))
                .collect(Collectors.toList());

        return libres;
    }

}