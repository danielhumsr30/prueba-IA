-- Datos de prueba para H2 Database
-- Se ejecuta automáticamente después de que Hibernate crea las tablas

-- Insertar estados
INSERT INTO ESTADOS (ID, ESTADO, CATEGORIA) VALUES (1, 'activo', 'usuario');
INSERT INTO ESTADOS (ID, ESTADO, CATEGORIA) VALUES (2, 'inactivo', 'usuario');

-- Insertar usuarios de prueba
-- usuario1 - activo
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) VALUES (1, 'usuario1', 'Usuario Uno', 'usuario1@test.com', 'usuario1', 1);

-- usuario2 - inactivo
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) VALUES (2, 'usuario2', 'Usuario Dos', 'usuario2@test.com', 'usuario2', 2);

-- usuario3 - activo
INSERT INTO USUARIOS (ID, USUARIO, NOMBRE, CORREO, PASSWORD, ESTADO_ID) VALUES (3, 'usuario3', 'Usuario Tres', 'usuario3@test.com', 'usuario3', 1);
