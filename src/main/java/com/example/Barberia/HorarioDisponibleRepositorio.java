package com.example.Barberia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioDisponibleRepositorio extends JpaRepository<HorarioDisponible, Long> {
}
