package com.example.Barberia.models;

import jakarta.persistence.*;

@Entity
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Column(nullable = false)
    private String nombreServicio;

    private String descripcion;


    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }


    public Servicio() {
    }

    public Servicio(Long idServicio, String nombreServicio, String descripcion) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", nombreServicio='" + nombreServicio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}



