package com.example.Barberia.services;

import com.example.Barberia.models.GaleriaCorte;

import java.util.List;

public interface GaleriaCorteService {
    
    // Guardar una nueva foto en la galería
    GaleriaCorte guardarFoto(GaleriaCorte galeriaCorte);
    
    // Obtener todas las fotos de un barbero
    List<GaleriaCorte> obtenerFotosPorBarbero(Long idBarbero);
    
    // Eliminar una foto específica
    void eliminarFoto(Long idGaleria);
    
    // Obtener una foto por ID
    GaleriaCorte obtenerFotoPorId(Long idGaleria);
}

