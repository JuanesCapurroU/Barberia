package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Cupon;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cupones")
public class CuponController {

    @Autowired
    private CuponService cuponService;

    @Autowired
    private AdministradorService administradorService;

    private void validarAdministrador(Long idAdministrador) {
        Administrador admin = administradorService.obtenerAdministradorPorId(idAdministrador);
        if (admin == null || !"ADMIN".equalsIgnoreCase(admin.getRol())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permisos para realizar esta acción.");
        }
    }

    /**
     * Listar cupones con filtro
     */
    @GetMapping
    public ResponseEntity<List<Cupon>> listarCupones(
            @RequestParam Long idAdministrador,
            @RequestParam(defaultValue = "todos") String filtro) {
        try {
            validarAdministrador(idAdministrador);
            List<Cupon> cupones;
            
            switch (filtro.toLowerCase()) {
                case "activos":
                    cupones = cuponService.listarCuponesActivos();
                    break;
                case "expirados":
                    cupones = cuponService.listarCuponesExpirados();
                    break;
                case "todos":
                default:
                    cupones = cuponService.listarTodosLosCupones();
                    break;
            }
            
            return ResponseEntity.ok(cupones);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al cargar cupones: " + e.getMessage());
        }
    }

    /**
     * Validar un cupón por código (para clientes)
     */
    @GetMapping("/validar/{codigo}")
    public ResponseEntity<Map<String, Object>> validarCupon(@PathVariable String codigo) {
        try {
            Optional<Cupon> cuponOpt = cuponService.validarCupon(codigo.toUpperCase());
            Map<String, Object> response = new HashMap<>();
            
            if (cuponOpt.isPresent()) {
                Cupon cupon = cuponOpt.get();
                response.put("valido", true);
                
                Map<String, Object> cuponData = new HashMap<>();
                cuponData.put("id", cupon.getId());
                cuponData.put("codigo", cupon.getCodigo());
                cuponData.put("porcentajeDescuento", cupon.getPorcentajeDescuento());
                cuponData.put("fechaValidez", formatearFecha(cupon.getFechaValidez()));
                cuponData.put("activo", cupon.getActivo());
                
                response.put("cupon", cuponData);
                return ResponseEntity.ok(response);
            } else {
                response.put("valido", false);
                response.put("message", "Cupón inválido o expirado");
                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("valido", false);
            error.put("message", "Error al validar el cupón: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    /**
     * Actualizar un cupón
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Cupon> actualizarCupon(
            @PathVariable Long id,
            @RequestBody Cupon cupon,
            @RequestParam Long idAdministrador) {
        try {
            validarAdministrador(idAdministrador);
            
            String fechaValidezStr = null;
            if (cupon.getFechaValidez() != null) {
                fechaValidezStr = formatearFecha(cupon.getFechaValidez());
            } else if (cupon.getFechaValidezAsString() != null) {
                fechaValidezStr = cupon.getFechaValidezAsString();
            } else {
                fechaValidezStr = formatearFecha(LocalDate.now().plusDays(30));
            }
            
            Cupon actualizado = cuponService.actualizarCupon(
                id,
                cupon.getCodigo(),
                cupon.getPorcentajeDescuento(),
                fechaValidezStr,
                cupon.getActivo()
            );
            
            return ResponseEntity.ok(actualizado);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al actualizar cupón: " + e.getMessage());
        }
    }

    /**
     * Eliminar un cupón
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCupon(
            @PathVariable Long id,
            @RequestParam Long idAdministrador) {
        try {
            validarAdministrador(idAdministrador);
            cuponService.eliminarCupon(id);
            return ResponseEntity.noContent().build();
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al eliminar cupón: " + e.getMessage());
        }
    }

    /**
     * Crear un nuevo cupón
     */
    @PostMapping
    public ResponseEntity<Cupon> crearCupon(
            @RequestBody Cupon cupon,
            @RequestParam Long idAdministrador) {
        try {
            validarAdministrador(idAdministrador);
            
            String fechaValidezStr = null;
            if (cupon.getFechaValidez() != null) {
                fechaValidezStr = formatearFecha(cupon.getFechaValidez());
            } else if (cupon.getFechaValidezAsString() != null) {
                fechaValidezStr = cupon.getFechaValidezAsString();
            } else {
                fechaValidezStr = formatearFecha(LocalDate.now().plusDays(30));
            }
            
            Cupon nuevoCupon = cuponService.crearCupon(
                cupon.getCodigo(),
                cupon.getPorcentajeDescuento(),
                fechaValidezStr
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCupon);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al crear cupón: " + e.getMessage());
        }
    }

    /**
     * Obtener un cupón por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cupon> obtenerCuponPorId(@PathVariable Long id) {
        try {
            Optional<Cupon> cuponOpt = cuponService.obtenerCuponPorId(id);
            if (cuponOpt.isPresent()) {
                return ResponseEntity.ok(cuponOpt.get());
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "Error al obtener cupón: " + e.getMessage());
        }
    }

    /**
     * Formatear LocalDate a String (DD/MM/YYYY)
     */
    private String formatearFecha(LocalDate fecha) {
        if (fecha == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return fecha.format(formatter);
    }
}

