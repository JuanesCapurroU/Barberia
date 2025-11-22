package com.example.Barberia.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "cupones")
public class Cupon {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String codigo;
    
    @Column(nullable = false)
    private Integer porcentajeDescuento;
    
    @Column(nullable = false)
    private LocalDate fechaValidez;
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public Integer getPorcentajeDescuento() {
        return porcentajeDescuento;
    }
    
    public void setPorcentajeDescuento(Integer porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }
    
    @JsonGetter("fechaValidez")
    public String getFechaValidezAsString() {
        if (fechaValidez != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return fechaValidez.format(formatter);
        }
        return null;
    }
    
    public LocalDate getFechaValidez() {
        return fechaValidez;
    }
    
    @JsonSetter("fechaValidez")
    public void setFechaValidezFromString(String fechaValidezStr) {
        if (fechaValidezStr != null && !fechaValidezStr.isEmpty()) {
            try {
                // Intentar parsear en formato DD/MM/YYYY
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                this.fechaValidez = LocalDate.parse(fechaValidezStr, formatter);
            } catch (Exception e) {
                try {
                    // Si falla, intentar formato ISO
                    this.fechaValidez = LocalDate.parse(fechaValidezStr);
                } catch (Exception e2) {
                    throw new RuntimeException("Formato de fecha inválido: " + fechaValidezStr);
                }
            }
        }
    }
    
    public void setFechaValidez(LocalDate fechaValidez) {
        this.fechaValidez = fechaValidez;
    }
    
    public Boolean getActivo() {
        return activo;
    }
    
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}



