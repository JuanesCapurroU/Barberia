package com.example.Barberia.repositories;

import com.example.Barberia.models.Reserva;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByBarbero_IdBarbero(Long idBarbero);

    // Borra todas las reservas asociadas a un barbero
    @Modifying(clearAutomatically = true)
    @Transactional
    void deleteByBarbero_IdBarbero(Long idBarbero);

    @Transactional
    @Modifying
    @Query("DELETE FROM Reserva r WHERE r.horarioDisponible.barbero.idBarbero = :idBarbero")
    void deleteByHorarioBarberoIdBarbero(@Param("idBarbero") Long idBarbero);

    List<Reserva> findByBarbero_IdBarberoAndHorarioDisponible_FechaAndEstado(
            Long idBarbero, LocalDate fecha, String estado
    );
    List<Reserva> findByBarbero_IdBarberoAndHorarioDisponible_Fecha(Long idBarbero, LocalDate fecha);

    @Query("SELECT SUM(s.precio) FROM Reserva r JOIN r.servicio s WHERE r.barbero.idBarbero = :idBarbero AND r.horarioDisponible.fecha = :fecha AND r.estado = :estado")
    Double sumarPreciosPorBarberoFechaYEstado(@Param("idBarbero") Long idBarbero, @Param("fecha") LocalDate fecha, @Param("estado") String estado);
    ;

    @Query("SELECT r FROM Reserva r WHERE r.barbero.idBarbero = :idBarbero AND r.horarioDisponible.fecha = :fecha AND r.estado = :estado")
    List<Reserva> findByBarberoAndFechaAndEstado(@Param("idBarbero") Long idBarbero, @Param("fecha") LocalDate fecha, @Param("estado") String estado);



}






