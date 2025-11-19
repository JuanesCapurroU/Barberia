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

    // Modalidad de trabajo actual: PRESENCIAL o DOMICILIO (no puede ser AMBOS)
    @Enumerated(EnumType.STRING)
    @Column(name = "modalidad_actual", nullable = false)
    private ModalidadServicio modalidadActual = ModalidadServicio.PRESENCIAL;

    // Precio adicional que cobra este barbero por servicio a domicilio
    @Column(name = "precio_adicional_domicilio")
    private Double precioAdicionalDomicilio = 10000.0;

    public Barbero() {
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Barbero(Long idBarbero, String nombre, String estado, String correo, String telefono, String usuario, String contraseña, String fotoUrl, ModalidadServicio modalidadActual) {
        this.idBarbero = idBarbero;
        this.nombre = nombre;
        this.estado = estado;
        this.correo = correo;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.fotoUrl = fotoUrl;
        this.modalidadActual = modalidadActual != null ? modalidadActual : ModalidadServicio.AMBOS;
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

    public ModalidadServicio getModalidadActual() {
        return modalidadActual;
    }

    public void setModalidadActual(ModalidadServicio modalidadActual) {
        this.modalidadActual = modalidadActual;
    }

    public Double getPrecioAdicionalDomicilio() {
        return precioAdicionalDomicilio;
    }

    public void setPrecioAdicionalDomicilio(Double precioAdicionalDomicilio) {
        this.precioAdicionalDomicilio = precioAdicionalDomicilio;
    }

    @Override
    public String toString() {
        return "Barbero{" +
                "idBarbero=" + idBarbero +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", modalidadActual=" + modalidadActual +
                '}';
    }
}