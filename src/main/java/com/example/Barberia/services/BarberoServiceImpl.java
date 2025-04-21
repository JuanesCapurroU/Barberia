package com.example.Barberia.services;

import com.example.Barberia.repositories.BarberoRepository;
import com.example.Barberia.models.Barbero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BarberoServiceImpl implements BarberoService {

    @Autowired
    private BarberoRepository barberoRepositorio;

    @Override
    public Barbero guardarBarbero(Barbero barbero) {
        return barberoRepositorio.save(barbero);
    }

    @Override
    public List<Barbero> listarBarberos() {
        return barberoRepositorio.findAll();
    }

    @Override
    public Barbero obtenerBarberoPorId(Long id) {
        return barberoRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbero no encontrado con id: " + id));
    }

    @Override
    public void eliminarBarbero(Long id) {
        barberoRepositorio.deleteById(id);
    }
}
