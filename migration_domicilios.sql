-- Script de migración para agregar soporte de servicios a domicilio
-- Ejecutar en PostgreSQL

-- 1. Agregar campo esADomicilio a la tabla reserva
ALTER TABLE reserva 
ADD COLUMN IF NOT EXISTS es_a_domicilio BOOLEAN DEFAULT false NOT NULL;

-- 2. Agregar precio adicional domicilio a la tabla servicio
ALTER TABLE servicio 
ADD COLUMN IF NOT EXISTS precio_adicional_domicilio DOUBLE PRECISION;

-- 3. Agregar modalidad a la tabla horario_disponible
ALTER TABLE horario_disponible 
ADD COLUMN IF NOT EXISTS modalidad_servicio VARCHAR(20) DEFAULT 'AMBOS' NOT NULL;

-- 4. Agregar modalidad actual a la tabla barbero
ALTER TABLE barbero 
ADD COLUMN IF NOT EXISTS modalidad_actual VARCHAR(20) DEFAULT 'AMBOS' NOT NULL;

-- Verificar que las columnas se agregaron correctamente
SELECT column_name, data_type, is_nullable 
FROM information_schema.columns 
WHERE table_name IN ('reserva', 'servicio', 'horario_disponible', 'barbero')
AND column_name IN ('es_a_domicilio', 'precio_adicional_domicilio', 'modalidad_servicio', 'modalidad_actual')
ORDER BY table_name, column_name;


