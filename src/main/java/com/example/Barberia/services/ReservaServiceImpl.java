package com.example.Barberia.services;
import com.example.Barberia.models.Reserva;
import com.example.Barberia.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepositorio;

    @Override
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    @Override
    public List<Reserva> listarReservas() {
        return reservaRepositorio.findAll();
    }

    @Override
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con id: " + id));
    }

    @Override
    public void eliminarReserva(Long id) {
        reservaRepositorio.deleteById(id);
    }
}