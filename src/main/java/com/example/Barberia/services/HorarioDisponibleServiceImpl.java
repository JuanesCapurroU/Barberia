package com.example.Barberia.services;

import com.example.Barberia.models.Barbero;
import com.example.Barberia.models.HorarioDisponible;
import com.example.Barberia.repositories.BarberoRepository;
import com.example.Barberia.repositories.HorarioDisponibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class HorarioDisponibleServiceImpl implements HorarioDisponibleService {

    @Autowired
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @Autowired
    private BarberoRepository barberoRepository;

    @Override
    public HorarioDisponible guardarHorarioDisponible(HorarioDisponible horario) {
        return horarioDisponibleRepository.save(horario);
    }

    @Override
    public void eliminarHorarioDisponible(Long id) {
        horarioDisponibleRepository.deleteById(id);
    }

    @Override
    public List<HorarioDisponible> listarHorarios() {
        return horarioDisponibleRepository.findAll();
    }

    @Override
    public List<HorarioDisponible> obtenerPorBarberoId(Long idBarbero) {
        return horarioDisponibleRepository.findByBarbero_IdBarbero(idBarbero);
    }

    @Override
    public List<HorarioDisponible> obtenerPorBarberoYFecha(Long idBarbero, LocalDate fecha) {
        return horarioDisponibleRepository.findByBarbero_IdBarberoAndFecha(idBarbero, fecha);
    }


    @Override
    public void crearHorariosParaDiaYBarbero(Long idBarbero, LocalDate fecha) {
        Barbero barbero = barberoRepository.findById(idBarbero)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado"));
        LocalTime inicio = LocalTime.of(9, 0);
        LocalTime fin = LocalTime.of(18, 0);
        int intervaloMin = 60;
        LocalTime hora = inicio;
        while (!hora.isAfter(fin.minusMinutes(intervaloMin))) {
            boolean existe = horarioDisponibleRepository
                    .existsByBarberoAndFechaAndHoraInicio(barbero, fecha, hora);
            if (!existe) {
                HorarioDisponible horario = new HorarioDisponible();
                horario.setBarbero(barbero);
                horario.setFecha(fecha);
                horario.setHoraInicio(hora);
                horario.setHoraFin(hora.plusMinutes(intervaloMin));
                horario.setDisponible(true);
                horarioDisponibleRepository.save(horario);
            }
            hora = hora.plusMinutes(intervaloMin);
        }
    }
}
