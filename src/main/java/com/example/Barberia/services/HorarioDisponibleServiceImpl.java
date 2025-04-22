package com.example.Barberia.services;

import com.example.Barberia.repositories.HorarioDisponibleRepository;
import com.example.Barberia.models.HorarioDisponible;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void eliminarHorarioDisponible(Long id) {
        horarioDisponibleRepository.deleteById(id);
    }

    @Override
    public List<HorarioDisponible> listarHorarios() {
        return horarioDisponibleRepository.findAll();
    }
}