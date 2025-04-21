package com.example.Barberia.services.impl;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.repositories.AdministradorRepository;
import com.example.Barberia.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository repositorio;

    @Override
    public List<Administrador> obtenerTodos() {
        return repositorio.findAll();
    }

    @Override
    public Administrador obtenerPorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Administrador guardar(Administrador administrador) {
        return repositorio.save(administrador);
    }

    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
