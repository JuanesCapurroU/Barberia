package com.example.Barberia.dto;

public class HorarioDisponibleDto {
    private Long idHorario;
    private String horaInicio;

    public HorarioDisponibleDto(Long idHorario, String horaInicio) {
        this.idHorario = idHorario;
        this.horaInicio = horaInicio;
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
}
