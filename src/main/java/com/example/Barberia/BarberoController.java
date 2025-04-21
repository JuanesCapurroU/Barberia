package com.example.Barberia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/barberos")
@CrossOrigin(origins = "*")
public class BarberoController {

    @Autowired
    private BarberoService barberoService;

    @PostMapping
    public Barbero guardarBarbero(@RequestBody Barbero barbero) {
        return barberoService.guardarBarbero(barbero);
    }

    @GetMapping
    public List<Barbero> listarBarberos() {
        return barberoService.listarBarberos();
    }

    @GetMapping("/{id}")
    public Barbero obtenerBarberoPorId(@PathVariable Long id) {
        return barberoService.obtenerBarberoPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminarBarbero(@PathVariable Long id) {
        barberoService.eliminarBarbero(id);
    }
}
