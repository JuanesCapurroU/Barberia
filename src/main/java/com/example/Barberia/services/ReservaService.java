package com.example.Barberia.services;

import com.example.Barberia.models.Reserva;
import java.util.List;

public interface ReservaService {
    Reserva guardarReserva(Reserva reserva);
    void eliminarReserva(Long id);
    List<Reserva> listarReservas();
}