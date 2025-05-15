package com.example.Barberia.services;

import com.example.Barberia.models.Barbero;
import com.example.Barberia.repositories.BarberoRepository;
import com.example.Barberia.repositories.HorarioDisponibleRepository;
import com.example.Barberia.repositories.ReservaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BarberoServiceImpl implements BarberoService {

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private HorarioDisponibleRepository horarioRepository;

    @Override
    public List<Barbero> listarBarberos() {
        return barberoRepository.findAll();  // Recupera todos los barberos
    }

    @Override
    public Barbero guardarBarbero(Barbero barbero) {
        return barberoRepository.save(barbero);
    }

   /* @Override
    public void eliminarBarbero(Long id) {
        barberoRepository.deleteById(id);
    }*/

    @Override
    @Transactional
    public void eliminarBarbero(Long idBarbero) {
        // 1) Elimina todas las reservas de los horarios de este barbero
        reservaRepository.deleteByHorarioBarberoIdBarbero(idBarbero);

        // 2) Elimina todos los horarios asociadas a este barbero
        horarioRepository.deleteByBarbero_IdBarbero(idBarbero);

        // 3) Finalmente elimina el barbero
        if (!barberoRepository.existsById(idBarbero)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbero no encontrado");
        }
        barberoRepository.deleteById(idBarbero);
    }
}
