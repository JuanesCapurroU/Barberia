package com.example.Barberia.services;

import com.example.Barberia.dto.DashboardEstadisticas;
import com.example.Barberia.dto.EstadisticasBarbero;

import java.util.List;

public interface DashboardService {
    
    DashboardEstadisticas obtenerEstadisticasGenerales();
    
    List<EstadisticasBarbero> obtenerEstadisticasPorBarbero();
    
    EstadisticasBarbero obtenerEstadisticasBarbero(Long idBarbero);
}

