package com.example.Barberia.dto;

import java.util.List;

public class DashboardEstadisticas {
    
    private Long totalReservas;
    private Long totalBarberos;
    private Long totalClientes;
    private Long totalServicios;
    private Double ingresosTotales;
    private Double comisionAdministrador;
    private Double comisionBarberos;
    private Double porcentajeComisionAdmin;
    private Double porcentajeComisionBarbero;
    private List<EstadisticasBarbero> estadisticasPorBarbero;
    private List<ReservaPorMes> reservasPorMes;
    
    // Estadísticas segmentadas por modalidad
    private Long reservasPresenciales;
    private Long reservasDomicilio;
    private Double ingresosPresenciales;
    private Double ingresosDomicilio;

    // Constructores
    public DashboardEstadisticas() {
    }

    public DashboardEstadisticas(Long totalReservas, Long totalBarberos, Long totalClientes, 
                                 Long totalServicios, Double ingresosTotales, 
                                 Double comisionAdministrador, Double comisionBarberos,
                                 Double porcentajeComisionAdmin, Double porcentajeComisionBarbero,
                                 List<EstadisticasBarbero> estadisticasPorBarbero,
                                 List<ReservaPorMes> reservasPorMes) {
        this.totalReservas = totalReservas;
        this.totalBarberos = totalBarberos;
        this.totalClientes = totalClientes;
        this.totalServicios = totalServicios;
        this.ingresosTotales = ingresosTotales;
        this.comisionAdministrador = comisionAdministrador;
        this.comisionBarberos = comisionBarberos;
        this.porcentajeComisionAdmin = porcentajeComisionAdmin;
        this.porcentajeComisionBarbero = porcentajeComisionBarbero;
        this.estadisticasPorBarbero = estadisticasPorBarbero;
        this.reservasPorMes = reservasPorMes;
    }

    // Getters y Setters
    public Long getTotalReservas() {
        return totalReservas;
    }

    public void setTotalReservas(Long totalReservas) {
        this.totalReservas = totalReservas;
    }

    public Long getTotalBarberos() {
        return totalBarberos;
    }

    public void setTotalBarberos(Long totalBarberos) {
        this.totalBarberos = totalBarberos;
    }

    public Long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(Long totalClientes) {
        this.totalClientes = totalClientes;
    }

    public Long getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(Long totalServicios) {
        this.totalServicios = totalServicios;
    }

    public Double getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(Double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    public Double getComisionAdministrador() {
        return comisionAdministrador;
    }

    public void setComisionAdministrador(Double comisionAdministrador) {
        this.comisionAdministrador = comisionAdministrador;
    }

    public Double getComisionBarberos() {
        return comisionBarberos;
    }

    public void setComisionBarberos(Double comisionBarberos) {
        this.comisionBarberos = comisionBarberos;
    }

    public Double getPorcentajeComisionAdmin() {
        return porcentajeComisionAdmin;
    }

    public void setPorcentajeComisionAdmin(Double porcentajeComisionAdmin) {
        this.porcentajeComisionAdmin = porcentajeComisionAdmin;
    }

    public Double getPorcentajeComisionBarbero() {
        return porcentajeComisionBarbero;
    }

    public void setPorcentajeComisionBarbero(Double porcentajeComisionBarbero) {
        this.porcentajeComisionBarbero = porcentajeComisionBarbero;
    }

    public List<EstadisticasBarbero> getEstadisticasPorBarbero() {
        return estadisticasPorBarbero;
    }

    public void setEstadisticasPorBarbero(List<EstadisticasBarbero> estadisticasPorBarbero) {
        this.estadisticasPorBarbero = estadisticasPorBarbero;
    }

    public List<ReservaPorMes> getReservasPorMes() {
        return reservasPorMes;
    }

    public void setReservasPorMes(List<ReservaPorMes> reservasPorMes) {
        this.reservasPorMes = reservasPorMes;
    }

    public Long getReservasPresenciales() {
        return reservasPresenciales;
    }

    public void setReservasPresenciales(Long reservasPresenciales) {
        this.reservasPresenciales = reservasPresenciales;
    }

    public Long getReservasDomicilio() {
        return reservasDomicilio;
    }

    public void setReservasDomicilio(Long reservasDomicilio) {
        this.reservasDomicilio = reservasDomicilio;
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

