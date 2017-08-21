-- CREAR TABLESPACE CON EL NOMBRE ejercicio1
CREATE TABLESPACE tarea
logging
datafile '/home/oracle/databases/tarea.dbf'
size 32m
autoextend off
;

-- CREAR USUARIOS PARA MANEJAR EL TS
CREATE USER usuario1 identified by donbosco
default tablespace tarea
;

CREATE USER usuario2 identified by donbosco
default tablespace tarea
;

--DARLES PERMISOS DE DBA A LOS USUARIOS
GRANT DBA TO usuario1, usuario2;

--
-- Estructura de tabla para la tabla `proveedores`
--

CREATE TABLE proveedores (
  id_proveedor number(5) primary key,
  nombre varchar2(200) NOT NULL,
  telefono varchar2(200) NOT NULL,
  direccion varchar2(200) NOT NULL,
  email varchar2(200) NOT NULL
  );

--CREANDO LA SECUENCIA PARA PROVEEDORES
CREATE SEQUENCE PROVEEDORES_ID;

--CREANDO EL TRIGGER UTILIZANDO LA SENTENCIA
CREATE OR REPLACE TRIGGER PROVEEDORES_AUTO 
BEFORE INSERT ON PROVEEDORES
FOR EACH ROW
WHEN (new.id_proveedor IS NULL)
BEGIN
  SELECT PROVEEDORES_ID.NEXTVAL
  INTO   :new.id_proveedor
  FROM   dual;
END;

--enable the trigger
ALTER TRIGGER PROVEEDORES_AUTO ENABLE ;
--
-- Estructura de tabla para la tabla `discos`
--

CREATE TABLE discos (
  id_disco number(5) primary key,
  nombre varchar(200) NOT NULL,
  autor varchar(200) NOT NULL,
  genero varchar(200) NOT NULL,
  precio number(6,2) NOT NULL,
  id_proveedor number(5) NOT NULL,
  CONSTRAINT fk_discos_proveedores FOREIGN KEY (id_proveedor)
  REFERENCES proveedores(id_proveedor)
);

--CREANDO LA SECUENCIA PARA DISCOS  
CREATE SEQUENCE DISCOS_ID;

--CREANDO EL TRIGGER UTILIZANDO LA SENTENCIA
CREATE OR REPLACE TRIGGER DISCOS_AUTO 
BEFORE INSERT ON DISCOS
FOR EACH ROW
WHEN (new.id_disco IS NULL)
BEGIN
  SELECT DISCOS_ID.NEXTVAL
  INTO   :new.id_disco
  FROM   dual;
END;

--enable the trigger
ALTER TRIGGER DISCOS_AUTO ENABLE ;
  
--
-- Volcado de datos para la tabla `proveedores`
--

INSERT INTO proveedores (id_proveedor, nombre, telefono, direccion, email) VALUES
(null, 'Luis', '12345678', 'Calle, casa', 'luis.calle@gmail.com');
INSERT INTO proveedores (id_proveedor, nombre, telefono, direccion, email) VALUES
(null, 'Mari', '12345678', 'Calle. casa', 'mari.casa@gmail.com');

--
-- Volcado de datos para la tabla `discos`
--

INSERT INTO discos (id_disco, nombre, autor, genero, precio, id_proveedor) VALUES
(null, 'Boro boro~na Ikizama', 'Aki Akane', 'J-Rock', 300.00, 1);
INSERT INTO discos (id_disco, nombre, autor, genero, precio, id_proveedor) VALUES
(null, 'APOCALYPSE - the Resurrection of Notes -', 'Rose Noire', 'Visual Kei', 280.00, 2);
INSERT INTO discos (id_disco, nombre, autor, genero, precio, id_proveedor) VALUES
(null, 'Cant Stop', 'CNBLUE', 'K-Pop', 600.00, 1);

COMMIT;