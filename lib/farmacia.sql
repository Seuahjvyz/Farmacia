
CREATE DATABASE farmacia;
USE farmacia;
SHOW DATABASES;

-- Tabla de roles
CREATE TABLE rol (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

INSERT INTO rol(nombre) VALUES ('Administrador'), ('Vendedor');

-- Tabla de usuarios
CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    user VARCHAR(50) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    activo TINYINT(1) NOT NULL DEFAULT 1,
    id_rol INT NOT NULL,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_rol) REFERENCES rol(id_rol)
);

INSERT INTO usuario(nombre, user, correo, contrasena, activo, id_rol) 
VALUES ('Diego Zaid', 'Zaid-reb', 'dzgr@gmail.com', '123456', 1, 1),
       ('Norma Jimenez', 'normajm', 'norma123@gmail.com', '123456', 1, 2);

select * from usuario;

-- Tabla de productos
CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre_comercial VARCHAR(150) NOT NULL UNIQUE,
    sustancia_activa VARCHAR(150) NOT NULL,
    concentracion VARCHAR(50) NOT NULL,
    contenido_total VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100),
    marca_laboratorio VARCHAR(100) NOT NULL,
    forma_farmaceutica VARCHAR(100) NOT NULL,
    via_administracion VARCHAR(100) NOT NULL,
    presentacion VARCHAR(100) NOT NULL,
    es_patente TINYINT(1) NOT NULL DEFAULT 0,
    requiere_receta TINYINT(1) NOT NULL DEFAULT 0,
    requiere_refrigeracion TINYINT(1) NOT NULL DEFAULT 0,
    ubicacion_anaquel VARCHAR(100),
    fecha_registro datetime not null default current_timestamp;
);

ALTER TABLE producto ADD COLUMN activo TINYINT(1) NOT NULL DEFAULT 1;


-- Tabla de inventario
CREATE TABLE inventario (
    id_inventario INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT NOT NULL,
    lote VARCHAR(50) NOT NULL,
    fecha_caducidad DATE NOT NULL,
    stock_actual INT NOT NULL DEFAULT 0,
    precio_compra DECIMAL(10,2) NOT NULL,
    precio_venta DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto),
    UNIQUE (id_producto, lote)
);

-- Tabla de ventas
CREATE TABLE venta (
    id_venta INT AUTO_INCREMENT PRIMARY KEY,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    id_usuario INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    descuento DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total_con_descuento DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    iva DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    total DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    metodo_pago ENUM('Efectivo', 'Tarjeta', 'Transferencia') NOT NULL,
    estado ENUM('En proceso', 'Activa', 'Cancelada') NOT NULL DEFAULT 'En proceso',
    FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario)
);

-- Detalle de ventas
CREATE TABLE detalle_venta (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_venta INT NOT NULL,
    id_inventario INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_venta) REFERENCES venta(id_venta),
    FOREIGN KEY (id_inventario) REFERENCES inventario(id_inventario)
);

-- Ver todas las tablas creadas
SHOW TABLES;