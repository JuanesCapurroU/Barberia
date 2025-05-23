package com.example.Barberia.models;

import jakarta.persistence.*;

@Entity
public class Barbero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBarbero;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_administrador", nullable = true)
    private Administrador administrador;



    private String estado;

    private String correo;

    private String telefono;

    private String usuario;
    private String contraseña;

    private String fotoUrl;

    public Barbero() {
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Barbero(Long idBarbero, String nombre, String estado, String correo, String telefono, String usuario, String contraseña, String fotoUrl) {
        this.idBarbero = idBarbero;
        this.nombre = nombre;
        this.estado = estado;
        this.correo = correo;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.fotoUrl = fotoUrl;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Long getIdBarbero() {
        return idBarbero;
    }

    public void setIdBarbero(Long idBarbero) {
        this.idBarbero = idBarbero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public Long getIdAdministrador() {
        return administrador != null ? administrador.getId_admin() : null;
    }

    @Override
    public String toString() {
        return "Barbero{" +
                "idBarbero=" + idBarbero +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}