-- 1. Creamos la lista comodín
INSERT INTO listas (nombre) VALUES ('Mis Favoritos');

-- 2. Buscamos a los favoritos huérfanos y les asignamos el ID de la lista recién creada
UPDATE favoritos 
SET lista_id = (SELECT id FROM listas WHERE nombre = 'Mis Favoritos' LIMIT 1)
WHERE lista_id IS NULL;

-- 3. Ahora sí, le decimos a PostgreSQL que de ahora en adelante es obligatorio
ALTER TABLE favoritos 
ALTER COLUMN lista_id SET NOT NULL;
