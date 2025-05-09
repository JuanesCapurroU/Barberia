package com.example.Barberia.services;

import com.example.Barberia.repositories.ServicioRepository;
import com.example.Barberia.models.Servicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Override
    public Servicio guardarServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public void eliminarServicio(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado");
        }
        servicioRepository.deleteById(id);
    }

    @Override
    public List<Servicio> obtenerTodosLosServicios() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio obtenerServicioPorId(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servicio no encontrado"));
    }
}
