-- Script para crear administrador
-- Ejecutar en la base de datos de Supabase

-- Insertar administrador juanes con contraseña 123456
INSERT INTO administrador (nombre, usuario, contraseña, correo, rol) 
VALUES ('Juanes Admin', 'juanes', '123456', 'juanes@admin.com', 'ADMIN')
ON CONFLICT (usuario) DO NOTHING;

-- Verificar que se insertó correctamente
SELECT * FROM administrador WHERE usuario = 'juanes';

-- Mostrar todos los administradores existentes
SELECT id_admin, nombre, usuario, contraseña, correo, rol FROM administrador;
