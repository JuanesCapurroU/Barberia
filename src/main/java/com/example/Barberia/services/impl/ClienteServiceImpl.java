package com.example.Barberia.services.impl;

import com.example.Barberia.models.Cliente;
import com.example.Barberia.repositories.ClienteRepository;
import com.example.Barberia.services.ClienteService;
import com.example.Barberia.services.EmailService;
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
    
    @Autowired
    private EmailService emailService;

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
        
        Cliente clienteGuardado = clienteRepository.save(cliente);
        
        // Enviar correo de bienvenida
        try {
            emailService.enviarCorreoBienvenida(clienteGuardado.getCorreo(), clienteGuardado.getNombre());
        } catch (Exception e) {
            // No lanzar excepción si falla el envío de correo, solo registrar el error
            System.err.println("Error al enviar correo de bienvenida: " + e.getMessage());
        }
        
        return clienteGuardado;
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
    
    @Override
    public boolean recuperarContraseña(String correo) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCorreo(correo);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            
            // Generar una nueva contraseña temporal
            String nuevaContraseña = generarContraseñaTemporal();
            
            // Encriptar la nueva contraseña
            String contraseñaEncriptada = passwordEncoder.encode(nuevaContraseña);
            cliente.setContraseña(contraseñaEncriptada);
            clienteRepository.save(cliente);
            
            // Enviar correo con la nueva contraseña
            emailService.enviarCorreoRecuperacionContraseña(cliente.getCorreo(), cliente.getNombre(), nuevaContraseña);
            
            return true;
        }
        return false;
    }
    
    private String generarContraseñaTemporal() {
        // Generar una contraseña temporal de 8 caracteres
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder contraseña = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < 8; i++) {
            contraseña.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return contraseña.toString();
    }
    
    @Override
    public Cliente actualizarPerfil(String correo, String nombre, String celular, String direccion) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCorreo(correo);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            if (nombre != null && !nombre.trim().isEmpty()) {
                cliente.setNombre(nombre.trim());
            }
            if (celular != null && !celular.trim().isEmpty()) {
                cliente.setCelular(celular.trim());
            }
            if (direccion != null) {
                cliente.setDireccion(direccion.trim());
            }
            return clienteRepository.save(cliente);
        }
        throw new RuntimeException("Cliente no encontrado");
    }
    
    @Override
    public boolean cambiarContraseña(String correo, String contraseñaActual, String nuevaContraseña) {
        Optional<Cliente> clienteOpt = clienteRepository.findByCorreo(correo);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            // Verificar que la contraseña actual sea correcta
            if (passwordEncoder.matches(contraseñaActual, cliente.getContraseña())) {
                // Encriptar la nueva contraseña
                String nuevaContraseñaEncriptada = passwordEncoder.encode(nuevaContraseña);
                cliente.setContraseña(nuevaContraseñaEncriptada);
                clienteRepository.save(cliente);
                return true;
            } else {
                throw new RuntimeException("La contraseña actual es incorrecta");
            }
        }
        throw new RuntimeException("Cliente no encontrado");
    }
}
