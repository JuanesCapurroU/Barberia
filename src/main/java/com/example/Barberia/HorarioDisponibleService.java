package com.example.Barberia;

import java.util.List;

public interface HorarioDisponibleService {
    HorarioDisponible guardarHorarioDisponible(HorarioDisponible horarioDisponible);
    List<HorarioDisponible> listarHorariosDisponibles();
    HorarioDisponible obtenerHorarioDisponiblePorId(Long id);
    void eliminarHorarioDisponible(Long id);
}
