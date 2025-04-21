package com.example.Barberia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barberos")
@CrossOrigin(origins = "*")
public class BarberoControlador {

    @Autowired
    private BarberoServicio barberoServicio;

    @PostMapping
    public Barbero guardarBarbero(@RequestBody Barbero barbero) {
        return barberoServicio.guardarBarbero(barbero);
    }

    @GetMapping
    public List<Barbero> listarBarberos() {
        return barberoServicio.listarBarberos();
    }

    @GetMapping("/{id}")
    public Barbero obtenerBarberoPorId(@PathVariable Long id) {
        return barberoServicio.obtenerBarberoPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarBarbero(@PathVariable Long id) {
        barberoServicio.eliminarBarbero(id);
    }
}
