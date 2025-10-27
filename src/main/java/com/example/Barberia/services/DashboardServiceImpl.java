package com.example.Barberia.services;

import com.example.Barberia.dto.DashboardEstadisticas;
import com.example.Barberia.dto.EstadisticasBarbero;
import com.example.Barberia.dto.ReservaPorMes;
import com.example.Barberia.models.Barbero;
import com.example.Barberia.models.Reserva;
import com.example.Barberia.models.Servicio;
import com.example.Barberia.repositories.BarberoRepository;
import com.example.Barberia.repositories.ClienteRepository;
import com.example.Barberia.repositories.ReservaRepository;
import com.example.Barberia.repositories.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private ConfiguracionService configuracionService;

    @Override
    public DashboardEstadisticas obtenerEstadisticasGenerales() {
        // Obtener totales básicos
        Long totalReservas = reservaRepository.count();
        Long totalBarberos = barberoRepository.count();
        Long totalClientes = clienteRepository.count();
        Long totalServicios = servicioRepository.count();

        // Obtener todas las reservas para calcular ingresos
        List<Reserva> reservas = reservaRepository.findAll();
        Double ingresosTotales = calcularIngresosTotales(reservas);

        // Obtener porcentajes de comisión
        Double porcentajeAdmin = configuracionService.obtenerPorcentajeComisionAdmin();
        Double porcentajeBarbero = configuracionService.obtenerPorcentajeComisionBarbero();

        // Calcular comisiones
        Double comisionAdmin = (ingresosTotales * porcentajeAdmin) / 100.0;
        Double comisionBarberos = (ingresosTotales * porcentajeBarbero) / 100.0;

        // Obtener estadísticas por barbero
        List<EstadisticasBarbero> estadisticasBarberos = obtenerEstadisticasPorBarbero();

        // Obtener reservas por mes
        List<ReservaPorMes> reservasPorMes = obtenerReservasPorMes(reservas);

        return new DashboardEstadisticas(
                totalReservas,
                totalBarberos,
                totalClientes,
                totalServicios,
                ingresosTotales,
                comisionAdmin,
                comisionBarberos,
                porcentajeAdmin,
                porcentajeBarbero,
                estadisticasBarberos,
                reservasPorMes
        );
    }

    @Override
    public List<EstadisticasBarbero> obtenerEstadisticasPorBarbero() {
        List<Barbero> barberos = barberoRepository.findAll();
        List<EstadisticasBarbero> estadisticas = new ArrayList<>();

        Double porcentajeAdmin = configuracionService.obtenerPorcentajeComisionAdmin();
        Double porcentajeBarbero = configuracionService.obtenerPorcentajeComisionBarbero();

        for (Barbero barbero : barberos) {
            List<Reserva> reservasBarbero = reservaRepository.findByBarbero_IdBarbero(barbero.getIdBarbero());
            Long totalCortes = (long) reservasBarbero.size();
            Double ingresosGenerados = calcularIngresosTotales(reservasBarbero);
            
            Double comisionBarbero = (ingresosGenerados * porcentajeBarbero) / 100.0;
            Double comisionAdmin = (ingresosGenerados * porcentajeAdmin) / 100.0;

            estadisticas.add(new EstadisticasBarbero(
                    barbero.getIdBarbero(),
                    barbero.getNombre(),
                    totalCortes,
                    ingresosGenerados,
                    comisionBarbero,
                    comisionAdmin
            ));
        }

        return estadisticas;
    }

    @Override
    public EstadisticasBarbero obtenerEstadisticasBarbero(Long idBarbero) {
        Barbero barbero = barberoRepository.findById(idBarbero).orElse(null);
        if (barbero == null) {
            return null;
        }

        List<Reserva> reservasBarbero = reservaRepository.findByBarbero_IdBarbero(idBarbero);
        Long totalCortes = (long) reservasBarbero.size();
        Double ingresosGenerados = calcularIngresosTotales(reservasBarbero);

        Double porcentajeAdmin = configuracionService.obtenerPorcentajeComisionAdmin();
        Double porcentajeBarbero = configuracionService.obtenerPorcentajeComisionBarbero();

        Double comisionBarbero = (ingresosGenerados * porcentajeBarbero) / 100.0;
        Double comisionAdmin = (ingresosGenerados * porcentajeAdmin) / 100.0;

        return new EstadisticasBarbero(
                barbero.getIdBarbero(),
                barbero.getNombre(),
                totalCortes,
                ingresosGenerados,
                comisionBarbero,
                comisionAdmin
        );
    }

    private Double calcularIngresosTotales(List<Reserva> reservas) {
        double total = 0.0;
        for (Reserva reserva : reservas) {
            Long servicioId = reserva.getServicio().getIdServicio();
            Servicio servicio = servicioRepository.findById(servicioId).orElse(null);
            if (servicio != null && servicio.getPrecio() != null) {
                total += servicio.getPrecio();
            }
        }
        return total;
    }

    private List<ReservaPorMes> obtenerReservasPorMes(List<Reserva> reservas) {
        Map<String, ReservaPorMes> reservasPorMes = new LinkedHashMap<>();

        for (Reserva reserva : reservas) {
            if (reserva.getHorarioDisponible() != null && reserva.getHorarioDisponible().getFecha() != null) {
                try {
                    // La fecha ya es LocalDate, no necesita parsearse
                    LocalDate fecha = reserva.getHorarioDisponible().getFecha();
                    String mes = fecha.format(DateTimeFormatter.ofPattern("yyyy-MM"));

                    ReservaPorMes reservaMes = reservasPorMes.getOrDefault(mes, new ReservaPorMes(mes, 0L, 0.0));
                    reservaMes.setCantidad(reservaMes.getCantidad() + 1);

                    // Calcular ingresos del servicio
                    Long servicioId = reserva.getServicio().getIdServicio();
                    Servicio servicio = servicioRepository.findById(servicioId).orElse(null);
                    if (servicio != null && servicio.getPrecio() != null) {
                        reservaMes.setIngresos(reservaMes.getIngresos() + servicio.getPrecio());
                    }

                    reservasPorMes.put(mes, reservaMes);
                } catch (Exception e) {
                    // Ignorar fechas inválidas
                }
            }
        }

        return new ArrayList<>(reservasPorMes.values());
    }
}

