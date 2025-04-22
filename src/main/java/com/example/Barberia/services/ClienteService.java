package com.example.Barberia.services;

import com.example.Barberia.models.Cliente;
import java.util.List;

public interface ClienteService {
    Cliente guardarCliente(Cliente cliente);
    void eliminarCliente(Long id);
    List<Cliente> listarClientes();
}