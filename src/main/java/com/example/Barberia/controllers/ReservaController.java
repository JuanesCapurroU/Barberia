package com.example.Barberia.controllers;

import com.example.Barberia.models.Reserva;
import com.example.Barberia.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaServicio;

    @PostMapping
    public Reserva guardarReserva(@RequestBody Reserva reserva) {
        return reservaServicio.guardarReserva(reserva);
    }

    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaServicio.listarReservas();
    }

    @GetMapping("/{id}")
    public Reserva obtenerReservaPorId(@PathVariable Long id) {
        return reservaServicio.obtenerReservaPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Long id) {
        reservaServicio.eliminarReserva(id);
    }
}
