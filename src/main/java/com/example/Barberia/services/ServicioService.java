package com.example.Barberia.services;

import com.example.Barberia.models.Servicio;

import java.util.List;

public interface ServicioService {
    Servicio guardarServicio(Servicio servicio);
    void eliminarServicio(Long id);
    List<Servicio> listarServicios();
}