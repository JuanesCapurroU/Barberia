package com.example.Barberia.services;

import com.example.Barberia.models.Administrador;
import java.util.List;

public interface AdministradorService {
    Administrador guardarAdministrador(Administrador administrador);
    void eliminarAdministrador(Long id);
    List<Administrador> listarAdministradores();
    Administrador obtenerAdministradorPorId(Long id); // <- Agregado
}