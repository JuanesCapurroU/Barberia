package com.example.Barberia.services;

import com.example.Barberia.models.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> obtenerTodos();
    Cliente obtenerPorId(Long id);
    Cliente guardar(Cliente cliente);
    void eliminar(Long id);
}
