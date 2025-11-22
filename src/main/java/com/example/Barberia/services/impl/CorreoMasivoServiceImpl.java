package com.example.Barberia.services.impl;

import com.example.Barberia.models.Cliente;
import com.example.Barberia.repositories.ClienteRepository;
import com.example.Barberia.services.CorreoMasivoService;
import com.example.Barberia.services.EmailService;
import com.example.Barberia.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CorreoMasivoServiceImpl implements CorreoMasivoService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CuponService cuponService;

    @Override
    public int enviarCorreosInformativos(String mensaje, Long idAdministrador) {
        List<Cliente> clientes = clienteRepository.findAll();
        int enviados = 0;
        
        for (Cliente cliente : clientes) {
            try {
                emailService.enviarCorreoInformativo(
                    cliente.getCorreo(),
                    cliente.getNombre(),
                    mensaje
                );
                enviados++;
            } catch (Exception e) {
                System.err.println("Error enviando correo a " + cliente.getCorreo() + ": " + e.getMessage());
            }
        }
        
        return enviados;
    }

    @Override
    public int enviarCorreosPromocion(String nombreCupon, int porcentajeDescuento, String fechaValidez, Long idAdministrador) {
        // Crear o actualizar el cupón en la base de datos
        try {
            cuponService.crearCupon(nombreCupon, porcentajeDescuento, fechaValidez);
        } catch (Exception e) {
            System.err.println("Error creando cupón: " + e.getMessage());
        }
        
        List<Cliente> clientes = clienteRepository.findAll();
        int enviados = 0;
        
        for (Cliente cliente : clientes) {
            try {
                emailService.enviarCorreoPromocion(
                    cliente.getCorreo(),
                    cliente.getNombre(),
                    nombreCupon,
                    porcentajeDescuento,
                    fechaValidez
                );
                enviados++;
            } catch (Exception e) {
                System.err.println("Error enviando correo de promoción a " + cliente.getCorreo() + ": " + e.getMessage());
            }
        }
        
        return enviados;
    }
}

