-- Eliminar la base de datos si ya existe
DROP DATABASE IF EXISTS veterinaria_db;

-- Crear la base de datos
CREATE DATABASE veterinaria_db;

-- Usar la base de datos
USE veterinaria_db;

-- ============================================
-- TABLA: MASCOTAS
-- ============================================

-- Eliminar la tabla si ya existe
DROP TABLE IF EXISTS mascotas;

-- Crear la tabla mascotas
CREATE TABLE mascotas (
    id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    peso DECIMAL(10,2) NOT NULL,
    raza VARCHAR(100) NOT NULL,
    dosis DECIMAL(10,2) DEFAULT 0
);

-- ============================================
-- TABLA: VETERINARIOS
-- ============================================

-- Eliminar la tabla si ya existe
DROP TABLE IF EXISTS veterinarios;

-- Crear la tabla veterinarios
CREATE TABLE veterinarios (
    idVeterinario INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(100)
);

-- ============================================
-- 10 REGISTROS DE MASCOTAS
-- ============================================

INSERT INTO mascotas (id, nombre, edad, peso, raza, dosis) VALUES
(1, 'Firulais', 3, 15.5, 'Golden Retriever', 7.75),
(2, 'Luna', 2, 8.2, 'Labrador', 4.10),
(3, 'Max', 5, 22.0, 'Pastor Aleman', 11.00),
(4, 'Bella', 4, 12.3, 'Poodle', 6.15),
(5, 'Rocky', 1, 5.5, 'Bulldog Frances', 2.75),
(6, 'Coco', 6, 18.7, 'Husky Siberiano', 9.35),
(7, 'Nala', 3, 10.2, 'Beagle', 5.10),
(8, 'Toby', 7, 25.0, 'Doberman', 12.50),
(9, 'Simba', 2, 6.8, 'Chihuahua', 3.40),
(10, 'Kiara', 4, 14.5, 'Boxer', 7.25);

-- ============================================
-- 10 REGISTROS DE VETERINARIOS
-- ============================================

INSERT INTO veterinarios (idVeterinario, nombre, especialidad, telefono, email) VALUES
(1, 'Dr. Juan Perez', 'Medicina General', '555-1001', 'juan.perez@vet.com'),
(2, 'Dra. Maria Lopez', 'Cirugia', '555-1002', 'maria.lopez@vet.com'),
(3, 'Dr. Carlos Ruiz', 'Cardiologia', '555-1003', 'carlos.ruiz@vet.com'),
(4, 'Dra. Ana Torres', 'Dermatologia', '555-1004', 'ana.torres@vet.com'),
(5, 'Dr. Luis Fernandez', 'Oftalmologia', '555-1005', 'luis.fernandez@vet.com'),
(6, 'Dra. Sofia Ramirez', 'Nutricion', '555-1006', 'sofia.ramirez@vet.com'),
(7, 'Dr. Miguel Castro', 'Odontologia', '555-1007', 'miguel.castro@vet.com'),
(8, 'Dra. Laura Gomez', 'Rehabilitacion', '555-1008', 'laura.gomez@vet.com'),
(9, 'Dr. Andres Mora', 'Oncologia', '555-1009', 'andres.mora@vet.com'),
(10, 'Dra. Carmen Rios', 'Neurologia', '555-1010', 'carmen.rios@vet.com');

-- ============================================
-- VERIFICAR QUE TODO SE CREO CORRECTAMENTE
-- ============================================

-- Mostrar todas las tablas
SHOW TABLES;

-- Ver datos de mascotas (10 registros)
SELECT * FROM mascotas;

-- Ver datos de veterinarios (10 registros)
SELECT * FROM veterinarios;

-- Contar registros
SELECT 'MASCOTAS' AS Tabla, COUNT(*) AS Total FROM mascotas
UNION
SELECT 'VETERINARIOS' AS Tabla, COUNT(*) AS Total FROM veterinarios;