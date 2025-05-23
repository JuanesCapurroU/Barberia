package com.example.Barberia.repositories;

import com.example.Barberia.models.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    // Método para buscar por usuario (nombre de usuario)
    Administrador findByUsuario(String usuario);
}
