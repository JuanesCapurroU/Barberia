package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Barbero;
import com.example.Barberia.models.ModalidadServicio;
import com.example.Barberia.repositories.AdministradorRepository;
import com.example.Barberia.services.BarberoServiceImpl;
import com.example.Barberia.services.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/barberos")
public class BarberoController {

    @Autowired
    private BarberoServiceImpl barberoServiceImpl;

    @Autowired
    private HorarioDisponibleService horarioDisponibleService;

    @Autowired
    private AdministradorRepository administradorRepository;

    @PostMapping
    public Barbero guardarBarbero(@RequestBody Barbero barbero, @RequestParam Long idAdministrador) {
        Administrador admin = administradorRepository.findById(idAdministrador)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Administrador no encontrado"));


        barbero.setAdministrador(admin);

        System.out.println("Barbero recibido: " + barbero);

        Barbero nuevoBarbero = barberoServiceImpl.guardarBarbero(barbero);


        for (int i = 0; i < 7; i++) {
            LocalDate fecha = LocalDate.now().plusDays(i);
            horarioDisponibleService.crearHorariosParaDiaYBarbero(nuevoBarbero.getIdBarbero(), fecha);
        }

        return nuevoBarbero;
    }



    @DeleteMapping("/{id}")
    public void eliminarBarbero(
            @PathVariable Long id,
            @RequestParam Long idAdministrador
    ) {

        barberoServiceImpl.eliminarBarbero(id);
    }


    @GetMapping
    public List<Barbero> obtenerTodosLosBarberos() {
        return barberoServiceImpl.listarBarberos();
    }

    @PutMapping("/{id}")
    public Barbero actualizarBarbero(@PathVariable Long id, @RequestBody Barbero barbero) {
        barbero.setIdBarbero(id);
        return barberoServiceImpl.guardarBarbero(barbero);
    }

    // Cambiar modalidad de trabajo del barbero
    @PatchMapping("/{id}/modalidad")
    public Barbero cambiarModalidadTrabajo(
            @PathVariable Long id,
            @RequestBody Map<String, String> body
    ) {
        String modalidad = body.get("modalidad");
        
        Barbero barbero = barberoServiceImpl.obtenerBarberoPorId(id);
        if (barbero == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbero no encontrado");
        }

        try {
            ModalidadServicio nuevaModalidad = ModalidadServicio.valueOf(modalidad);
            barbero.setModalidadActual(nuevaModalidad);
            return barberoServiceImpl.guardarBarbero(barbero);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Modalidad inválida. Valores permitidos: PRESENCIAL, DOMICILIO, AMBOS");
        }
    }

    // Actualizar precio adicional por domicilio
    @PatchMapping("/{id}/precio-domicilio")
    public Barbero actualizarPrecioDomicilio(
            @PathVariable Long id,
            @RequestBody Map<String, Double> body
    ) {
        Double precio = body.get("precio");
        
        Barbero barbero = barberoServiceImpl.obtenerBarberoPorId(id);
        if (barbero == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbero no encontrado");
        }

        if (precio == null || precio < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Precio inválido. Debe ser mayor o igual a 0");
        }

        barbero.setPrecioAdicionalDomicilio(precio);
        return barberoServiceImpl.guardarBarbero(barbero);
    }


}
