package com.example.Barberia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorarioDisponibleServicioImpl implements HorarioDisponibleServicio {

    @Autowired
    private HorarioDisponibleRepositorio horarioDisponibleRepositorio;

    @Override
    public HorarioDisponible guardarHorarioDisponible(HorarioDisponible horarioDisponible) {
        return horarioDisponibleRepositorio.save(horarioDisponible);
    }

    @Override
    public List<HorarioDisponible> listarHorariosDisponibles() {
        return horarioDisponibleRepositorio.findAll();
    }

    @Override
    public HorarioDisponible obtenerHorarioDisponiblePorId(Long id) {
        return horarioDisponibleRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado con id: " + id));
    }

    @Override
    public void eliminarHorarioDisponible(Long id) {
        horarioDisponibleRepositorio.deleteById(id);
    }
}
