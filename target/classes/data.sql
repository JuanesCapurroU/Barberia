-- Script SQL para generar horarios automáticamente al iniciar la aplicación
-- Este script se ejecuta automáticamente si spring.jpa.hibernate.ddl-auto está configurado
-- NOTA: Este script solo se ejecuta si la base de datos está vacía o si se usa create/create-drop

-- Función para generar horarios para un barbero específico
-- Esta función genera horarios de 9:00 AM a 6:00 PM (18:00) con intervalos de 1 hora
-- para los próximos 7 días desde hoy

-- Si quieres usar este script manualmente, descomenta y ajusta el ID del barbero
/*
DO $$
DECLARE
    barbero_record RECORD;
    fecha_actual DATE;
    fecha_objetivo DATE;
    hora_inicio TIME;
    hora_fin TIME;
    fecha_loop DATE;
BEGIN
    -- Obtener la fecha actual y calcular la fecha objetivo (7 días desde hoy)
    fecha_actual := CURRENT_DATE;
    fecha_objetivo := fecha_actual + INTERVAL '7 days';
    
    -- Iterar sobre todos los barberos
    FOR barbero_record IN SELECT "idBarbero" FROM barbero LOOP
        fecha_loop := fecha_actual;
        
        -- Generar horarios para cada día desde hoy hasta 7 días adelante
        WHILE fecha_loop <= fecha_objetivo LOOP
            hora_inicio := '09:00:00'::TIME;
            hora_fin := '10:00:00'::TIME;
            
            -- Generar horarios cada hora desde las 9:00 AM hasta las 6:00 PM
            WHILE hora_inicio < '18:00:00'::TIME LOOP
                -- Insertar horario solo si no existe ya
                INSERT INTO horariodisponible (
                    "idBarbero",
                    fecha,
                    "horaInicio",
                    "horaFin",
                    disponible,
                    modalidad_servicio
                )
                SELECT 
                    barbero_record."idBarbero",
                    fecha_loop,
                    hora_inicio,
                    hora_fin,
                    true,
                    'AMBOS'
                WHERE NOT EXISTS (
                    SELECT 1 
                    FROM horariodisponible 
                    WHERE "idBarbero" = barbero_record."idBarbero"
                    AND fecha = fecha_loop
                    AND "horaInicio" = hora_inicio
                );
                
                -- Avanzar una hora
                hora_inicio := hora_inicio + INTERVAL '1 hour';
                hora_fin := hora_fin + INTERVAL '1 hour';
            END LOOP;
            
            -- Avanzar al siguiente día
            fecha_loop := fecha_loop + INTERVAL '1 day';
        END LOOP;
    END LOOP;
    
    RAISE NOTICE 'Horarios generados exitosamente para todos los barberos';
END $$;
*/

-- NOTA: Este script está comentado porque Spring Boot ejecutará automáticamente
-- la generación de horarios a través del HorarioSchedulerService al iniciar.
-- Si prefieres usar SQL directamente, descomenta el bloque DO $$ arriba.

