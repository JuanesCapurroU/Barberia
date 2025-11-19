package com.example.Barberia.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class HorarioDisponible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHorario;

    @ManyToOne
    @JoinColumn(name = "idBarbero", nullable = false)
    private Barbero barbero;

    private LocalDate fecha;

    private LocalTime horaInicio;

    private LocalTime horaFin;

    private boolean disponible;

    // Modalidad del servicio: PRESENCIAL, DOMICILIO o AMBOS
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad_servicio", nullable = false)
    private ModalidadServicio modalidadServicio = ModalidadServicio.PRESENCIAL;

    public HorarioDisponible() {
    }

    public HorarioDisponible(Long idHorario, LocalDate fecha, Barbero barbero, LocalTime horaInicio, LocalTime horaFin, boolean disponible, ModalidadServicio modalidadServicio) {
        this.idHorario = idHorario;
        this.fecha = fecha;
        this.barbero = barbero;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.disponible = disponible;
        this.modalidadServicio = modalidadServicio != null ? modalidadServicio : ModalidadServicio.PRESENCIAL;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public ModalidadServicio getModalidadServicio() {
        return modalidadServicio;
    }

    public void setModalidadServicio(ModalidadServicio modalidadServicio) {
        this.modalidadServicio = modalidadServicio;
    }

    @Override
    public String toString() {
        return "HorarioDisponible{" +
                "idHorario=" + idHorario +
                ", barbero=" + barbero +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                ", disponible=" + disponible +
                ", modalidadServicio=" + modalidadServicio +
                '}';
    }
}
