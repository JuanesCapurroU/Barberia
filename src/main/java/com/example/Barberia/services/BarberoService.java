package com.example.Barberia.services;

import com.example.Barberia.models.Barbero;

import java.util.List;

public interface BarberoService {
    Barbero guardarBarbero(Barbero barbero);
    List<Barbero> listarBarberos();
    Barbero obtenerBarberoPorId(Long id);
    void eliminarBarbero(Long id);
}
