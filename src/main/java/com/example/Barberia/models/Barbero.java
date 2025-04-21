package com.example.Barberia.models;

import jakarta.persistence.*;

@Entity
public class Barbero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarbero;

    @Column(nullable = false)
    private String nombre;

    private String estado; // Ejemplo: Activo o Inactivo

    private String correo;

    private String telefono;
}
