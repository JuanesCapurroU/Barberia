package com.example.Barberia.services;

import com.example.Barberia.models.Cupon;
import java.util.List;
import java.util.Optional;

public interface CuponService {
    Cupon crearCupon(String codigo, Integer porcentajeDescuento, String fechaValidez);
    Optional<Cupon> validarCupon(String codigo);
    List<Cupon> listarTodosLosCupones();
    List<Cupon> listarCuponesActivos();
    List<Cupon> listarCuponesExpirados();
    Optional<Cupon> obtenerCuponPorId(Long id);
    Cupon actualizarCupon(Long id, String codigo, Integer porcentajeDescuento, String fechaValidez, Boolean activo);
    void eliminarCupon(Long id);
}

