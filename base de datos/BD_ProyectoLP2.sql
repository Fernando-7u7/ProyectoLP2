DROP DATABASE IF EXISTS proyectoFarmacia;
CREATE DATABASE proyectoFarmacia;
USE proyectoFarmacia;

CREATE TABLE TB_ROLES (
    ID_ROL INT PRIMARY KEY,
    DESCRIPCION CHAR(1) NOT NULL
);

INSERT INTO TB_ROLES(ID_ROL,DESCRIPCION)
VALUES
(1,'A'),
(2,'C'),
(3,'F');

CREATE TABLE TB_ESTADO(
	ID_ESTADO INT PRIMARY KEY,
    DESCRIPCION VARCHAR(15) NOT NULL
);

INSERT INTO TB_ESTADO(ID_ESTADO, DESCRIPCION)
VALUES
(1,'ACTIVO'),
(2,'ELIMINADO');

CREATE TABLE TB_CATEGORIA(
	ID_CATEGORIA INT PRIMARY KEY,
    DESCRIPCION VARCHAR(25) NOT NULL
);

INSERT INTO TB_CATEGORIA (ID_CATEGORIA, DESCRIPCION) VALUES
(1, 'Analgesicos'),
(2, 'Antibioticos'),
(3, 'Antiinflamatorios'),
(4, 'Antihistaminicos'),
(5, 'Descongestionantes'),
(6, 'Antiacidos'),
(7, 'Antipireticos'),
(8, 'Antidiabeticos'),
(9, 'Antihipertensivos'),
(10, 'Antidepresivos');

CREATE TABLE TB_USUARIOS (
	ID_USUARIO INT PRIMARY KEY auto_increment,
    NOMBRE VARCHAR(50) NOT NULL,
    APELLIDO VARCHAR(50) NOT NULL,
    CORREO VARCHAR(50) NOT NULL,
    DNI CHAR(8) NOT NULL,
    CLAVE VARCHAR(50) NOT NULL,
    DIRECCION VARCHAR(50) NULL,
    TELEFONO CHAR(9) NOT NULL,
    ROL INT DEFAULT 2,
    ESTADO INT DEFAULT 1,
    FOREIGN KEY (ROL) REFERENCES TB_ROLES(ID_ROL),
    FOREIGN KEY(ESTADO) REFERENCES TB_ESTADO(ID_ESTADO)
);
/*Prueba para meter el dashboard de ventas*/
INSERT INTO TB_USUARIOS (NOMBRE, APELLIDO, CORREO, DNI, CLAVE, DIRECCION, TELEFONO, ROL, ESTADO)
VALUES
('Juan', 'Pérez', 'juan.perez@example.com', '12345678', 'clave123', 'Calle Falsa 123', '987654321', 1, 1),
('María', 'López', 'maria.lopez@example.com', '01234567','clave456', 'Av. Siempre Viva 456', '912345678', 2, 1),
('Ana', 'García', 'ana.garcia@example.com', '32109876', 'clave321', 'Jr. Independencia 789', '934567891', 3, 2),
('Carlos', 'Ramírez', 'carlos.ramirez@example.com', '11223344', 'clavecar1', 'Calle Central 101', '901122334', 3, 1),
('Lucía', 'Fernández', 'lucia.fernandez@example.com', '22334455', 'clavelu2', 'Av. Libertad 202', '902233445', 3, 1),
('Pedro', 'Sánchez', 'pedro.sanchez@example.com', '33445566', 'claveped3', 'Jr. Perú 303', '903344556', 3, 1),
('Valeria', 'Torres', 'valeria.torres@example.com', '44556677', 'claveval4', 'Calle Real 404', '904455667', 3, 2),
('Esteban', 'Morales', 'esteban.morales@example.com', '55667788', 'claveest5', 'Av. Cultura 505', '905566778', 3, 2),
('Julieta', 'Cáceres', 'julieta.caceres@example.com', '66778899', 'clavejul6', 'Jr. Bolognesi 606', '906677889', 3, 1),
('Martín', 'Quispe', 'martin.quispe@example.com', '77889900', 'clavemar7', 'Calle Colmena 707', '907788990', 3, 1),
('Renata', 'Delgado', 'renata.delgado@example.com', '88990011', 'claveren8', 'Av. Grau 808', '908899001', 3, 1),
('Sebastián', 'Rojas', 'sebastian.rojas@example.com', '99001122', 'claveseb9', 'Jr. Cuzco 909', '909900112', 3, 2),
('Camila', 'Vargas', 'camila.vargas@example.com', '10111213', 'clavecam0', 'Calle Amazonas 1010', '910111213', 3, 1);

CREATE TABLE TB_PROVEEDORES (
    ID_PROVEEDOR INT PRIMARY KEY AUTO_INCREMENT,
    RUC CHAR(11) NOT NULL CHECK (RUC REGEXP '^20[0-9]{9}$'),
    RAZON_SOCIAL VARCHAR(100) NOT NULL,
    TELEFONO CHAR(15) NOT NULL,
    DIRECCION VARCHAR(80) NOT NULL,
    ESTADO INT DEFAULT 1,
    FOREIGN KEY (ESTADO) REFERENCES TB_ESTADO(ID_ESTADO)
);

INSERT INTO TB_PROVEEDORES (RUC, RAZON_SOCIAL, TELEFONO, DIRECCION, ESTADO)
VALUES
('20123456789', 'Proveedores Salud S.A.', '0123456789', 'Av. Salud 123, Lima', 1),
('20456789012', 'Medicamentos Perú S.A.', '0987654321', 'Jr. Farmacia 456, Lima', 1),
('20567890123', 'Distribuidora Farma', '0147852369', 'Calle Medicamentos 789, Arequipa', 1),
('20678901234', 'Farmacias Andina S.A.', '0156789123', 'Av. Los Andes 101, Cusco', 1),
('20789012345', 'Proveedora Médica Integral', '0123987456', 'Jr. Salud Pública 202, Trujillo', 1),
('20890123456', 'Distribuciones Sanitas', '0176543210', 'Calle Bienestar 303, Chiclayo', 1),
('20901234567', 'Insumos Médicos Perú', '0167894321', 'Av. Vida Sana 404, Piura', 1),
('20012345678', 'Medicamentos y Más S.A.', '0182345678', 'Jr. Farmacia Central 505, Lima', 1);

CREATE TABLE TB_MEDICAMENTOS (
	ID_MEDICAMENTO CHAR(5) PRIMARY KEY,
    IMAGEN LONGBLOB NULL,
    NOMBRE VARCHAR(50) NOT NULL,
    PRECIO DECIMAL(10,2) NOT NULL,
    STOCK_ACTUAL INT NOT NULL,
    FECHA_VENCIMIENTO DATE NOT NULL,
    ESTADO INT,
    DESCRIPCION TEXT NULL,
    ID_PROVEEDOR INT,
    PREESCRIPCION CHAR(3) CHECK (PREESCRIPCION IN('SRM','CRM')),
    ID_CATEGORIA INT,
    FOREIGN KEY(ID_PROVEEDOR) REFERENCES TB_PROVEEDORES(ID_PROVEEDOR),
	FOREIGN KEY(ESTADO) REFERENCES TB_ESTADO(ID_ESTADO),
    FOREIGN KEY (ID_CATEGORIA) REFERENCES TB_CATEGORIA(ID_CATEGORIA)
);
INSERT INTO TB_MEDICAMENTOS
(ID_MEDICAMENTO, IMAGEN, NOMBRE, PRECIO, STOCK_ACTUAL, FECHA_VENCIMIENTO, ESTADO, DESCRIPCION, ID_PROVEEDOR, PREESCRIPCION, ID_CATEGORIA)
VALUES
('M0001', NULL, 'Paracetamol 500mg',     5.50,  120, '2027-12-31', 1, 'Alivio de dolor y fiebre',         1, 'SRM', 1),
('M0002', NULL, 'Ibuprofeno 400mg',       7.20,   80,'2026-08-15', 1, 'Antiinflamatorio no esteroideo',   1, 'SRM', 3),
('M0003', NULL, 'Amoxicilina 500mg',      12.00,  60, '2025-11-30', 1, 'Antibiótico de amplio espectro',   2, 'CRM', 2),
('M0004', NULL, 'Loratadina 10mg',         8.00,  140, '2028-05-20', 1, 'Antihistamínico para alergias',    3, 'SRM', 4),
('M0005', NULL, 'Ranitidina 150mg',        9.50,   50,  '2025-10-10', 1, 'Antiácido H2',                     2, 'SRM', 6),
('M0006', NULL, 'Metformina 850mg',       15.00,  100, '2027-03-05', 1, 'Glucemia en diabetes tipo 2',      4, 'CRM', 8),
('M0007', NULL, 'Enalapril 10mg',         11.00,   70, '2026-12-12', 1, 'Hipertensión arterial',            5, 'CRM', 9),
('M0008', NULL, 'Fluconazol 150mg',       14.50,   40, '2026-06-06', 1, 'Antifúngico oral',                 2, 'CRM', 2),
('M0009', NULL, 'Omeprazol 20mg',         10.00,  130, '2028-01-01', 1, 'Inhibidor de bomba de protones',   2, 'SRM', 6),
('M0010', NULL, 'Aspirina 100mg',          6.00,  110,  '2027-09-09', 1, 'Analgésico y antiplaquetario',     1, 'SRM', 1),
('M0011', NULL, 'Diclofenaco 50mg',        9.00,   90, '2026-04-22', 1, 'Antiinflamatorio fuerte',          3, 'SRM', 3),
('M0012', NULL, 'Cefalexina 500mg',       13.00,   55, '2025-12-25', 1, 'Antibiótico cefalosporina',        2, 'CRM', 2),
('M0013', NULL, 'Clorfenamina 4mg',        7.50,   75, '2027-07-07', 1, 'Antihistamínico sedante',          3, 'SRM', 4),
('M0014', NULL, 'Pseudoefedrina 60mg',    11.50,   65, '2026-09-09', 1, 'Descongestionante nasal',          3, 'SRM', 5),
('M0015', NULL, 'Esomeprazol 40mg',       16.00,   45,  '2026-11-11', 1, 'IBP para reflujo gastroesofágico',2, 'SRM', 6),
('M0016', NULL, 'Tramadol 50mg',          20.00,   35,   '2025-05-05', 1, 'Analgésico opioide moderado',       5, 'CRM', 1),
('M0017', NULL, 'Prednisona 20mg',        18.00,   30,  '2025-03-15', 1, 'Corticosteroide oral',             4, 'CRM', 3),
('M0018', NULL, 'Insulina NPH 100UI/mL',  50.00,   20,   '2025-09-09', 1, 'Insulina de acción intermedia',    4, 'CRM', 8),
('M0019', NULL, 'Losartán 50mg',          14.00,   85,  '2027-02-02', 1, 'Antihipertensivo ARB',             5, 'CRM', 9),
('M0020', NULL, 'Sertralina 50mg',        19.00,   40, '2026-07-07', 1, 'Antidepresivo ISRS',               5, 'CRM', 10),
('M0021', NULL, 'Claritromicina 500mg',   17.00,   50,  '2025-08-08', 1, 'Antibiótico macrólido',            2, 'CRM', 2),
('M0022', NULL, 'Naproxeno 500mg',        12.50,   95,  '2026-10-10', 1, 'Antiinflamatorio AINES',           3, 'SRM', 3),
('M0023', NULL, 'Cetirizina 10mg',         7.00,  125, '2028-02-02', 1, 'Antihistamínico no sedante',       4, 'SRM', 4),
('M0024', NULL, 'Dextrometorfano 10ml',    8.50,   60, '2027-06-06', 1, 'Supresor de la tos',              1, 'SRM', 5),
('M0025', NULL, 'Budesonida Spray',       22.00,   30,   '2025-07-07', 1, 'Corticosteroide nasal',            4, 'CRM', 4),
('M0026', NULL, 'Clonazepam 0.5mg',       25.00,   28,  '2025-04-04', 1, 'Ansiolítico benzodiacepina',       5, 'CRM', 10),
('M0027', NULL, 'Metoclopramida 10mg',    9.00,   55,  '2026-01-01', 1, 'Antiemético gastroprocinético',    2, 'SRM', 6),
('M0028', NULL, 'Azitromicina 500mg',     18.00,   45, '2025-11-11', 1, 'Antibiótico macrólido',            2, 'CRM', 2),
('M0029', NULL, 'Fexofenadina 120mg',     10.00,   70,  '2028-03-03', 1, 'Antihistamínico no sedante',       4, 'SRM', 4),
('M0030', NULL, 'Clopidogrel 75mg',       20.50,   60, '2027-08-08', 1, 'Antiagregante plaquetario',        1, 'CRM', 1),
('M0031', NULL, 'Simvastatina 20mg',      15.50,   90,  '2026-06-06', 1, 'Lipid lowering (estatinas)',       5, 'CRM', 9),
('M0032', NULL, 'Levotiroxina 50µg',      13.75,   50,  '2026-05-05', 1, 'Hormona tiroidea',                4, 'CRM', 9),
('M0033', NULL, 'Salbutamol Inhaler',     18.50,   40,   '2027-01-01', 1, 'Broncodilatador (asma)',           4, 'SRM', 4),
('M0034', NULL, 'Warfarina 5mg',          22.00,   35,   '2025-10-10', 1, 'Anticoagulante oral',              5, 'CRM', 1),
('M0035', NULL, 'Prednisolona Solución',  16.00,    90, '2026-12-12', 1, 'Corticosteroide sistémico',        4, 'CRM', 3),
('M0036', NULL, 'Rifampicina 300mg',      23.00,     50, '2025-09-09', 1, 'Antibiótico rifamicina',           2, 'CRM', 2),
('M0037', NULL, 'Carvedilol 12.5mg',      17.00,   80, '2027-04-04', 1, 'Betabloqueante antihipertensivo',  5, 'CRM', 9),
('M0038', NULL, 'Pantoprazol 40mg',       14.00,   95, '2027-03-03', 1, 'IBP para úlcera/reflujo',          2, 'SRM', 6),
('M0039', NULL, 'Metamizol 500mg',        6.75,  200, '2028-04-04', 1, 'Analgesico-non opioide',           1, 'SRM', 1),
('M0040', NULL, 'Duloxetina 30mg',        21.00,   70, '2026-02-02', 1, 'Antidepresivo dual',               5, 'CRM', 10),
('M0041', NULL, 'Espironolactona 25mg',   12.00,    120, '2026-07-07', 1, 'Diurético ahorrador de potasio',   5, 'CRM', 9),
('M0042', NULL, 'Nitroglicerina Spray',   25.00,   40,  '2025-08-08', 1, 'Vasodilatador angina',             4, 'SRM', 1),
('M0043', NULL, 'Nifedipino 20mg',        18.75,   75,  '2027-05-05', 1, 'Calcioantagonista antihipertensivo',5,'CRM',9),
('M0044', NULL, 'Fenitoína 100mg',        19.00,   50,  '2026-09-09', 1, 'Antiepiléptico',                   5, 'CRM', 1),
('M0045', NULL, 'Clonidina 0.1mg',        13.00,   55, '2027-11-11', 1, 'Antihipertensivo)',                5, 'CRM',9),
('M0046', NULL, 'Vitamina D 400UI',        8.25,  140, '2028-06-06', 1, 'Suplemento de vitamina D',         1, 'SRM',1),
('M0047', NULL, 'Omega-3 1000mg',         11.00,   200, '2028-07-07', 1, 'Suplemento omega‑3',               1, 'SRM',1),
('M0048', NULL, 'Levamisol 150mg',        14.80,     70, '2026-10-10', 1, 'Antihelmíntico',                   2, 'CRM',2),
('M0049', NULL, 'Propranolol 20mg',       16.50,   60,  '2027-09-09', 1, 'Betabloqueante no selectivo',      5, 'CRM',9),
('M0050', NULL, 'Bromuro de ipratropio 2ml',19.00,  45,  '2026-12-12', 1, 'Broncodilatador anticolinérgico', 4, 'SRM',4);

CREATE TABLE TB_ORDENES_COMPRA (
	ID_ORDEN INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    ID_USUARIO INT NOT NULL,
    FECHA DATE NOT NULL,
    FOREIGN KEY (ID_USUARIO) REFERENCES TB_USUARIOS (ID_USUARIO)
);

CREATE TABLE TB_DETALLE_COMPRA (
    ID_ORDEN INT,
    ID_MEDICAMENTO CHAR(5) NOT NULL,
    CANTIDAD INT NOT NULL,
    PRECIO DECIMAL(10,2) NOT NULL,
    PRIMARY KEY(ID_ORDEN,ID_MEDICAMENTO),
    FOREIGN KEY (ID_ORDEN) REFERENCES TB_ORDENES_COMPRA(ID_ORDEN),
    FOREIGN KEY (ID_MEDICAMENTO) REFERENCES TB_MEDICAMENTOS(ID_MEDICAMENTO)
);

/*Prueba para meter el dashboard de ventas*/
INSERT INTO TB_ORDENES_COMPRA (ID_ORDEN, ID_USUARIO, FECHA)
VALUES
(1001, 1, '2025-06-15'),
(1002, 2, '2025-05-20'),
(1003, 3, '2025-04-22'),
(1004, 2, '2025-03-24'),
(1005, 1, '2025-02-25'),
(1006, 2, '2025-01-23'),
(1007, 2, '2024-12-19'),
(1008, 3, '2024-11-17'),
(1009, 3, '2024-10-17'),
(1010, 3, '2024-09-17'),
(1011, 3, '2024-08-17');

INSERT INTO TB_DETALLE_COMPRA (ID_ORDEN, ID_MEDICAMENTO, CANTIDAD, PRECIO)
VALUES
(1001, 'M0001',  2, 5.50),
(1001, 'M0004',  1, 8.00),
(1002, 'M0003',  1, 12.00),
(1002, 'M0005',  2, 9.50),
(1003, 'M0006',  1, 15.00),
(1004, 'M0002',  3, 7.20),
(1004, 'M0010',  1, 6.00),
(1005, 'M0007', 1, 4.75),
(1005, 'M0002', 2, 7.20),
(1006, 'M0001', 1, 5.50),
(1006, 'M0009', 1, 9.00),
(1007, 'M0006', 2, 15.00),
(1008, 'M0003', 1, 12.00),
(1009, 'M0008', 1, 10.50),
(1009, 'M0010', 1, 10.50),
(1010, 'M0008', 1, 10.50),
(1010, 'M0009', 1, 10.50);


SELECT * FROM TB_ORDENES_COMPRA
SELECT * FROM TB_DETALLE_COMPRA

DELIMITER //
CREATE PROCEDURE SP_CREATE_MEDICINE (
    IN p_IMAGEN LONGBLOB,
    IN p_NOMBRE VARCHAR(50),
    IN p_PRECIO DECIMAL(10,2),
    IN p_STOCK_ACTUAL INT,
    IN p_FECHA_VENCIMIENTO DATE,
    IN p_DESCRIPCION TEXT,
    IN p_ID_PROVEEDOR INT,
    IN p_PREESCRIPCION CHAR(3),
    IN p_ID_CATEGORIA INT
)
BEGIN
    DECLARE NEW_ID CHAR(5);
    SELECT CONCAT('M', LPAD(IFNULL(MAX(CAST(SUBSTRING(ID_MEDICAMENTO, 2) AS UNSIGNED)), 0) + 1, 4, '0'))
    INTO NEW_ID
    FROM TB_MEDICAMENTOS;
    INSERT INTO TB_MEDICAMENTOS (
        ID_MEDICAMENTO, IMAGEN, NOMBRE, PRECIO, STOCK_ACTUAL, FECHA_VENCIMIENTO, ESTADO, DESCRIPCION,
        ID_PROVEEDOR, PREESCRIPCION, ID_CATEGORIA
    )
    VALUES (
        NEW_ID, p_IMAGEN, p_NOMBRE, p_PRECIO, p_STOCK_ACTUAL, p_FECHA_VENCIMIENTO, 1, p_DESCRIPCION,
        p_ID_PROVEEDOR, p_PREESCRIPCION, p_ID_CATEGORIA
    );
END;
// DELIMITER ;
DELIMITER //
CREATE PROCEDURE SP_UPDATE_MEDICINE (
	IN p_ID_MEDICAMENTO CHAR(5),
    IN p_IMAGEN LONGBLOB,
    IN p_NOMBRE VARCHAR(50),
    IN p_PRECIO DECIMAL(10,2),
    IN p_STOCK_ACTUAL INT,
    IN p_FECHA_VENCIMIENTO DATE,
    IN p_DESCRIPCION TEXT,
    IN p_ID_PROVEEDOR INT,
    IN p_PREESCRIPCION CHAR(3),
    IN p_ID_CATEGORIA INT
)
BEGIN
	UPDATE TB_MEDICAMENTOS
    SET	IMAGEN = p_IMAGEN, NOMBRE=p_NOMBRE,PRECIO =p_PRECIO,
		STOCK_ACTUAL =p_STOCK_ACTUAL,
        FECHA_VENCIMIENTO = p_FECHA_VENCIMIENTO, DESCRIPCION = p_DESCRIPCION,
        ID_PROVEEDOR = p_ID_PROVEEDOR, PREESCRIPCION= p_PREESCRIPCION, ID_CATEGORIA = p_ID_CATEGORIA
    WHERE ID_MEDICAMENTO = p_ID_MEDICAMENTO;
END;
// DELIMITER ;