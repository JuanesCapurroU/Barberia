 Script para unificar las tablas cliente y clientes
 Ejecutar este script en tu base de datos de Supabase

 1. Primero, vamos a verificar qué tablas existen
 SELECT table_name FROM information_schema.tables WHERE table_schema = 'public' AND table_name LIKE '%client%';

 2. Si existe una tabla 'cliente' (singular), migrar datos a 'clientes' (plural)
 INSERT INTO clientes (nombre, celular, correo, contraseña, direccion, activo)
 SELECT nombre, celular, correo, contraseña, direccion, true
 FROM cliente
 WHERE NOT EXISTS (
     SELECT 1 FROM clientes WHERE clientes.correo = cliente.correo
 );

 3. Eliminar la tabla 'cliente' si existe
 DROP TABLE IF EXISTS cliente;

 4. Asegurar que la tabla 'reserva' tenga la clave foránea correcta
 ALTER TABLE reserva DROP CONSTRAINT IF EXISTS fkni4948ed2cxhnssm7cuqbm6qv;
 ALTER TABLE reserva ADD CONSTRAINT fk_reserva_cliente 
     FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente);

 5. Limpiar datos huérfanos en reserva si los hay
 DELETE FROM reserva WHERE id_cliente NOT IN (SELECT id_cliente FROM clientes);

 6. Verificar la estructura final
 \d clientes
 \d reserva
