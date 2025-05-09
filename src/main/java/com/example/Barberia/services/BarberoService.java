package com.example.Barberia.services;

import com.example.Barberia.models.Barbero;

import java.util.List;

public interface BarberoService {

    Barbero guardarBarbero(Barbero barbero);
    void eliminarBarbero(Long id);

    // Método para listar todos los barberos
    List<Barbero> listarBarberos();
}
