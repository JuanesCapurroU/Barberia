package com.example.Barberia.repositories;


import com.example.Barberia.models.Barbero;
import com.example.Barberia.models.HorarioDisponible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface HorarioDisponibleRepository extends JpaRepository<HorarioDisponible, Long> {
    List<HorarioDisponible> findByBarbero_IdBarbero(Long idBarbero);
    List<HorarioDisponible> findByBarbero_IdBarberoAndFecha(Long idBarbero, LocalDate fecha);
    boolean existsByBarberoAndFechaAndHoraInicio(Barbero barbero, LocalDate fecha, LocalTime horaInicio);
    List<HorarioDisponible> findByBarbero_IdBarberoAndFechaAndDisponibleTrue(Long idBarbero, LocalDate fecha);
}
