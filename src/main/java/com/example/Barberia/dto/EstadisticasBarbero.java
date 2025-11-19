package com.example.Barberia.dto;

public class EstadisticasBarbero {
    
    private Long idBarbero;
    private String nombreBarbero;
    private Long totalCortes;
    private Double ingresosGenerados;
    private Double comisionBarbero;
    private Double comisionAdmin;
    
    // Estadísticas segmentadas por modalidad
    private Long cortesPresenciales;
    private Long cortesDomicilio;
    private Double ingresosPresenciales;
    private Double ingresosDomicilio;

    // Constructores
    public EstadisticasBarbero() {
    }

    public EstadisticasBarbero(Long idBarbero, String nombreBarbero, Long totalCortes, 
                               Double ingresosGenerados, Double comisionBarbero, Double comisionAdmin) {
        this.idBarbero = idBarbero;
        this.nombreBarbero = nombreBarbero;
        this.totalCortes = totalCortes;
        this.ingresosGenerados = ingresosGenerados;
        this.comisionBarbero = comisionBarbero;
        this.comisionAdmin = comisionAdmin;
    }

    // Getters y Setters
    public Long getIdBarbero() {
        return idBarbero;
    }

    public void setIdBarbero(Long idBarbero) {
        this.idBarbero = idBarbero;
    }

    public String getNombreBarbero() {
        return nombreBarbero;
    }

    public void setNombreBarbero(String nombreBarbero) {
        this.nombreBarbero = nombreBarbero;
    }

    public Long getTotalCortes() {
        return totalCortes;
    }

    public void setTotalCortes(Long totalCortes) {
        this.totalCortes = totalCortes;
    }

    public Double getIngresosGenerados() {
        return ingresosGenerados;
    }

    public void setIngresosGenerados(Double ingresosGenerados) {
        this.ingresosGenerados = ingresosGenerados;
    }

    public Double getComisionBarbero() {
        return comisionBarbero;
    }

    public void setComisionBarbero(Double comisionBarbero) {
        this.comisionBarbero = comisionBarbero;
    }

    public Double getComisionAdmin() {
        return comisionAdmin;
    }

    public void setComisionAdmin(Double comisionAdmin) {
        this.comisionAdmin = comisionAdmin;
    }

    public Long getCortesPresenciales() {
        return cortesPresenciales;
    }

    public void setCortesPresenciales(Long cortesPresenciales) {
        this.cortesPresenciales = cortesPresenciales;
    }

    public Long getCortesDomicilio() {
        return cortesDomicilio;
    }

    public void setCortesDomicilio(Long cortesDomicilio) {
        this.cortesDomicilio = cortesDomicilio;
    }

    public Double getIngresosPresenciales() {
        return ingresosPresenciales;
    }

    public void setIngresosPresenciales(Double ingresosPresenciales) {
        this.ingresosPresenciales = ingresosPresenciales;
    }

    public Double getIngresosDomicilio() {
        return ingresosDomicilio;
    }

    public void setIngresosDomicilio(Double ingresosDomicilio) {
        this.ingresosDomicilio = ingresosDomicilio;
    }
}

