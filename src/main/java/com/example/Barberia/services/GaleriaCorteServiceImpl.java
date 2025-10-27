package com.example.Barberia.services;

import com.example.Barberia.models.GaleriaCorte;
import com.example.Barberia.repositories.GaleriaCorteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class GaleriaCorteServiceImpl implements GaleriaCorteService {

    @Autowired
    private GaleriaCorteRepository galeriaCorteRepository;

    @Override
    @Transactional
    public GaleriaCorte guardarFoto(GaleriaCorte galeriaCorte) {
        // Asignar fecha actual si no se proporciona
        if (galeriaCorte.getFechaSubida() == null) {
            String fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            galeriaCorte.setFechaSubida(fechaActual);
        }
        return galeriaCorteRepository.save(galeriaCorte);
    }

    @Override
    public List<GaleriaCorte> obtenerFotosPorBarbero(Long idBarbero) {
        return galeriaCorteRepository.findByBarbero_IdBarbero(idBarbero);
    }

    @Override
    @Transactional
    public void eliminarFoto(Long idGaleria) {
        galeriaCorteRepository.deleteById(idGaleria);
    }

    @Override
    public GaleriaCorte obtenerFotoPorId(Long idGaleria) {
        return galeriaCorteRepository.findById(idGaleria).orElse(null);
    }
}

