package com.example.Barberia.controllers;

import com.example.Barberia.models.Barbero;
import com.example.Barberia.models.GaleriaCorte;
import com.example.Barberia.services.BarberoService;
import com.example.Barberia.services.GaleriaCorteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/galeria")
public class GaleriaCorteController {

    @Autowired
    private GaleriaCorteService galeriaCorteService;

    @Autowired
    private BarberoService barberoService;

    /**
     * Obtener todas las fotos de un barbero específico
     */
    @GetMapping("/barbero/{idBarbero}")
    public ResponseEntity<List<GaleriaCorte>> obtenerFotosPorBarbero(@PathVariable Long idBarbero) {
        List<GaleriaCorte> fotos = galeriaCorteService.obtenerFotosPorBarbero(idBarbero);
        return ResponseEntity.ok(fotos);
    }

    /**
     * Subir una nueva foto a la galería de un barbero
     */
    @PostMapping("/barbero/{idBarbero}")
    public ResponseEntity<GaleriaCorte> subirFoto(
            @PathVariable Long idBarbero,
            @RequestBody GaleriaCorte galeriaCorte
    ) {
        try {
            // Buscar el barbero
            Barbero barbero = barberoService.obtenerBarberoPorId(idBarbero);
            if (barbero == null) {
                return ResponseEntity.notFound().build();
            }

            // Asignar el barbero a la foto
            galeriaCorte.setBarbero(barbero);

            // Guardar la foto
            GaleriaCorte fotoGuardada = galeriaCorteService.guardarFoto(galeriaCorte);
            return ResponseEntity.status(HttpStatus.CREATED).body(fotoGuardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Eliminar una foto de la galería
     */
    @DeleteMapping("/{idGaleria}")
    public ResponseEntity<Void> eliminarFoto(@PathVariable Long idGaleria) {
        try {
            GaleriaCorte foto = galeriaCorteService.obtenerFotoPorId(idGaleria);
            if (foto == null) {
                return ResponseEntity.notFound().build();
            }
            
            galeriaCorteService.eliminarFoto(idGaleria);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Obtener una foto específica por ID
     */
    @GetMapping("/{idGaleria}")
    public ResponseEntity<GaleriaCorte> obtenerFotoPorId(@PathVariable Long idGaleria) {
        GaleriaCorte foto = galeriaCorteService.obtenerFotoPorId(idGaleria);
        if (foto != null) {
            return ResponseEntity.ok(foto);
        }
        return ResponseEntity.notFound().build();
    }
}

