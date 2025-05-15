package com.example.Barberia.repositories;


import com.example.Barberia.models.Barbero;
import com.example.Barberia.models.HorarioDisponible;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Transactional
    @Modifying
    void deleteByBarbero_IdBarbero(Long idBarbero);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM HorarioDisponible h WHERE h.barbero.idBarbero = :idBarbero")
    void deleteByBarberoId(@Param("idBarbero") Long idBarbero);

}
