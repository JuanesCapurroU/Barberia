package com.example.Barberia.services.impl;

import com.example.Barberia.models.Cliente;
import com.example.Barberia.repositories.ClienteRepository;
import com.example.Barberia.services.ClienteService;
import com.example.Barberia.utils.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    @Qualifier("customPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    @Override
    public Cliente guardarCliente(Cliente cliente, Long idAdministrador) {
        // Aquí puedes agregar validación del administrador si es necesario
        // Por ahora simplemente guardamos el cliente
        return clienteRepository.save(cliente);
    }
    
    @Override
    public Cliente registrarCliente(Cliente cliente) {
        // Verificar si el correo ya existe
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        // Encriptar la contraseña
        String contraseñaEncriptada = passwordEncoder.encode(cliente.getContraseña());
        cliente.setContraseña(contraseñaEncriptada);
        
        // Establecer como activo por defecto
        cliente.setActivo(true);
        
        return clienteRepository.save(cliente);
    }
    
    @Override
    public Optional<Cliente> findByCorreo(String correo) {
        return clienteRepository.findByCorreo(correo);
    }
    
    @Override
    public boolean validarCredenciales(String correo, String contraseña) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCorreo(correo);
        
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return cliente.getActivo() && passwordEncoder.matches(contraseña, cliente.getContraseña());
        }
        
        return false;
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
