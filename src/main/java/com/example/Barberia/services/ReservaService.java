package com.example.Barberia.services;

import com.example.Barberia.models.Reserva;
import com.example.Barberia.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    Reserva guardarReserva(Reserva reserva);
    void eliminarReserva(Long id);
    List<Reserva> listarReservas();
    List<Reserva> listarReservasPorBarbero(Long idBarbero);
    Reserva obtenerReservaPorId(Long id);
    Reserva actualizarEstadoReserva(Long id, String estado);
    List<Reserva> buscarPorBarberoFechaYEstado(Long idBarbero, LocalDate fecha, String estado);
    List<Reserva> buscarPorBarberoYFecha(Long idBarbero, LocalDate fecha);
    Double calcularTotalDiario(Long idBarbero, LocalDate fecha);
    Double calcularTotalDiarioPorEstado(Long idBarbero, LocalDate fecha, String estado);




}