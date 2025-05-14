package com.example.Barberia.services;

import com.example.Barberia.models.HorarioDisponible;
import java.time.LocalDate;
import java.util.List;

public interface HorarioDisponibleService {
    HorarioDisponible guardarHorarioDisponible(HorarioDisponible horario);
    void eliminarHorarioDisponible(Long id);
    List<HorarioDisponible> listarHorarios();
    List<HorarioDisponible> obtenerPorBarberoId(Long idBarbero);
    List<HorarioDisponible> obtenerPorBarberoYFecha(Long idBarbero, LocalDate fecha);
    void crearHorariosParaDiaYBarbero(Long idBarbero, LocalDate fecha);

}
