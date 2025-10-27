package com.example.Barberia.models;

import jakarta.persistence.*;

@Entity
@Table(name = "galeria_cortes")
public class GaleriaCorte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGaleria;

    @ManyToOne
    @JoinColumn(name = "idBarbero", nullable = false)
    private Barbero barbero;

    @Column(nullable = false)
    private String fotoUrl;

    private String descripcion;

    @Column(name = "fecha_subida")
    private String fechaSubida;

    // Constructores
    public GaleriaCorte() {
    }

    public GaleriaCorte(Long idGaleria, Barbero barbero, String fotoUrl, String descripcion, String fechaSubida) {
        this.idGaleria = idGaleria;
        this.barbero = barbero;
        this.fotoUrl = fotoUrl;
        this.descripcion = descripcion;
        this.fechaSubida = fechaSubida;
    }

    // Getters y Setters
    public Long getIdGaleria() {
        return idGaleria;
    }

    public void setIdGaleria(Long idGaleria) {
        this.idGaleria = idGaleria;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public void setBarbero(Barbero barbero) {
        this.barbero = barbero;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(String fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    @Override
    public String toString() {
        return "GaleriaCorte{" +
                "idGaleria=" + idGaleria +
                ", barbero=" + (barbero != null ? barbero.getIdBarbero() : null) +
                ", fotoUrl='" + fotoUrl + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fechaSubida='" + fechaSubida + '\'' +
                '}';
    }
}

