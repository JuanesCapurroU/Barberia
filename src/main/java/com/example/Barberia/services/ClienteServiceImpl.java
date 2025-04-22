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
    private ClienteRepository clienteRepository;

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}
