package com.example.Barberia.services;

import com.example.Barberia.models.Configuracion;

public interface ConfiguracionService {
    
    Configuracion guardarConfiguracion(Configuracion configuracion);
    
    Configuracion obtenerPorClave(String clave);
    
    Double obtenerPorcentajeComisionAdmin();
    
    Double obtenerPorcentajeComisionBarbero();
    
    void actualizarComisiones(Double porcentajeAdmin, Double porcentajeBarbero);
}

