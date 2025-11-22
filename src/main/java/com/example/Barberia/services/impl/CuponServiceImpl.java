package com.example.Barberia.services.impl;

import com.example.Barberia.models.Cupon;
import com.example.Barberia.repositories.CuponRepository;
import com.example.Barberia.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CuponServiceImpl implements CuponService {

    @Autowired
    private CuponRepository cuponRepository;

    @Override
    public Cupon crearCupon(String codigo, Integer porcentajeDescuento, String fechaValidez) {
        // Verificar si el cupón ya existe
        if (cuponRepository.existsByCodigo(codigo)) {
            // Si existe, actualizar sus datos
            Optional<Cupon> cuponExistente = cuponRepository.findByCodigo(codigo);
            if (cuponExistente.isPresent()) {
                Cupon cupon = cuponExistente.get();
                cupon.setPorcentajeDescuento(porcentajeDescuento);
                cupon.setFechaValidez(parsearFecha(fechaValidez));
                cupon.setActivo(true);
                return cuponRepository.save(cupon);
            }
        }
        
        // Crear nuevo cupón
        Cupon cupon = new Cupon();
        cupon.setCodigo(codigo);
        cupon.setPorcentajeDescuento(porcentajeDescuento);
        cupon.setFechaValidez(parsearFecha(fechaValidez));
        cupon.setActivo(true);
        return cuponRepository.save(cupon);
    }

    @Override
    public Optional<Cupon> validarCupon(String codigo) {
        Optional<Cupon> cuponOpt = cuponRepository.findByCodigo(codigo);
        
        if (cuponOpt.isPresent()) {
            Cupon cupon = cuponOpt.get();
            LocalDate hoy = LocalDate.now();
            
            // Validar que el cupón esté activo y no haya expirado
            if (cupon.getActivo() && !cupon.getFechaValidez().isBefore(hoy)) {
                return Optional.of(cupon);
            }
        }
        
        return Optional.empty();
    }

    private LocalDate parsearFecha(String fechaStr) {
        // Intentar parsear en formato DD/MM/YYYY
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(fechaStr, formatter);
        } catch (Exception e) {
            // Si falla, intentar formato ISO
            try {
                return LocalDate.parse(fechaStr);
            } catch (Exception e2) {
                throw new RuntimeException("Formato de fecha inválido: " + fechaStr);
            }
        }
    }

    @Override
    public List<Cupon> listarTodosLosCupones() {
        return cuponRepository.findAll();
    }

    @Override
    public List<Cupon> listarCuponesActivos() {
        LocalDate hoy = LocalDate.now();
        return cuponRepository.findAll().stream()
                .filter(cupon -> cupon.getActivo() && !cupon.getFechaValidez().isBefore(hoy))
                .toList();
    }

    @Override
    public List<Cupon> listarCuponesExpirados() {
        LocalDate hoy = LocalDate.now();
        return cuponRepository.findAll().stream()
                .filter(cupon -> cupon.getFechaValidez().isBefore(hoy) || !cupon.getActivo())
                .toList();
    }

    @Override
    public Optional<Cupon> obtenerCuponPorId(Long id) {
        return cuponRepository.findById(id);
    }

    @Override
    public Cupon actualizarCupon(Long id, String codigo, Integer porcentajeDescuento, String fechaValidez, Boolean activo) {
        Optional<Cupon> cuponOpt = cuponRepository.findById(id);
        if (cuponOpt.isPresent()) {
            Cupon cupon = cuponOpt.get();
            cupon.setCodigo(codigo);
            cupon.setPorcentajeDescuento(porcentajeDescuento);
            cupon.setFechaValidez(parsearFecha(fechaValidez));
            cupon.setActivo(activo != null ? activo : true);
            return cuponRepository.save(cupon);
        }
        throw new RuntimeException("Cupón no encontrado");
    }

    @Override
    public void eliminarCupon(Long id) {
        cuponRepository.deleteById(id);
    }
}

