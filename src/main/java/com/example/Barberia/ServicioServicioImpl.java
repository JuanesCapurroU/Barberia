package com.example.Barberia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServicioImpl implements ServicioServicio {

    @Autowired
    private ServicioRepositorio servicioRepositorio;

    @Override
    public Servicio guardarServicio(Servicio servicio) {
        return servicioRepositorio.save(servicio);
    }

    @Override
    public List<Servicio> listarServicios() {
        return servicioRepositorio.findAll();
    }

    @Override
    public Servicio obtenerServicioPorId(Long id) {
        return servicioRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con id: " + id));
    }

    @Override
    public void eliminarServicio(Long id) {
        servicioRepositorio.deleteById(id);
    }
}