package com.example.Barberia.models;

import jakarta.persistence.*;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    @ManyToOne
    @JoinColumn(name = "id_barbero", nullable = false)
    private Barbero barbero;

    @ManyToOne
    @JoinColumn(name = "id_horario", nullable = false)
    private HorarioDisponible horarioDisponible;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    private String nombreCliente;

    private String celularCliente;

    private String correoCliente;
}