package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Reserva;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private AdministradorService administradorService;

    private void validarAdministrador(Long idAdministrador) {
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRol())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.");
        }
    }

    @PostMapping
    public Reserva guardarReserva(
            @RequestBody Reserva reserva,
            @RequestParam Long idAdministrador
    ) {
        validarAdministrador(idAdministrador);
        return reservaService.guardarReserva(reserva);
    }

    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Long id, @RequestParam Long idAdministrador) {
        validarAdministrador(idAdministrador);
        reservaService.eliminarReserva(id);
    }
    @GetMapping("/barbero/{idBarbero}")
    public List<Reserva> listarReservasPorBarbero(@PathVariable Long idBarbero) {
        return reservaService.listarReservasPorBarbero(idBarbero);
    }
    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    @PatchMapping("/{id}/estado")
    public Reserva actualizarEstadoReserva(
            @PathVariable Long id,
            @RequestParam String estado,
            @RequestParam Long idAdministrador // o idBarbero si lo prefieres
    ) {
        validarAdministrador(idAdministrador); // O puedes validar barbero si lo permites
        return reservaService.actualizarEstadoReserva(id, estado);
    }

    @GetMapping("/barbero/{idBarbero}/fecha")
    public List<Reserva> reservasPorBarberoYFecha(
            @PathVariable Long idBarbero,
            @RequestParam String fecha, // formato: "2024-05-20"
            @RequestParam(required = false) String estado // opcional
    ) {
        LocalDate localDate = LocalDate.parse(fecha);
        if (estado != null) {
            return reservaService.buscarPorBarberoFechaYEstado(idBarbero, localDate, estado);
        } else {
            return reservaService.buscarPorBarberoYFecha(idBarbero, localDate);
        }
    }
    // Método 1: Total diario (sin estado)
    @GetMapping("/barbero/{idBarbero}/total")
    public Double obtenerTotalDiario(
            @PathVariable Long idBarbero,
            @RequestParam String fecha
    ) {
        return reservaService.calcularTotalDiario(idBarbero, LocalDate.parse(fecha));
    }

    // Método 2: Total diario por estado
    @GetMapping("/barbero/{idBarbero}/total/por-estado")
    public Double obtenerTotalDiarioPorEstado(
            @PathVariable Long idBarbero,
            @RequestParam String fecha,
            @RequestParam String estado
    ) {
        return reservaService.calcularTotalDiarioPorEstado(idBarbero, LocalDate.parse(fecha), estado);
    }



}