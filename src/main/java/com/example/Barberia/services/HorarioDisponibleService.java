package com.example.Barberia.services;

import com.example.Barberia.models.HorarioDisponible;

import java.util.List;

public interface HorarioDisponibleService {
    HorarioDisponible guardarHorarioDisponible(HorarioDisponible horarioDisponible);
    List<HorarioDisponible> listarHorariosDisponibles();
    HorarioDisponible obtenerHorarioDisponiblePorId(Long id);
    void eliminarHorarioDisponible(Long id);
}
