package com.example.Barberia.services;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.repositories.AdministradorRepository;
import com.example.Barberia.services.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdministradorServiceImpl implements AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Override
    public Administrador guardarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    @Override
    public void eliminarAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }

    @Override
    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    @Override
    public Administrador obtenerAdministradorPorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado con id: " + id));
    }
}