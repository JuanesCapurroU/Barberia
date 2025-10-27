package com.example.Barberia.controllers;

import com.example.Barberia.dto.DashboardEstadisticas;
import com.example.Barberia.dto.EstadisticasBarbero;
import com.example.Barberia.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Obtener estadísticas generales del dashboard
     */
    @GetMapping("/estadisticas")
    public ResponseEntity<DashboardEstadisticas> obtenerEstadisticasGenerales() {
        try {
            DashboardEstadisticas estadisticas = dashboardService.obtenerEstadisticasGenerales();
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtener estadísticas de todos los barberos
     */
    @GetMapping("/barberos")
    public ResponseEntity<List<EstadisticasBarbero>> obtenerEstadisticasBarberos() {
        try {
            List<EstadisticasBarbero> estadisticas = dashboardService.obtenerEstadisticasPorBarbero();
            return ResponseEntity.ok(estadisticas);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtener estadísticas de un barbero específico
     */
    @GetMapping("/barbero/{idBarbero}")
    public ResponseEntity<EstadisticasBarbero> obtenerEstadisticasBarbero(@PathVariable Long idBarbero) {
        try {
            EstadisticasBarbero estadisticas = dashboardService.obtenerEstadisticasBarbero(idBarbero);
            if (estadisticas != null) {
                return ResponseEntity.ok(estadisticas);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

