package com.example.Barberia.repositories;

import com.example.Barberia.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByBarbero_IdBarbero(Long idBarbero);
}