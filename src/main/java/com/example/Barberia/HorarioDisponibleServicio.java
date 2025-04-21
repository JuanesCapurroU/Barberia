package com.example.Barberia;

import java.util.List;

public interface HorarioDisponibleServicio {
    HorarioDisponible guardarHorarioDisponible(HorarioDisponible horarioDisponible);
    List<HorarioDisponible> listarHorariosDisponibles();
    HorarioDisponible obtenerHorarioDisponiblePorId(Long id);
    void eliminarHorarioDisponible(Long id);
}
