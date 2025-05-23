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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));
        HorarioDisponible horario = reserva.getHorarioDisponible();
        if (horario != null) {
            horario.setDisponible(true);
            horarioDisponibleRepository.save(horario);
        }
        reservaRepository.deleteById(id);
    }

    @Override
    public Double calcularTotalDiario(Long idBarbero, LocalDate fecha) {
        Double total = reservaRepository.sumarPreciosPorBarberoFechaYEstado(idBarbero, fecha, "CONFIRMADA");
        return total != null ? total : 0.0;
    }

    @Override
    public Double calcularTotalDiarioPorEstado(Long idBarbero, LocalDate fecha, String estado) {
        Double total = reservaRepository.sumarPreciosPorBarberoFechaYEstado(idBarbero, fecha, estado);
        return total != null ? total : 0.0;
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

        // Puedes reemplazar los enlaces de iconos por los de tus redes reales
        String facebook = "https://facebook.com/";
        String instagram = "https://instagram.com/";
        String whatsapp = "https://wa.me/";

        return """
        <div style="background: #181818; color: #fff; font-family: Arial, sans-serif; padding: 32px; border-radius: 16px; max-width: 500px; margin: auto;">
            <div style="text-align: center;">
                <h1 style="color: #FFD700; margin-bottom: 0;">KALU Barbería</h1>
                <p style="color: #bbb; margin-top: 4px;">¡Gracias por tu reserva!</p>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <p style="font-size: 18px;">Hola <b>%s</b>,<br>
            Tu reserva ha sido registrada exitosamente. Aquí tienes los detalles:</p>
            <ul style="font-size: 16px; line-height: 1.7;">
                <li><b>Servicio:</b> %s</li>
                <li><b>Barbero:</b> %s</li>
                <li><b>%s</b></li>
                <li><b>Celular:</b> %s</li>
                <li><b>Correo:</b> %s</li>
            </ul>
            <div style="text-align: center; margin: 24px 0;">
                <span style="font-size: 22px; color: #FFD700;">¡Te esperamos en KALU!</span>
            </div>
            <hr style="border: 1px solid #FFD700; margin: 24px 0;">
            <div style="text-align: center; margin-bottom: 12px;">
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733547.png" alt="Facebook" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/2111/2111463.png" alt="Instagram" style="vertical-align: middle;" /></a>
                <a href="%s" style="margin: 0 10px;"><img src="https://cdn-icons-png.flaticon.com/32/733/733585.png" alt="WhatsApp" style="vertical-align: middle;" /></a>
            </div>
            <div style="text-align: center; color: #bbb; font-size: 13px;">
                &copy; 2024 KALU Barbería. Todos los derechos reservados.<br>
                Juan Capurro y Juan Bustos
            </div>
        </div>
        """.formatted(
                reserva.getNombreCliente(),
                nombreServicio,
                nombreBarbero,
                detallesHorario,
                reserva.getCelularCliente(),
                reserva.getCorreoCliente(),
                facebook,
                instagram,
                whatsapp
        );
    }

    @Override
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reserva no encontrada"));
    }


    @Override
    public List<Reserva> buscarPorBarberoFechaYEstado(Long idBarbero, LocalDate fecha, String estado) {
        return reservaRepository.findByBarbero_IdBarberoAndHorarioDisponible_FechaAndEstado(idBarbero, fecha, estado);
    }

    @Override
    public List<Reserva> buscarPorBarberoYFecha(Long idBarbero, LocalDate fecha) {
        return reservaRepository.findByBarbero_IdBarberoAndHorarioDisponible_Fecha(idBarbero, fecha);
    }

    @Override
    public Reserva actualizarEstadoReserva(Long id, String estado) {
        Reserva reserva = obtenerReservaPorId(id);
        reserva.setEstado(estado);

        if ("CANCELADA".equalsIgnoreCase(estado)) {
            HorarioDisponible horario = reserva.getHorarioDisponible();
            if (horario != null) {
                horario.setDisponible(true);
                horarioDisponibleRepository.save(horario);
            }
        }

        return reservaRepository.save(reserva);
    }
}

