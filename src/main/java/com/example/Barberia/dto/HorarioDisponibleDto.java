package com.example.Barberia.dto;

public class HorarioDisponibleDto {
    private Long idHorario;
    private String horaInicio;
    private String modalidadServicio; // PRESENCIAL, DOMICILIO, AMBOS

    public HorarioDisponibleDto(Long idHorario, String horaInicio) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
    }

    public HorarioDisponibleDto(Long idHorario, String horaInicio, String modalidadServicio) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
        this.modalidadServicio = modalidadServicio;
    }

    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getModalidadServicio() {
        return modalidadServicio;
    }

    public void setModalidadServicio(String modalidadServicio) {
        this.modalidadServicio = modalidadServicio;
    }
}
