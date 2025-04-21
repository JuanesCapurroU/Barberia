package com.example.Barberia;

import java.util.List;

public interface BarberoService {
    Barbero guardarBarbero(Barbero barbero);
    List<Barbero> listarBarberos();
    Barbero obtenerBarberoPorId(Long id);
    void eliminarBarbero(Long id);
}
