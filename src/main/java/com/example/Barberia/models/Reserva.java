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


    public Reserva() {
    }

    public Reserva(Long idReserva, Servicio servicio, Barbero barbero, HorarioDisponible horarioDisponible, Cliente cliente, String nombreCliente, String celularCliente, String correoCliente) {
        this.idReserva = idReserva;
        this.servicio = servicio;
        this.barbero = barbero;
        this.horarioDisponible = horarioDisponible;
        this.cliente = cliente;
        this.nombreCliente = nombreCliente;
        this.celularCliente = celularCliente;
        this.correoCliente = correoCliente;
    }

    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    public HorarioDisponible getHorarioDisponible() {
        return horarioDisponible;
    }

    public void setHorarioDisponible(HorarioDisponible horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCelularCliente() {
        return celularCliente;
    }

    public void setCelularCliente(String celularCliente) {
        this.celularCliente = celularCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", servicio=" + servicio +
                ", barbero=" + barbero +
                ", horarioDisponible=" + horarioDisponible +
                ", cliente=" + cliente +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", celularCliente='" + celularCliente + '\'' +
                ", correoCliente='" + correoCliente + '\'' +
                '}';
    }
}

