package com.example.Barberia.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HorarioSchedulerService implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(HorarioSchedulerService.class);

    @Autowired
    private HorarioDisponibleService horarioDisponibleService;

    /**
     * Se ejecuta al iniciar la aplicación para generar horarios
     * para todos los barberos existentes que no tengan horarios.
     */
    @Override
    public void run(String... args) throws Exception {
        try {
            logger.info("Iniciando generación de horarios al arrancar la aplicación...");
            horarioDisponibleService.generarHorariosFuturosParaTodosLosBarberos();
            logger.info("Generación inicial de horarios completada exitosamente.");
        } catch (Exception e) {
            logger.error("Error al generar horarios al iniciar la aplicación: ", e);
        }
    }

    /**
     * Se ejecuta diariamente a las 2:00 AM para generar horarios futuros
     * para todos los barberos, asegurando que siempre haya horarios disponibles
     * para los próximos 7 días.
     */
    @Scheduled(cron = "0 0 2 * * ?") // Ejecuta todos los días a las 2:00 AM
    public void generarHorariosAutomaticamente() {
        try {
            logger.info("Iniciando generación automática de horarios para todos los barberos...");
            horarioDisponibleService.generarHorariosFuturosParaTodosLosBarberos();
            logger.info("Generación automática de horarios completada exitosamente.");
        } catch (Exception e) {
            logger.error("Error al generar horarios automáticamente: ", e);
        }
    }
}

