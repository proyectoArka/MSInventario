-- Script de migración para agregar el campo isDelete a la tabla productos
-- Fecha: 2025-11-15
-- Descripción: Agrega una columna booleana para manejar bajas lógicas de productos

-- Agregar la columna isDelete con valor por defecto false
ALTER TABLE productos
ADD COLUMN is_delete BOOLEAN NOT NULL DEFAULT FALSE;

-- Comentario descriptivo de la columna (opcional, dependiendo del motor de BD)
COMMENT ON COLUMN productos.is_delete IS 'Indica si el producto ha sido dado de baja lógicamente. true = eliminado, false = activo';

-- Verificar que la columna se agregó correctamente
-- SELECT column_name, data_type, column_default
-- FROM information_schema.columns
-- WHERE table_name = 'productos' AND column_name = 'is_delete';

