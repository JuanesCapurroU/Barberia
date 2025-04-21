package com.example.Barberia.services;

import com.example.Barberia.models.Administrador;
import java.util.List;

public interface AdministradorService {
    List<Administrador> obtenerTodos();
    Administrador obtenerPorId(Long id);
    Administrador guardar(Administrador administrador);
    void eliminar(Long id);
}
