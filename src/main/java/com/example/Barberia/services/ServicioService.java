package com.example.Barberia.services;

import com.example.Barberia.models.Servicio;

import java.util.List;

public interface ServicioService {

    Servicio guardarServicio(Servicio servicio);
    List<Servicio> listarServicios();
    Servicio obtenerServicioPorId(Long id);
    void eliminarServicio(Long id);
}