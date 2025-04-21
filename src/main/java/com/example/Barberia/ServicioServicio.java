package com.example.Barberia;

import com.example.Barberia.Servicio;
import java.util.List;

public interface ServicioServicio {

    Servicio guardarServicio(Servicio servicio);
    List<Servicio> listarServicios();
    Servicio obtenerServicioPorId(Long id);
    void eliminarServicio(Long id);
}