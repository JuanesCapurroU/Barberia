package com.example.Barberia.services;

import com.example.Barberia.repositories.BarberoRepository;
import com.example.Barberia.models.Barbero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BarberoServiceImpl implements BarberoService {

    @Autowired
    private BarberoRepository barberoRepository;

    @Override
    public Barbero guardarBarbero(Barbero barbero) {
        return barberoRepository.save(barbero);
    }

    @Override
    public void eliminarBarbero(Long id) {
        barberoRepository.deleteById(id);
    }

    @Override
    public List<Barbero> listarBarberos() {
        return barberoRepository.findAll();
    }
}