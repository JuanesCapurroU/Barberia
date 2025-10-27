package com.example.Barberia.repositories;

import com.example.Barberia.models.GaleriaCorte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GaleriaCorteRepository extends JpaRepository<GaleriaCorte, Long> {
    
    // Obtener todas las fotos de un barbero específico
    List<GaleriaCorte> findByBarbero_IdBarbero(Long idBarbero);
    
    // Eliminar todas las fotos de un barbero
    void deleteByBarbero_IdBarbero(Long idBarbero);
}

