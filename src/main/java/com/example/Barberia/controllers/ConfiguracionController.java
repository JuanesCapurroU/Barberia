package com.example.Barberia.controllers;

import com.example.Barberia.models.Configuracion;
import com.example.Barberia.services.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/configuracion")
public class ConfiguracionController {

    @Autowired
    private ConfiguracionService configuracionService;

    /**
     * Obtener configuración por clave
     */
    @GetMapping("/{clave}")
    public ResponseEntity<Configuracion> obtenerConfiguracion(@PathVariable String clave) {
        try {
            Configuracion config = configuracionService.obtenerPorClave(clave);
            if (config != null) {
                return ResponseEntity.ok(config);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Obtener porcentajes de comisión
     */
    @GetMapping("/comisiones")
    public ResponseEntity<Map<String, Double>> obtenerComisiones() {
        try {
            Double comisionAdmin = configuracionService.obtenerPorcentajeComisionAdmin();
            Double comisionBarbero = configuracionService.obtenerPorcentajeComisionBarbero();

            Map<String, Double> comisiones = new HashMap<>();
            comisiones.put("comisionAdmin", comisionAdmin);
            comisiones.put("comisionBarbero", comisionBarbero);

            return ResponseEntity.ok(comisiones);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Actualizar porcentajes de comisión
     */
    @PutMapping("/comisiones")
    public ResponseEntity<Map<String, String>> actualizarComisiones(
            @RequestBody Map<String, Double> comisiones
    ) {
        try {
            Double comisionAdmin = comisiones.get("comisionAdmin");
            Double comisionBarbero = comisiones.get("comisionBarbero");

            if (comisionAdmin == null || comisionBarbero == null) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Deben proporcionarse ambas comisiones");
                return ResponseEntity.badRequest().body(error);
            }

            if (comisionAdmin + comisionBarbero != 100.0) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Las comisiones deben sumar 100%");
                return ResponseEntity.badRequest().body(error);
            }

            configuracionService.actualizarComisiones(comisionAdmin, comisionBarbero);

            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Comisiones actualizadas correctamente");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Guardar configuración general
     */
    @PostMapping
    public ResponseEntity<Configuracion> guardarConfiguracion(@RequestBody Configuracion configuracion) {
        try {
            Configuracion saved = configuracionService.guardarConfiguracion(configuracion);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

