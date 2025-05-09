package com.example.Barberia.services;

import com.example.Barberia.repositories.HorarioDisponibleRepository;
import com.example.Barberia.models.HorarioDisponible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HorarioDisponibleServiceImpl implements HorarioDisponibleService {

    @Autowired
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @Override
    public HorarioDisponible guardarHorarioDisponible(HorarioDisponible horario) {
        return horarioDisponibleRepository.save(horario);
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
    public void eliminarHorarioDisponible(Long id) {
        horarioDisponibleRepository.deleteById(id);
    }

    @Override
    public List<HorarioDisponible> listarHorarios() {
        return horarioDisponibleRepository.findAll();
    }
}