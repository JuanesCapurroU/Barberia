package com.example.Barberia.controllers;

import com.example.Barberia.models.Barbero;
import com.example.Barberia.services.BarberoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barberos")
public class BarberoController {

    @Autowired
    private BarberoServiceImpl barberoServiceImpl;

    @PostMapping
    public Barbero guardarBarbero(@RequestBody Barbero barbero, @RequestParam Long idAdministrador) {
        System.out.println("Barbero recibido: " + barbero);
        return barberoServiceImpl.guardarBarbero(barbero);
    }


    @DeleteMapping("/{id}")
    public void eliminarBarbero(@PathVariable Long id, @RequestParam Long idAdministrador) {
        barberoServiceImpl.eliminarBarbero(id);
    }

    @GetMapping
    public List<Barbero> obtenerTodosLosBarberos() {
        return barberoServiceImpl.listarBarberos();  // Llamamos al método listarBarberos
    }
}
