package com.example.Barberia.services;

import com.example.Barberia.models.Reserva;
import com.example.Barberia.models.HorarioDisponible;
import com.example.Barberia.repositories.ReservaRepository;
import com.example.Barberia.repositories.HorarioDisponibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @Override
    public Reserva guardarReserva(Reserva reserva) {
        Reserva saved = reservaRepository.save(reserva);

        Long idHorario = reserva.getHorarioDisponible().getIdHorario();
        HorarioDisponible horario = horarioDisponibleRepository.findById(idHorario)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        horario.setDisponible(false);
        horarioDisponibleRepository.save(horario);

        return saved;
    }

    @Override
    public void eliminarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        HorarioDisponible horario = reserva.getHorarioDisponible();
        horario.setDisponible(true);
        horarioDisponibleRepository.save(horario);
        reservaRepository.deleteById(id);
    }


    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> listarReservasPorBarbero(Long idBarbero) {
        return reservaRepository.findByBarbero_IdBarbero(idBarbero);
    }

}

