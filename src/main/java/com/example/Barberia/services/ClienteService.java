package com.example.Barberia.services;

import com.example.Barberia.models.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente guardarCliente(Cliente cliente);
    Cliente guardarCliente(Cliente cliente, Long idAdministrador);
    Cliente registrarCliente(Cliente cliente);
    Optional<Cliente> findByCorreo(String correo);
    boolean validarCredenciales(String correo, String contraseña);
    void eliminarCliente(Long id);
    List<Cliente> listarClientes();
}