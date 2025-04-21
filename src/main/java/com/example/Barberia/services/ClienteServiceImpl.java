package com.example.Barberia.services.impl;

import com.example.Barberia.models.Cliente;
import com.example.Barberia.repositories.ClienteRepository;
import com.example.Barberia.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repositorio;

    @Override
    public List<Cliente> obtenerTodos() {
        return repositorio.findAll();
    }

    @Override
    public Cliente obtenerPorId(Long id) {
        return repositorio.findById(id).orElse(null);
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return repositorio.save(cliente);
    }

    @Override
    public void eliminar(Long id) {
        repositorio.deleteById(id);
    }
}
