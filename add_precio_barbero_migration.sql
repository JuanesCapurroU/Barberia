-- Agregar columna precio_adicional_domicilio a la tabla barbero
ALTER TABLE barbero 
ADD COLUMN IF NOT EXISTS precio_adicional_domicilio DOUBLE PRECISION DEFAULT 10000;

-- Actualizar barberos existentes que tengan NULL
UPDATE barbero 
SET precio_adicional_domicilio = 10000 
WHERE precio_adicional_domicilio IS NULL;

-- Verificar
SELECT id_barbero, nombre, modalidad_actual, precio_adicional_domicilio 
FROM barbero;


