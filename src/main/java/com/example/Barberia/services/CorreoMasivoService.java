package com.example.Barberia.services;

import java.util.List;

public interface CorreoMasivoService {
    int enviarCorreosInformativos(String mensaje, Long idAdministrador);
    int enviarCorreosPromocion(String nombreCupon, int porcentajeDescuento, String fechaValidez, Long idAdministrador);
}



