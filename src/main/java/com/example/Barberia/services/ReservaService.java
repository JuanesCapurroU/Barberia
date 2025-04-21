package com.example.Barberia.services;

import com.example.Barberia.models.Reserva;
import java.util.List;

public interface ReservaService {
    Reserva guardarReserva(Reserva reserva);
    List<Reserva> listarReservas();
    Reserva obtenerReservaPorId(Long id);
    void eliminarReserva(Long id);
}