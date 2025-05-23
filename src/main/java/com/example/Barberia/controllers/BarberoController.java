package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Barbero;
import com.example.Barberia.repositories.AdministradorRepository;
import com.example.Barberia.services.BarberoServiceImpl;
import com.example.Barberia.services.HorarioDisponibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

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


}
