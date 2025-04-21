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
    public HorarioDisponible guardarHorarioDisponible(HorarioDisponible horarioDisponible) {
        return horarioDisponibleRepository.save(horarioDisponible);
    }

    @Override
    public List<HorarioDisponible> listarHorariosDisponibles() {
        return horarioDisponibleRepository.findAll();
    }

    @Override
    public HorarioDisponible obtenerHorarioDisponiblePorId(Long id) {
        return horarioDisponibleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));
    }

    @Override
    public void eliminarHorarioDisponible(Long id) {
        horarioDisponibleRepository.deleteById(id);
    }
}
