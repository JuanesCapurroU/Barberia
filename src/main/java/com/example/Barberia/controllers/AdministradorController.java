package com.example.Barberia.controllers;

import com.example.Barberia.models.Administrador;
import com.example.Barberia.models.Cupon;
import com.example.Barberia.services.AdministradorService;
import com.example.Barberia.services.CorreoMasivoService;
import com.example.Barberia.services.CuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @Autowired
    private CorreoMasivoService correoMasivoService;

    @Autowired
    private CuponService cuponService;


    @PostMapping
    public Administrador crearAdministrador(@RequestBody Administrador administrador) {
        return administradorService.guardarAdministrador(administrador);
    }


    @GetMapping
    public List<Administrador> listarAdministradores() {
        return administradorService.listarAdministradores();
    }


    @DeleteMapping("/{id}")
    public void eliminarAdministrador(@PathVariable Long id) {
        administradorService.eliminarAdministrador(id);
    }

    @GetMapping("/{id}")
    public Administrador obtenerAdministrador(@PathVariable Long id) {
        return administradorService.obtenerAdministradorPorId(id);
    }

    @PutMapping("/{id}")
    public Administrador actualizarAdministrador(@PathVariable Long id, @RequestBody Administrador admin) {
        admin.setId_admin(id);
        return administradorService.guardarAdministrador(admin);
    }

    @PostMapping("/correos/informativo")
    public ResponseEntity<?> enviarCorreosInformativos(
            @RequestParam Long idAdministrador,
            @RequestBody Map<String, String> request
    ) {
        try {
            String mensaje = request.get("mensaje");
            if (mensaje == null || mensaje.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "El mensaje es requerido"));
            }

            int enviados = correoMasivoService.enviarCorreosInformativos(mensaje, idAdministrador);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Correos enviados exitosamente",
                    "enviados", enviados
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", "Error al enviar correos: " + e.getMessage()));
        }
    }

    @PostMapping("/correos/promocion")
    public ResponseEntity<?> enviarCorreosPromocion(
            @RequestParam Long idAdministrador,
            @RequestBody Map<String, Object> request
    ) {
        try {
            String nombreCupon = (String) request.get("nombreCupon");
            Object porcentajeObj = request.get("porcentajeDescuento");
            String fechaValidez = (String) request.get("fechaValidez");

            if (nombreCupon == null || nombreCupon.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "El nombre del cupón es requerido"));
            }

            int porcentajeDescuento;
            if (porcentajeObj instanceof Integer) {
                porcentajeDescuento = (Integer) porcentajeObj;
            } else if (porcentajeObj instanceof String) {
                porcentajeDescuento = Integer.parseInt((String) porcentajeObj);
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "El porcentaje de descuento debe ser un número"));
            }

            if (fechaValidez == null || fechaValidez.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "La fecha de validez es requerida"));
            }

            int enviados = correoMasivoService.enviarCorreosPromocion(
                    nombreCupon, porcentajeDescuento, fechaValidez, idAdministrador
            );

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Correos de promoción enviados exitosamente",
                    "enviados", enviados
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", "Error al enviar correos: " + e.getMessage()));
        }
    }

    // Gestión de cupones
    @GetMapping("/cupones")
    public ResponseEntity<?> listarCupones(
            @RequestParam Long idAdministrador,
            @RequestParam(required = false, defaultValue = "todos") String filtro
    ) {
        try {
            List<Cupon> cupones;
            switch (filtro.toLowerCase()) {
                case "activos":
                    cupones = cuponService.listarCuponesActivos();
                    break;
                case "expirados":
                    cupones = cuponService.listarCuponesExpirados();
                    break;
                default:
                    cupones = cuponService.listarTodosLosCupones();
            }
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "cupones", cupones
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", "Error al listar cupones: " + e.getMessage()));
        }
    }

    @GetMapping("/cupones/{id}")
    public ResponseEntity<?> obtenerCupon(
            @RequestParam Long idAdministrador,
            @PathVariable Long id
    ) {
        try {
            Optional<Cupon> cuponOpt = cuponService.obtenerCuponPorId(id);
            if (cuponOpt.isPresent()) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "cupon", cuponOpt.get()
                ));
            } else {
                return ResponseEntity.status(404)
                        .body(Map.of("success", false, "message", "Cupón no encontrado"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", "Error al obtener cupón: " + e.getMessage()));
        }
    }

    @PutMapping("/cupones/{id}")
    public ResponseEntity<?> actualizarCupon(
            @RequestParam Long idAdministrador,
            @PathVariable Long id,
            @RequestBody Map<String, Object> request
    ) {
        try {
            String codigo = (String) request.get("codigo");
            Object porcentajeObj = request.get("porcentajeDescuento");
            String fechaValidez = (String) request.get("fechaValidez");
            Object activoObj = request.get("activo");

            if (codigo == null || porcentajeObj == null || fechaValidez == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "Faltan datos requeridos"));
            }

            int porcentajeDescuento;
            if (porcentajeObj instanceof Integer) {
                porcentajeDescuento = (Integer) porcentajeObj;
            } else if (porcentajeObj instanceof String) {
                porcentajeDescuento = Integer.parseInt((String) porcentajeObj);
            } else {
                return ResponseEntity.badRequest()
                        .body(Map.of("success", false, "message", "El porcentaje debe ser un número"));
            }

            Boolean activo = null;
            if (activoObj != null) {
                if (activoObj instanceof Boolean) {
                    activo = (Boolean) activoObj;
                } else if (activoObj instanceof String) {
                    activo = Boolean.parseBoolean((String) activoObj);
                }
            }

            Cupon cupon = cuponService.actualizarCupon(id, codigo, porcentajeDescuento, fechaValidez, activo);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cupón actualizado exitosamente",
                    "cupon", cupon
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", "Error al actualizar cupón: " + e.getMessage()));
        }
    }

    @DeleteMapping("/cupones/{id}")
    public ResponseEntity<?> eliminarCupon(
            @RequestParam Long idAdministrador,
            @PathVariable Long id
    ) {
        try {
            cuponService.eliminarCupon(id);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Cupón eliminado exitosamente"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(Map.of("success", false, "message", "Error al eliminar cupón: " + e.getMessage()));
        }
    }

}