package com.example.Barberia.services;

import com.example.Barberia.models.HorarioDisponible;

import java.util.List;

public interface HorarioDisponibleService {
    HorarioDisponible guardarHorarioDisponible(HorarioDisponible horario);
    void eliminarHorarioDisponible(Long id);
    List<HorarioDisponible> listarHorarios();
}