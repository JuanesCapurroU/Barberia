package com.example.Barberia.dto;

public class ReservaPorMes {
    
    private String mes;
    private Long cantidad;
    private Double ingresos;

    // Constructores
    public ReservaPorMes() {
    }

    public ReservaPorMes(String mes, Long cantidad, Double ingresos) {
        this.mes = mes;
        this.cantidad = cantidad;
        this.ingresos = ingresos;
    }

    // Getters y Setters
    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Double getIngresos() {
        return ingresos;
    }

    public void setIngresos(Double ingresos) {
        this.ingresos = ingresos;
    }
}

