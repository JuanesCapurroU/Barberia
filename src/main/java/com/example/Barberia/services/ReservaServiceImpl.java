package com.example.Barberia.services;

import com.example.Barberia.models.Barbero;
import com.example.Barberia.models.Reserva;
import com.example.Barberia.models.HorarioDisponible;
import com.example.Barberia.models.Servicio;
import com.example.Barberia.repositories.BarberoRepository;
import com.example.Barberia.repositories.ReservaRepository;
import com.example.Barberia.repositories.HorarioDisponibleRepository;
import com.example.Barberia.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HorarioDisponibleRepository horarioDisponibleRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private BarberoRepository barberoRepository;


    @Override
    public Reserva guardarReserva(Reserva reserva) {
        Reserva saved = reservaRepository.save(reserva);

        Long idHorario = reserva.getHorarioDisponible().getIdHorario();
        HorarioDisponible horario = horarioDisponibleRepository.findById(idHorario)
                .orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        horario.setDisponible(false);
        horarioDisponibleRepository.save(horario);

        // --- Enviar correo al cliente ---
        String asunto = "Confirmación de tu reserva en KALU!";
        String cuerpo = construirCuerpoCorreo(saved);
        emailService.enviarCorreoReserva(saved.getCorreoCliente(), asunto, cuerpo);

        return saved;
    }


    @Override
    public void eliminarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
        HorarioDisponible horario = reserva.getHorarioDisponible();
        horario.setDisponible(true);
        horarioDisponibleRepository.save(horario);
        reservaRepository.deleteById(id);
    }


    @Override
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public List<Reserva> listarReservasPorBarbero(Long idBarbero) {
        return reservaRepository.findByBarbero_IdBarbero(idBarbero);
    }
    private String construirCuerpoCorreo(Reserva reserva) {
        String nombreServicio = servicioRepository.findById(reserva.getServicio().getIdServicio())
                .map(Servicio::getNombreServicio)
                .orElse("Servicio desconocido");

        String nombreBarbero = barberoRepository.findById(reserva.getBarbero().getIdBarbero())
                .map(Barbero::getNombre)
                .orElse("Barbero desconocido");

        String detallesHorario = horarioDisponibleRepository.findById(reserva.getHorarioDisponible().getIdHorario())
                .map(h -> "Fecha: " + h.getFecha() + " Hora: " + h.getHoraInicio())
                .orElse("Horario desconocido");

        StringBuilder sb = new StringBuilder();
        sb.append("Hola ").append(reserva.getNombreCliente()).append(",\n\n")
                .append("¡Gracias por reservar con nosotros!\n\n")
                .append("Detalles de tu reserva:\n")
                .append("Servicio: ").append(nombreServicio).append("\n")
                .append("Barbero: ").append(nombreBarbero).append("\n")
                .append(detallesHorario).append("\n")
                .append("Celular: ").append(reserva.getCelularCliente()).append("\n")
                .append("Correo: ").append(reserva.getCorreoCliente()).append("\n\n")
                .append("¡Te esperamos!\nBarbería Barberi");
        return sb.toString();
    }




}

