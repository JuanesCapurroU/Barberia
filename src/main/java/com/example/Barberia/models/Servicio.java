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
    private String fotoUrl;

    private Double precio;

    // Precio adicional por servicio a domicilio (null si no aplica)
    @Column(name = "precio_adicional_domicilio")
    private Double precioAdicionalDomicilio;


    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPrecioAdicionalDomicilio() {
        return precioAdicionalDomicilio;
    }

    public void setPrecioAdicionalDomicilio(Double precioAdicionalDomicilio) {
        this.precioAdicionalDomicilio = precioAdicionalDomicilio;
    }

    public Servicio() {
    }

    public Servicio(Long idServicio, String nombreServicio, String descripcion, String fotoUrl, Double precio, Double precioAdicionalDomicilio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.fotoUrl = fotoUrl;
        this.precio = precio;
        this.precioAdicionalDomicilio = precioAdicionalDomicilio;
    }


    @Override
    public String toString() {
        return "Servicio{" +
                "idServicio=" + idServicio +
                ", nombreServicio='" + nombreServicio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fotoUrl='" + fotoUrl + '\'' +
                ", precio=" + precio +
                ", precioAdicionalDomicilio=" + precioAdicionalDomicilio +
                '}';
    }
}
