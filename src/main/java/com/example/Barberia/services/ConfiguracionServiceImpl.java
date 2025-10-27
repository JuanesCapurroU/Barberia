package com.example.Barberia.services;

import com.example.Barberia.models.Configuracion;
import com.example.Barberia.repositories.ConfiguracionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConfiguracionServiceImpl implements ConfiguracionService {

    @Autowired
    private ConfiguracionRepository configuracionRepository;

    private static final String CLAVE_COMISION_ADMIN = "comision_admin";
    private static final String CLAVE_COMISION_BARBERO = "comision_barbero";
    private static final Double COMISION_ADMIN_DEFAULT = 40.0;
    private static final Double COMISION_BARBERO_DEFAULT = 60.0;

    @Override
    @Transactional
    public Configuracion guardarConfiguracion(Configuracion configuracion) {
        return configuracionRepository.save(configuracion);
    }

    @Override
    public Configuracion obtenerPorClave(String clave) {
        return configuracionRepository.findByClave(clave).orElse(null);
    }

    @Override
    public Double obtenerPorcentajeComisionAdmin() {
        Configuracion config = configuracionRepository.findByClave(CLAVE_COMISION_ADMIN)
                .orElse(null);
        
        if (config == null) {
            // Crear configuración por defecto
            config = new Configuracion();
            config.setClave(CLAVE_COMISION_ADMIN);
            config.setValor(COMISION_ADMIN_DEFAULT.toString());
            config.setDescripcion("Porcentaje de comisión del administrador");
            configuracionRepository.save(config);
            return COMISION_ADMIN_DEFAULT;
        }
        
        return Double.parseDouble(config.getValor());
    }

    @Override
    public Double obtenerPorcentajeComisionBarbero() {
        Configuracion config = configuracionRepository.findByClave(CLAVE_COMISION_BARBERO)
                .orElse(null);
        
        if (config == null) {
            // Crear configuración por defecto
            config = new Configuracion();
            config.setClave(CLAVE_COMISION_BARBERO);
            config.setValor(COMISION_BARBERO_DEFAULT.toString());
            config.setDescripcion("Porcentaje de comisión del barbero");
            configuracionRepository.save(config);
            return COMISION_BARBERO_DEFAULT;
        }
        
        return Double.parseDouble(config.getValor());
    }

    @Override
    @Transactional
    public void actualizarComisiones(Double porcentajeAdmin, Double porcentajeBarbero) {
        // Validar que sumen 100%
        if (porcentajeAdmin + porcentajeBarbero != 100.0) {
            throw new IllegalArgumentException("Las comisiones deben sumar 100%");
        }

        // Actualizar comisión del admin
        Configuracion configAdmin = configuracionRepository.findByClave(CLAVE_COMISION_ADMIN)
                .orElse(new Configuracion());
        configAdmin.setClave(CLAVE_COMISION_ADMIN);
        configAdmin.setValor(porcentajeAdmin.toString());
        configAdmin.setDescripcion("Porcentaje de comisión del administrador");
        configuracionRepository.save(configAdmin);

        // Actualizar comisión del barbero
        Configuracion configBarbero = configuracionRepository.findByClave(CLAVE_COMISION_BARBERO)
                .orElse(new Configuracion());
        configBarbero.setClave(CLAVE_COMISION_BARBERO);
        configBarbero.setValor(porcentajeBarbero.toString());
        configBarbero.setDescripcion("Porcentaje de comisión del barbero");
        configuracionRepository.save(configBarbero);
    }
}

