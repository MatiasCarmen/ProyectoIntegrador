-- Procedimientos almacenados para el sistema CRM
-- Generado automáticamente para la migración de DAOs

DELIMITER //

-- ================================================
-- Procedimientos para ACTIVIDADES
-- ================================================

-- Insertar una nueva actividad
CREATE PROCEDURE insertarActividad(
    IN p_idActividad VARCHAR(50),
    IN p_idCuenta VARCHAR(50),
    IN p_descripcion TEXT,
    IN p_fechaCreacion DATE,
    IN p_fechaCierre DATE,
    IN p_tipo VARCHAR(50),
    IN p_razon TEXT,
    IN p_detalle TEXT,
    IN p_resolucion VARCHAR(50),
    IN p_comentarios TEXT,
    IN p_telefono VARCHAR(50),
    IN p_correo VARCHAR(100),
    IN p_idUsuario VARCHAR(50)
)
BEGIN
    -- LOG: insertando actividad
    INSERT INTO ACTIVIDADES (IDACTIVIDAD, IDCUENTA, DESCRIPCION, FECHA_CREACION, FECHA_CIERRE, TIPO, RAZON, DETALLE, RESOLUCION, COMENTARIOS, TELEFONO, CORREO, IDUSUARIO)
    VALUES (p_idActividad, p_idCuenta, p_descripcion, p_fechaCreacion, p_fechaCierre, p_tipo, p_razon, p_detalle, p_resolucion, p_comentarios, p_telefono, p_correo, p_idUsuario);
END;
//

-- Obtener actividad por IDACTIVIDAD
CREATE PROCEDURE obtenerActividadPorId(
    IN p_idActividad VARCHAR(50)
)
BEGIN
    -- LOG: obteniendo actividad por ID
    SELECT * FROM ACTIVIDADES WHERE IDACTIVIDAD = p_idActividad;
END;
//

-- Obtener actividades por cuenta
CREATE PROCEDURE obtenerActividadesPorCuenta(
    IN p_idCuenta VARCHAR(50)
)
BEGIN
    -- LOG: obteniendo actividades por cuenta
    SELECT * FROM ACTIVIDADES WHERE IDCUENTA = p_idCuenta;
END;
//

-- Obtener actividades por usuario
CREATE PROCEDURE obtenerActividadesPorUsuario(
    IN p_idUsuario VARCHAR(50)
)
BEGIN
    -- LOG: obteniendo actividades por usuario
    SELECT * FROM ACTIVIDADES WHERE IDUSUARIO = p_idUsuario;
END;
//

-- Obtener actividades con cierre futuro
CREATE PROCEDURE obtenerActividadesPendientes()
BEGIN
    -- LOG: obteniendo actividades con fecha de cierre futura
    SELECT * FROM ACTIVIDADES WHERE FECHA_CIERRE >= CURDATE() ORDER BY FECHA_CIERRE ASC;
END;
//

-- Obtener actividades finalizadas
CREATE PROCEDURE obtenerActividadesFinalizadas()
BEGIN
    -- LOG: obteniendo actividades finalizadas
    SELECT * FROM ACTIVIDADES WHERE RESOLUCION = 'FINALIZADO' ORDER BY FECHA_CIERRE DESC;
END;
//

-- Obtener todas las actividades
CREATE PROCEDURE obtenerTodasActividades()
BEGIN
    -- LOG: obteniendo todas las actividades
    SELECT * FROM ACTIVIDADES;
END;
//

-- Actualizar una actividad existente
CREATE PROCEDURE actualizarActividad(
    IN p_descripcion TEXT,
    IN p_fechaCierre DATE,
    IN p_tipo VARCHAR(50),
    IN p_razon TEXT,
    IN p_detalle TEXT,
    IN p_resolucion VARCHAR(50),
    IN p_comentarios TEXT,
    IN p_telefono VARCHAR(50),
    IN p_correo VARCHAR(100),
    IN p_idActividad VARCHAR(50)
)
BEGIN
    -- LOG: actualizando actividad
    UPDATE ACTIVIDADES SET
        DESCRIPCION = p_descripcion,
        FECHA_CIERRE = p_fechaCierre,
        TIPO = p_tipo,
        RAZON = p_razon,
        DETALLE = p_detalle,
        RESOLUCION = p_resolucion,
        COMENTARIOS = p_comentarios,
        TELEFONO = p_telefono,
        CORREO = p_correo
    WHERE IDACTIVIDAD = p_idActividad;
END;
//

-- Eliminar una actividad
CREATE PROCEDURE eliminarActividad(
    IN p_idActividad VARCHAR(50)
)
BEGIN
    -- LOG: eliminando actividad
    DELETE FROM ACTIVIDADES WHERE IDACTIVIDAD = p_idActividad;
END;
//

-- Validar si existe una actividad por ID
CREATE PROCEDURE existeActividad(
    IN p_idActividad VARCHAR(50)
)
BEGIN
    -- LOG: validando existencia de actividad
    SELECT 1 FROM ACTIVIDADES WHERE IDACTIVIDAD = p_idActividad LIMIT 1;
END;
//

-- ================================================
-- Procedimientos para CLIENTES
-- ================================================

-- Procedimiento para insertar un nuevo cliente
CREATE PROCEDURE insertarCliente(
    IN p_rut VARCHAR(10),
    IN p_correo VARCHAR(255),
    IN p_nombres VARCHAR(100),
    IN p_apellidoP VARCHAR(100),
    IN p_apellidoM VARCHAR(100),
    IN p_telefono NUMERIC(9),
    IN p_edad TINYINT,
    IN p_direccion VARCHAR(100),
    IN p_idComuna VARCHAR(20)
)
BEGIN
    INSERT INTO CLIENTES (RUT, CORREO, NOMBRES, APELLIDOP, APELLIDOM, TELEFONO, EDAD, DIRECCION, IDCOMUNA) 
    VALUES (p_rut, p_correo, p_nombres, p_apellidoP, p_apellidoM, p_telefono, p_edad, p_direccion, p_idComuna);
END;
//

-- Procedimiento para obtener un cliente por su RUT
CREATE PROCEDURE obtenerClientePorRut(
    IN p_rut VARCHAR(10)
)
BEGIN
    SELECT * FROM CLIENTES WHERE RUT = p_rut;
END;
//

-- Procedimiento para listar todos los clientes
CREATE PROCEDURE listarClientes()
BEGIN
    SELECT * FROM CLIENTES;
END;
//

-- Procedimiento para actualizar un cliente existente
CREATE PROCEDURE actualizarCliente(
    IN p_correo VARCHAR(255),
    IN p_nombres VARCHAR(100),
    IN p_apellidoP VARCHAR(100),
    IN p_apellidoM VARCHAR(100),
    IN p_telefono NUMERIC(9),
    IN p_edad TINYINT,
    IN p_direccion VARCHAR(100),
    IN p_idComuna VARCHAR(20),
    IN p_rut VARCHAR(10)
)
BEGIN
    UPDATE CLIENTES SET 
        CORREO = p_correo,
        NOMBRES = p_nombres,
        APELLIDOP = p_apellidoP,
        APELLIDOM = p_apellidoM,
        TELEFONO = p_telefono,
        EDAD = p_edad,
        DIRECCION = p_direccion,
        IDCOMUNA = p_idComuna
    WHERE RUT = p_rut;
END;
//

-- Procedimiento para eliminar un cliente por su RUT
CREATE PROCEDURE eliminarCliente(
    IN p_rut VARCHAR(10)
)
BEGIN
    DELETE FROM CLIENTES WHERE RUT = p_rut;
END;
//

-- Procedimiento para buscar clientes por criterio
CREATE PROCEDURE buscarClientesPorCriterio(
    IN p_criterio VARCHAR(255)
)
BEGIN
    SELECT * FROM CLIENTES
    WHERE RUT LIKE CONCAT('%', p_criterio, '%')
    OR NOMBRES LIKE CONCAT('%', p_criterio, '%')
    OR APELLIDOP LIKE CONCAT('%', p_criterio, '%')
    OR APELLIDOM LIKE CONCAT('%', p_criterio, '%')
    OR CORREO LIKE CONCAT('%', p_criterio, '%');
END;
//

-- Procedimiento para búsqueda avanzada de clientes
CREATE PROCEDURE buscarClientesAvanzado(
    IN p_rut VARCHAR(10),
    IN p_nombre VARCHAR(100),
    IN p_apellidoP VARCHAR(100),
    IN p_apellidoM VARCHAR(100),
    IN p_direccion VARCHAR(100),
    IN p_tipoCuenta VARCHAR(20),
    IN p_comuna VARCHAR(100)
)
BEGIN
    SELECT 
        c.NOMBRES, 
        c.APELLIDOP, 
        c.APELLIDOM,
        c.RUT, 
        c.DIRECCION, 
        co.DESCRIPCION as COMUNA,
        COALESCE(cc.CLASE, 'Sin Cuenta') as TIPO_CUENTA,
        cc.IDCUENTA as IDCUENTA_CLIENTE
    FROM CLIENTES c
    LEFT JOIN CUENTAS_CLIENTES cc ON c.RUT = cc.RUT
    LEFT JOIN COMUNAS co ON c.IDCOMUNA = co.IDCOMUNA
    WHERE 1=1
        AND (p_nombre IS NULL OR p_nombre = '' 
             OR CONCAT(c.NOMBRES, ' ', c.APELLIDOP, ' ', c.APELLIDOM) LIKE CONCAT('%', p_nombre, '%')
             OR c.NOMBRES LIKE CONCAT('%', p_nombre, '%'))
        AND (p_rut IS NULL OR p_rut = '' OR c.RUT LIKE CONCAT('%', p_rut, '%'))
        AND (p_apellidoP IS NULL OR p_apellidoP = '' OR c.APELLIDOP LIKE CONCAT('%', p_apellidoP, '%'))
        AND (p_apellidoM IS NULL OR p_apellidoM = '' OR c.APELLIDOM LIKE CONCAT('%', p_apellidoM, '%'))
        AND (p_direccion IS NULL OR p_direccion = '' OR c.DIRECCION LIKE CONCAT('%', p_direccion, '%'))
        AND (p_tipoCuenta IS NULL OR p_tipoCuenta = 'Todos' OR cc.CLASE = p_tipoCuenta)
        AND (p_comuna IS NULL OR p_comuna = 'Todas' OR co.DESCRIPCION = p_comuna)
    ORDER BY c.APELLIDOP, c.APELLIDOM, c.NOMBRES
    LIMIT 1000;
END;
//

-- Procedimiento para contar clientes
CREATE PROCEDURE contarClientes()
BEGIN
    SELECT COUNT(*) FROM CLIENTES;
END;
//

-- ================================================
-- Procedimientos para USUARIOS
-- ================================================

-- Procedimiento para crear un nuevo usuario
CREATE PROCEDURE crearUsuario(
    IN p_idUsuario VARCHAR(20),
    IN p_rut VARCHAR(20),
    IN p_idRol VARCHAR(20),
    IN p_idPais VARCHAR(20),
    IN p_clave VARCHAR(255),
    IN p_nombres VARCHAR(100),
    IN p_apellidoP VARCHAR(100),
    IN p_apellidoM VARCHAR(100),
    IN p_area VARCHAR(20),
    IN p_fechaCreacion DATE
)
BEGIN
    INSERT INTO USUARIOS (IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA, FECHA_CREACION)
    VALUES (p_idUsuario, p_rut, p_idRol, p_idPais, p_clave, p_nombres, p_apellidoP, p_apellidoM, p_area, p_fechaCreacion);
END;
//

-- Procedimiento para validar login
CREATE PROCEDURE obtenerUsuarioPorId(
    IN p_idUsuario VARCHAR(20)
)
BEGIN
    SELECT IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA, FECHA_CREACION 
    FROM USUARIOS 
    WHERE IDUSUARIO = p_idUsuario;
END;
//

-- Procedimiento para listar todos los usuarios
CREATE PROCEDURE listarUsuarios()
BEGIN
    SELECT IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA, FECHA_CREACION 
    FROM USUARIOS;
END;
//

-- Procedimiento para verificar si existe un usuario
CREATE PROCEDURE existeUsuario(
    IN p_idUsuario VARCHAR(20)
)
BEGIN
    SELECT 1 FROM USUARIOS WHERE IDUSUARIO = p_idUsuario LIMIT 1;
END;
//

-- Procedimiento para actualizar un usuario
CREATE PROCEDURE actualizarUsuario(
    IN p_rut VARCHAR(20),
    IN p_idRol VARCHAR(20),
    IN p_idPais VARCHAR(20),
    IN p_clave VARCHAR(255),
    IN p_nombres VARCHAR(100),
    IN p_apellidoP VARCHAR(100),
    IN p_apellidoM VARCHAR(100),
    IN p_area VARCHAR(20),
    IN p_idUsuario VARCHAR(20)
)
BEGIN
    UPDATE USUARIOS 
    SET RUT = p_rut,
        IDROL = p_idRol,
        IDPAIS = p_idPais,
        CLAVE = p_clave,
        NOMBRES = p_nombres,
        APELLIDOP = p_apellidoP,
        APELLIDOM = p_apellidoM,
        AREA = p_area
    WHERE IDUSUARIO = p_idUsuario;
END;
//

-- Eliminar un usuario
CREATE PROCEDURE eliminarUsuario(
    IN p_idUsuario VARCHAR(20)
)
BEGIN
    DELETE FROM USUARIOS WHERE IDUSUARIO = p_idUsuario;
END;
//

-- Validar login de usuario
CREATE PROCEDURE validarLogin(
    IN p_idUsuario VARCHAR(20),
    IN p_clave VARCHAR(255)
)
BEGIN
    SELECT IDUSUARIO, RUT, IDROL, IDPAIS, CLAVE, NOMBRES, APELLIDOP, APELLIDOM, AREA, FECHA_CREACION 
    FROM USUARIOS 
    WHERE IDUSUARIO = p_idUsuario AND CLAVE = p_clave;
END;
//

-- Obtener productos por modalidad
CREATE PROCEDURE obtenerProductosPorModalidad(
    IN p_modalidad VARCHAR(10)
)
BEGIN
    SELECT * FROM PRODUCTOS WHERE MODALIDAD = p_modalidad;
END;
//

-- ================================================
-- Procedimientos para CUENTAS_CLIENTES
-- ================================================

-- Insertar una nueva cuenta de cliente
CREATE PROCEDURE insertarCuentaCliente(
    IN p_idCuenta VARCHAR(20),
    IN p_rut VARCHAR(10),
    IN p_clase VARCHAR(20)
)
BEGIN
    INSERT INTO CUENTAS_CLIENTES (IDCUENTA, RUT, CLASE) 
    VALUES (p_idCuenta, p_rut, p_clase);
END;
//

-- Obtener cuenta por ID
CREATE PROCEDURE obtenerCuentaPorId(
    IN p_idCuenta VARCHAR(20)
)
BEGIN
    SELECT * FROM CUENTAS_CLIENTES WHERE IDCUENTA = p_idCuenta;
END;
//

-- Listar todas las cuentas
CREATE PROCEDURE listarCuentas()
BEGIN
    SELECT * FROM CUENTAS_CLIENTES;
END;
//

-- Obtener cuentas por RUT de cliente
CREATE PROCEDURE obtenerCuentasPorRut(
    IN p_rut VARCHAR(10)
)
BEGIN
    SELECT * FROM CUENTAS_CLIENTES WHERE RUT = p_rut;
END;
//

-- Actualizar una cuenta
CREATE PROCEDURE actualizarCuentaCliente(
    IN p_rut VARCHAR(10),
    IN p_clase VARCHAR(20),
    IN p_idCuenta VARCHAR(20)
)
BEGIN
    UPDATE CUENTAS_CLIENTES 
    SET RUT = p_rut,
        CLASE = p_clase
    WHERE IDCUENTA = p_idCuenta;
END;
//

-- Eliminar una cuenta
CREATE PROCEDURE eliminarCuentaCliente(
    IN p_idCuenta VARCHAR(20)
)
BEGIN
    DELETE FROM CUENTAS_CLIENTES WHERE IDCUENTA = p_idCuenta;
END;
//

-- ================================================
-- Procedimientos para COMUNAS
-- ================================================

-- Insertar una nueva comuna
CREATE PROCEDURE insertarComuna(
    IN p_idComuna VARCHAR(20),
    IN p_descripcion VARCHAR(100)
)
BEGIN
    INSERT INTO COMUNAS (IDCOMUNA, DESCRIPCION) 
    VALUES (p_idComuna, p_descripcion);
END;
//

-- Obtener comuna por ID
CREATE PROCEDURE obtenerComunaPorId(
    IN p_idComuna VARCHAR(20)
)
BEGIN
    SELECT * FROM COMUNAS WHERE IDCOMUNA = p_idComuna;
END;
//

-- Listar todas las comunas
CREATE PROCEDURE listarComunas()
BEGIN
    SELECT * FROM COMUNAS ORDER BY DESCRIPCION;
END;
//

-- Actualizar una comuna
CREATE PROCEDURE actualizarComuna(
    IN p_descripcion VARCHAR(100),
    IN p_idComuna VARCHAR(20)
)
BEGIN
    UPDATE COMUNAS 
    SET DESCRIPCION = p_descripcion
    WHERE IDCOMUNA = p_idComuna;
END;
//

-- Eliminar una comuna
CREATE PROCEDURE eliminarComuna(
    IN p_idComuna VARCHAR(20)
)
BEGIN
    DELETE FROM COMUNAS WHERE IDCOMUNA = p_idComuna;
END;
//

-- ================================================
-- Procedimientos para ROLES
-- ================================================

-- Insertar un nuevo rol
CREATE PROCEDURE insertarRol(
    IN p_idRol VARCHAR(20),
    IN p_descripcion VARCHAR(100)
)
BEGIN
    INSERT INTO ROLES (IDROL, DESCRIPCION) 
    VALUES (p_idRol, p_descripcion);
END;
//

-- Obtener rol por ID
CREATE PROCEDURE obtenerRolPorId(
    IN p_idRol VARCHAR(20)
)
BEGIN
    SELECT * FROM ROLES WHERE IDROL = p_idRol;
END;
//

-- Listar todos los roles
CREATE PROCEDURE listarRoles()
BEGIN
    SELECT * FROM ROLES;
END;
//

-- Actualizar un rol
CREATE PROCEDURE actualizarRol(
    IN p_descripcion VARCHAR(100),
    IN p_idRol VARCHAR(20)
)
BEGIN
    UPDATE ROLES 
    SET DESCRIPCION = p_descripcion
    WHERE IDROL = p_idRol;
END;
//

-- Eliminar un rol
CREATE PROCEDURE eliminarRol(
    IN p_idRol VARCHAR(20)
)
BEGIN
    DELETE FROM ROLES WHERE IDROL = p_idRol;
END;
//

-- ================================================
-- Procedimientos para USUARIO_ROL
-- ================================================

-- Insertar un nuevo usuario-rol
CREATE PROCEDURE insertarUsuarioRol(
    IN p_idUsuario VARCHAR(20),
    IN p_idRol VARCHAR(20)
)
BEGIN
    INSERT INTO USUARIO_ROL (IDUSUARIO, IDROL) 
    VALUES (p_idUsuario, p_idRol);
END;
//

-- Obtener roles por usuario
CREATE PROCEDURE obtenerRolesPorUsuario(
    IN p_idUsuario VARCHAR(20)
)
BEGIN
    SELECT r.* 
    FROM USUARIO_ROL ur
    JOIN ROLES r ON ur.IDROL = r.IDROL
    WHERE ur.IDUSUARIO = p_idUsuario;
END;
//

-- Obtener usuarios por rol
CREATE PROCEDURE obtenerUsuariosPorRol(
    IN p_idRol VARCHAR(20)
)
BEGIN
    SELECT u.* 
    FROM USUARIO_ROL ur
    JOIN USUARIOS u ON ur.IDUSUARIO = u.IDUSUARIO
    WHERE ur.IDROL = p_idRol;
END;
//

-- Eliminar un usuario-rol
CREATE PROCEDURE eliminarUsuarioRol(
    IN p_idUsuario VARCHAR(20),
    IN p_idRol VARCHAR(20)
)
BEGIN
    DELETE FROM USUARIO_ROL 
    WHERE IDUSUARIO = p_idUsuario AND IDROL = p_idRol;
END;
//

-- ================================================
-- Procedimientos para PRODUCTOS
-- ================================================

-- Insertar un nuevo producto
CREATE PROCEDURE insertarProducto(
    IN p_idProducto VARCHAR(20),
    IN p_tipo VARCHAR(20),
    IN p_descripcion VARCHAR(100),
    IN p_modalidad VARCHAR(10)
)
BEGIN
    INSERT INTO PRODUCTOS (IDPRODUCTO, TIPO, DESCRIPCION, MODALIDAD)
    VALUES (p_idProducto, p_tipo, p_descripcion, p_modalidad);
END;
//

-- Obtener producto por ID
CREATE PROCEDURE obtenerProductoPorId(
    IN p_idProducto VARCHAR(20)
)
BEGIN
    SELECT * FROM PRODUCTOS WHERE IDPRODUCTO = p_idProducto;
END;
//

-- Listar todos los productos
CREATE PROCEDURE listarProductos()
BEGIN
    SELECT * FROM PRODUCTOS;
END;
//

-- Obtener productos por tipo
CREATE PROCEDURE obtenerProductosPorTipo(
    IN p_tipo VARCHAR(20)
)
BEGIN
    SELECT * FROM PRODUCTOS WHERE TIPO = p_tipo;
END;
//

-- Actualizar un producto
CREATE PROCEDURE actualizarProducto(
    IN p_tipo VARCHAR(20),
    IN p_descripcion VARCHAR(100),
    IN p_modalidad VARCHAR(10),
    IN p_idProducto VARCHAR(20)
)
BEGIN
    UPDATE PRODUCTOS 
    SET TIPO = p_tipo,
        DESCRIPCION = p_descripcion,
        MODALIDAD = p_modalidad
    WHERE IDPRODUCTO = p_idProducto;
END;
//

-- Eliminar un producto
CREATE PROCEDURE eliminarProducto(
    IN p_idProducto VARCHAR(20)
)
BEGIN
    DELETE FROM PRODUCTOS WHERE IDPRODUCTO = p_idProducto;
END;
//

-- ================================================
-- Procedimientos para PAISES
-- ================================================

-- Insertar un nuevo país
CREATE PROCEDURE insertarPais(
    IN p_idPais VARCHAR(20),
    IN p_descripcion VARCHAR(100)
)
BEGIN
    INSERT INTO PAISES (IDPAIS, DESCRIPCION) 
    VALUES (p_idPais, p_descripcion);
END;
//

-- Obtener país por ID
CREATE PROCEDURE obtenerPaisPorId(
    IN p_idPais VARCHAR(20)
)
BEGIN
    SELECT * FROM PAISES WHERE IDPAIS = p_idPais;
END;
//

-- Listar todos los países
CREATE PROCEDURE listarPaises()
BEGIN
    SELECT * FROM PAISES ORDER BY DESCRIPCION;
END;
//

-- Actualizar un país
CREATE PROCEDURE actualizarPais(
    IN p_descripcion VARCHAR(100),
    IN p_idPais VARCHAR(20)
)
BEGIN
    UPDATE PAISES 
    SET DESCRIPCION = p_descripcion
    WHERE IDPAIS = p_idPais;
END;
//

-- Eliminar un país
CREATE PROCEDURE eliminarPais(
    IN p_idPais VARCHAR(20)
)
BEGIN
    DELETE FROM PAISES WHERE IDPAIS = p_idPais;
END;
//

-- ================================================
-- Procedimientos para PLANES
-- ================================================

CREATE PROCEDURE insertarPlan(
    IN p_idPlan VARCHAR(20),
    IN p_nombre VARCHAR(100),
    IN p_costoT DECIMAL(10,2)
)
BEGIN
    INSERT INTO PLANES (IDPLAN, NOMBRE, COSTOT) 
    VALUES (p_idPlan, p_nombre, p_costoT);
END;
//

CREATE PROCEDURE obtenerPlanPorId(
    IN p_idPlan VARCHAR(20)
)
BEGIN
    SELECT * FROM PLANES WHERE IDPLAN = p_idPlan;
END;
//

CREATE PROCEDURE listarPlanes()
BEGIN
    SELECT * FROM PLANES;
END;
//

CREATE PROCEDURE actualizarPlan(
    IN p_nombre VARCHAR(100),
    IN p_costoT DECIMAL(10,2),
    IN p_idPlan VARCHAR(20)
)
BEGIN
    UPDATE PLANES 
    SET NOMBRE = p_nombre,
        COSTOT = p_costoT
    WHERE IDPLAN = p_idPlan;
END;
//

CREATE PROCEDURE eliminarPlan(
    IN p_idPlan VARCHAR(20)
)
BEGIN
    DELETE FROM PLANES WHERE IDPLAN = p_idPlan;
END;
//

-- ================================================
-- Procedimientos para PEDIDOS
-- ================================================

CREATE PROCEDURE insertarPedido(
    IN p_idPedido VARCHAR(20),
    IN p_idCuenta VARCHAR(20),
    IN p_estado VARCHAR(20),
    IN p_fechaCreacion DATE,
    IN p_fechaActualizacion DATE,
    IN p_tipo VARCHAR(20),
    IN p_modalidad VARCHAR(10),
    IN p_usuarioCreador VARCHAR(20),
    IN p_usuarioModificador VARCHAR(20)
)
BEGIN
    INSERT INTO PEDIDOS (IDPEDIDO, IDCUENTA, ESTADO, FECHA_CREACION, FECHA_ACTUALIZACION, 
                        TIPO, MODALIDAD, USUARIO_CREADOR, USUARIO_MODIFICADOR)
    VALUES (p_idPedido, p_idCuenta, p_estado, p_fechaCreacion, p_fechaActualizacion,
            p_tipo, p_modalidad, p_usuarioCreador, p_usuarioModificador);
END;
//

CREATE PROCEDURE obtenerPedidoPorId(
    IN p_idPedido VARCHAR(20)
)
BEGIN
    SELECT * FROM PEDIDOS WHERE IDPEDIDO = p_idPedido;
END;
//

CREATE PROCEDURE listarPedidos()
BEGIN
    SELECT * FROM PEDIDOS;
END;
//

CREATE PROCEDURE obtenerPedidosPorCuenta(
    IN p_idCuenta VARCHAR(20)
)
BEGIN
    SELECT * FROM PEDIDOS WHERE IDCUENTA = p_idCuenta;
END;
//

CREATE PROCEDURE actualizarPedido(
    IN p_estado VARCHAR(20),
    IN p_fechaActualizacion DATE,
    IN p_tipo VARCHAR(20),
    IN p_modalidad VARCHAR(10),
    IN p_usuarioModificador VARCHAR(20),
    IN p_idPedido VARCHAR(20)
)
BEGIN
    UPDATE PEDIDOS 
    SET ESTADO = p_estado,
        FECHA_ACTUALIZACION = p_fechaActualizacion,
        TIPO = p_tipo,
        MODALIDAD = p_modalidad,
        USUARIO_MODIFICADOR = p_usuarioModificador
    WHERE IDPEDIDO = p_idPedido;
END;
//

CREATE PROCEDURE eliminarPedido(
    IN p_idPedido VARCHAR(20)
)
BEGIN
    DELETE FROM PEDIDOS WHERE IDPEDIDO = p_idPedido;
END;
//

-- ================================================
-- Procedimientos para FUNCIONES
-- ================================================

CREATE PROCEDURE insertarFuncion(
    IN p_idFuncion VARCHAR(20),
    IN p_descripcion VARCHAR(100)
)
BEGIN
    INSERT INTO FUNCIONES (IDFUNCION, DESCRIPCION) 
    VALUES (p_idFuncion, p_descripcion);
END;
//

CREATE PROCEDURE obtenerFuncionPorId(
    IN p_idFuncion VARCHAR(20)
)
BEGIN
    SELECT * FROM FUNCIONES WHERE IDFUNCION = p_idFuncion;
END;
//

CREATE PROCEDURE listarFunciones()
BEGIN
    SELECT * FROM FUNCIONES;
END;
//

CREATE PROCEDURE actualizarFuncion(
    IN p_descripcion VARCHAR(100),
    IN p_idFuncion VARCHAR(20)
)
BEGIN
    UPDATE FUNCIONES 
    SET DESCRIPCION = p_descripcion
    WHERE IDFUNCION = p_idFuncion;
END;
//

CREATE PROCEDURE eliminarFuncion(
    IN p_idFuncion VARCHAR(20)
)
BEGIN
    DELETE FROM FUNCIONES WHERE IDFUNCION = p_idFuncion;
END;
//

-- ================================================
-- Procedimientos para ROL_FUNCION
-- ================================================

CREATE PROCEDURE insertarRolFuncion(
    IN p_idRol VARCHAR(20),
    IN p_idFuncion VARCHAR(20)
)
BEGIN
    INSERT INTO ROL_FUNCION (IDROL, IDFUNCION) 
    VALUES (p_idRol, p_idFuncion);
END;
//

CREATE PROCEDURE obtenerFuncionesPorRol(
    IN p_idRol VARCHAR(20)
)
BEGIN
    SELECT f.* 
    FROM ROL_FUNCION rf
    JOIN FUNCIONES f ON rf.IDFUNCION = f.IDFUNCION
    WHERE rf.IDROL = p_idRol;
END;
//

CREATE PROCEDURE obtenerRolesPorFuncion(
    IN p_idFuncion VARCHAR(20)
)
BEGIN
    SELECT r.* 
    FROM ROL_FUNCION rf
    JOIN ROLES r ON rf.IDROL = r.IDROL
    WHERE rf.IDFUNCION = p_idFuncion;
END;
//

CREATE PROCEDURE eliminarRolFuncion(
    IN p_idRol VARCHAR(20),
    IN p_idFuncion VARCHAR(20)
)
BEGIN
    DELETE FROM ROL_FUNCION 
    WHERE IDROL = p_idRol AND IDFUNCION = p_idFuncion;
END;
//

-- Procedimientos almacenados para OFERTAS

-- Crear una nueva oferta
DELIMITER //
CREATE PROCEDURE sp_crear_oferta(
    IN p_idoferta VARCHAR(255),
    IN p_idcuenta VARCHAR(255),
    IN p_descripcion VARCHAR(255),
    IN p_fechainicio DATE,
    IN p_fechafin DATE,
    IN p_porcentajedescuento DOUBLE,
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO OFERTAS (IDOFERTA, IDCUENTA, DESCRIPCION, FECHAINICIO, FECHAFIN, PORCENTAJEDESCUENTO)
    VALUES (p_idoferta, p_idcuenta, p_descripcion, p_fechainicio, p_fechafin, p_porcentajedescuento);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Obtener oferta por ID
DELIMITER //
CREATE PROCEDURE sp_obtener_oferta_por_id(
    IN p_idoferta VARCHAR(255)
)
BEGIN
    SELECT * FROM OFERTAS WHERE IDOFERTA = p_idoferta;
END //
DELIMITER ;

-- Listar todas las ofertas
DELIMITER //
CREATE PROCEDURE sp_listar_ofertas()
BEGIN
    SELECT * FROM OFERTAS;
END //
DELIMITER ;

-- Actualizar oferta
DELIMITER //
CREATE PROCEDURE sp_actualizar_oferta(
    IN p_idoferta VARCHAR(255),
    IN p_idcuenta VARCHAR(255),
    IN p_descripcion VARCHAR(255),
    IN p_fechainicio DATE,
    IN p_fechafin DATE,
    IN p_porcentajedescuento DOUBLE,
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    UPDATE OFERTAS 
    SET IDCUENTA = p_idcuenta,
        DESCRIPCION = p_descripcion,
        FECHAINICIO = p_fechainicio,
        FECHAFIN = p_fechafin,
        PORCENTAJEDESCUENTO = p_porcentajedescuento
    WHERE IDOFERTA = p_idoferta;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Eliminar oferta
DELIMITER //
CREATE PROCEDURE sp_eliminar_oferta(
    IN p_idoferta VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM OFERTAS WHERE IDOFERTA = p_idoferta;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ; 

-- Procedimientos almacenados para MESA_CENTRAL

-- Crear una nueva mesa central
DELIMITER //
CREATE PROCEDURE sp_crear_mesa_central(
    IN p_idactividad VARCHAR(255),
    IN p_telefono BIGINT,
    IN p_lugar VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO MESA_CENTRAL (IDACTIVIDAD, TELEFONO, LUGAR)
    VALUES (p_idactividad, p_telefono, p_lugar);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Obtener mesa central por ID de actividad
DELIMITER //
CREATE PROCEDURE sp_obtener_mesa_central_por_actividad(
    IN p_idactividad VARCHAR(255)
)
BEGIN
    SELECT * FROM MESA_CENTRAL WHERE IDACTIVIDAD = p_idactividad;
END //
DELIMITER ;

-- Actualizar mesa central
DELIMITER //
CREATE PROCEDURE sp_actualizar_mesa_central(
    IN p_idactividad VARCHAR(255),
    IN p_telefono BIGINT,
    IN p_lugar VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    UPDATE MESA_CENTRAL 
    SET TELEFONO = p_telefono,
        LUGAR = p_lugar
    WHERE IDACTIVIDAD = p_idactividad;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Eliminar mesa central
DELIMITER //
CREATE PROCEDURE sp_eliminar_mesa_central(
    IN p_idactividad VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM MESA_CENTRAL WHERE IDACTIVIDAD = p_idactividad;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar todas las mesas centrales
DELIMITER //
CREATE PROCEDURE sp_listar_mesas_centrales()
BEGIN
    SELECT * FROM MESA_CENTRAL;
END //
DELIMITER ;

-- Listar mesas centrales por ID de cuenta
DELIMITER //
CREATE PROCEDURE sp_listar_mesas_centrales_por_cuenta(
    IN p_idcuenta VARCHAR(255)
)
BEGIN
    SELECT M.IDACTIVIDAD, M.TELEFONO, M.LUGAR 
    FROM MESA_CENTRAL AS M 
    INNER JOIN ACTIVIDADES AS A ON M.IDACTIVIDAD = A.IDACTIVIDAD 
    WHERE A.IDCUENTA = p_idcuenta;
END //
DELIMITER ;

-- Filtrar mesas centrales por campo y valor
DELIMITER //
CREATE PROCEDURE sp_filtrar_mesas_centrales(
    IN p_idcuenta VARCHAR(255),
    IN p_campo VARCHAR(50),
    IN p_valor VARCHAR(255)
)
BEGIN
    SET @sql = CONCAT('SELECT M.IDACTIVIDAD, M.TELEFONO, M.LUGAR 
                      FROM MESA_CENTRAL AS M 
                      INNER JOIN ACTIVIDADES AS A ON M.IDACTIVIDAD = A.IDACTIVIDAD 
                      WHERE A.IDCUENTA = ? AND M.', p_campo, ' = ?');
    
    PREPARE stmt FROM @sql;
    SET @p1 = p_idcuenta;
    SET @p2 = p_valor;
    EXECUTE stmt USING @p1, @p2;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

-- Filtrar mesas centrales por campo y valor (LIKE)
DELIMITER //
CREATE PROCEDURE sp_filtrar_mesas_centrales_like(
    IN p_idcuenta VARCHAR(255),
    IN p_campo VARCHAR(50),
    IN p_valor VARCHAR(255)
)
BEGIN
    SET @sql = CONCAT('SELECT M.IDACTIVIDAD, M.TELEFONO, M.LUGAR 
                      FROM MESA_CENTRAL AS M 
                      INNER JOIN ACTIVIDADES AS A ON M.IDACTIVIDAD = A.IDACTIVIDAD 
                      WHERE A.IDCUENTA = ? AND M.', p_campo, ' LIKE CONCAT(''%'', ?, ''%'')');
    
    PREPARE stmt FROM @sql;
    SET @p1 = p_idcuenta;
    SET @p2 = p_valor;
    EXECUTE stmt USING @p1, @p2;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ; 

-- Procedimientos almacenados para DETALLE_PLANES

-- Crear un nuevo detalle de plan
DELIMITER //
CREATE PROCEDURE sp_crear_detalle_plan(
    IN p_idplan VARCHAR(255),
    IN p_idproducto VARCHAR(255),
    IN p_costo DECIMAL(10,2),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO DETALLE_PLANES (IDPLAN, IDPRODUCTO, COSTO)
    VALUES (p_idplan, p_idproducto, p_costo);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar detalles por plan
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_por_plan(
    IN p_idplan VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_PLANES WHERE IDPLAN = p_idplan;
END //
DELIMITER ;

-- Eliminar detalles de un plan
DELIMITER //
CREATE PROCEDURE sp_eliminar_detalles_plan(
    IN p_idplan VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM DETALLE_PLANES WHERE IDPLAN = p_idplan;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Obtener detalles de un plan específico
DELIMITER //
CREATE PROCEDURE sp_obtener_detalles_plan(
    IN p_idplan VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_PLANES WHERE IDPLAN = p_idplan;
END //
DELIMITER ; 

-- Procedimientos almacenados para DETALLE_PEDIDOS

-- Crear un nuevo detalle de pedido
DELIMITER //
CREATE PROCEDURE sp_crear_detalle_pedido(
    IN p_idpedido VARCHAR(255),
    IN p_idadicionales VARCHAR(255),
    IN p_idplan VARCHAR(255),
    IN p_iddescuentos VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO DETALLE_PEDIDOS (IDPEDIDO, IDADICIONALES, IDPLAN, IDDESCUENTOS)
    VALUES (p_idpedido, p_idadicionales, p_idplan, p_iddescuentos);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar detalles por pedido
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_por_pedido(
    IN p_idpedido VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_PEDIDOS WHERE IDPEDIDO = p_idpedido;
END //
DELIMITER ;

-- Eliminar detalles de un pedido
DELIMITER //
CREATE PROCEDURE sp_eliminar_detalles_pedido(
    IN p_idpedido VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM DETALLE_PEDIDOS WHERE IDPEDIDO = p_idpedido;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ; 

-- Procedimientos almacenados para DETALLE_DESCUENTOS

-- Crear un nuevo detalle de descuento
DELIMITER //
CREATE PROCEDURE sp_crear_detalle_descuento(
    IN p_iddescuentos VARCHAR(255),
    IN p_idproducto VARCHAR(255),
    IN p_costo DECIMAL(10,2),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO DETALLE_DESCUENTOS (IDDESCUENTOS, IDPRODUCTO, COSTO)
    VALUES (p_iddescuentos, p_idproducto, p_costo);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar detalles por descuento
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_por_descuento(
    IN p_iddescuentos VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_DESCUENTOS WHERE IDDESCUENTOS = p_iddescuentos;
END //
DELIMITER ;

-- Listar detalles por producto
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_por_producto(
    IN p_idproducto VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_DESCUENTOS WHERE IDPRODUCTO = p_idproducto;
END //
DELIMITER ;

-- Eliminar detalle de descuento específico
DELIMITER //
CREATE PROCEDURE sp_eliminar_detalle_descuento(
    IN p_iddescuentos VARCHAR(255),
    IN p_idproducto VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM DETALLE_DESCUENTOS 
    WHERE IDDESCUENTOS = p_iddescuentos 
    AND IDPRODUCTO = p_idproducto;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Obtener detalles de un descuento específico
DELIMITER //
CREATE PROCEDURE sp_obtener_detalles_descuento(
    IN p_iddescuentos VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_DESCUENTOS WHERE IDDESCUENTOS = p_iddescuentos;
END //
DELIMITER ; 

-- Procedimientos almacenados para DETALLE_OFERTAS

-- Crear un nuevo detalle de oferta
DELIMITER //
CREATE PROCEDURE sp_crear_detalle_oferta(
    IN p_idofertas VARCHAR(255),
    IN p_idproducto VARCHAR(255),
    IN p_costo DECIMAL(10,2),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO DETALLE_OFERTAS (IDOFERTAS, IDPRODUCTO, COSTO)
    VALUES (p_idofertas, p_idproducto, p_costo);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar detalles por oferta
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_por_oferta(
    IN p_idofertas VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_OFERTAS WHERE IDOFERTAS = p_idofertas;
END //
DELIMITER ;

-- Listar detalles por producto
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_oferta_por_producto(
    IN p_idproducto VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_OFERTAS WHERE IDPRODUCTO = p_idproducto;
END //
DELIMITER ;

-- Eliminar detalle de oferta específico
DELIMITER //
CREATE PROCEDURE sp_eliminar_detalle_oferta(
    IN p_idofertas VARCHAR(255),
    IN p_idproducto VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM DETALLE_OFERTAS 
    WHERE IDOFERTAS = p_idofertas 
    AND IDPRODUCTO = p_idproducto;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ; 

-- ================================================
-- Procedimientos para DESCUENTOS
-- ================================================

-- Crear un nuevo descuento
DELIMITER //
CREATE PROCEDURE sp_crear_descuento(
    IN p_iddescuentos VARCHAR(255),
    IN p_costot DECIMAL(10,2),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO DESCUENTOS (IDDESCUENTOS, COSTOT)
    VALUES (p_iddescuentos, p_costot);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Obtener descuento por ID
DELIMITER //
CREATE PROCEDURE sp_obtener_descuento_por_id(
    IN p_iddescuentos VARCHAR(255)
)
BEGIN
    SELECT * FROM DESCUENTOS WHERE IDDESCUENTOS = p_iddescuentos;
END //
DELIMITER ;

-- Listar todos los descuentos
DELIMITER //
CREATE PROCEDURE sp_listar_descuentos()
BEGIN
    SELECT * FROM DESCUENTOS;
END //
DELIMITER ;

-- Actualizar descuento
DELIMITER //
CREATE PROCEDURE sp_actualizar_descuento(
    IN p_iddescuentos VARCHAR(255),
    IN p_costot DECIMAL(10,2),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    UPDATE DESCUENTOS 
    SET COSTOT = p_costot
    WHERE IDDESCUENTOS = p_iddescuentos;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Eliminar descuento
DELIMITER //
CREATE PROCEDURE sp_eliminar_descuento(
    IN p_iddescuentos VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM DESCUENTOS WHERE IDDESCUENTOS = p_iddescuentos;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Obtener descuento por ID de cuenta
DELIMITER //
CREATE PROCEDURE sp_obtener_descuento_por_cuenta(
    IN p_idcuenta VARCHAR(255)
)
BEGIN
    SELECT d.* 
    FROM PRODUCTOS_INSTALADOS pi
    JOIN DESCUENTOS d ON pi.IDDESCUENTOS = d.IDDESCUENTOS
    WHERE pi.IDCUENTA = p_idcuenta;
END //
DELIMITER ; 

-- ================================================
-- Procedimientos para DETALLE_ADICIONALES
-- ================================================

-- Crear un nuevo detalle adicional
DELIMITER //
CREATE PROCEDURE sp_crear_detalle_adicional(
    IN p_idadicionales VARCHAR(255),
    IN p_idproducto VARCHAR(255),
    IN p_costo DECIMAL(10,2),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO DETALLE_ADICIONALES (IDADICIONALES, IDPRODUCTO, COSTO)
    VALUES (p_idadicionales, p_idproducto, p_costo);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar detalles por adicional
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_por_adicional(
    IN p_idadicionales VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_ADICIONALES 
    WHERE IDADICIONALES = p_idadicionales;
END //
DELIMITER ;

-- Listar detalles por producto
DELIMITER //
CREATE PROCEDURE sp_listar_detalles_por_producto(
    IN p_idproducto VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_ADICIONALES 
    WHERE IDPRODUCTO = p_idproducto;
END //
DELIMITER ;

-- Eliminar detalle adicional
DELIMITER //
CREATE PROCEDURE sp_eliminar_detalle_adicional(
    IN p_idadicionales VARCHAR(255),
    IN p_idproducto VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM DETALLE_ADICIONALES 
    WHERE IDADICIONALES = p_idadicionales 
    AND IDPRODUCTO = p_idproducto;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Obtener detalles por ID de adicional
DELIMITER //
CREATE PROCEDURE sp_obtener_detalles_adicional(
    IN p_idadicionales VARCHAR(255)
)
BEGIN
    SELECT * FROM DETALLE_ADICIONALES 
    WHERE IDADICIONALES = p_idadicionales;
END //
DELIMITER ; 

-- ================================================
-- Procedimientos para CUENTAS_CLIENTES
-- ================================================

-- Insertar una nueva cuenta de cliente
DELIMITER //
CREATE PROCEDURE sp_insertar_cuenta_cliente(
    IN p_idcuenta VARCHAR(255),
    IN p_rut VARCHAR(20),
    IN p_clase VARCHAR(20),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO CUENTAS_CLIENTES (IDCUENTA, RUT, CLASE)
    VALUES (p_idcuenta, p_rut, p_clase);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar todas las cuentas cliente
DELIMITER //
CREATE PROCEDURE sp_listar_cuentas_cliente()
BEGIN
    SELECT * FROM CUENTAS_CLIENTES;
END //
DELIMITER ;

-- Obtener RUT por ID de cuenta
DELIMITER //
CREATE PROCEDURE sp_obtener_rut_por_idcuenta(
    IN p_idcuenta VARCHAR(255)
)
BEGIN
    SELECT RUT FROM CUENTAS_CLIENTES 
    WHERE IDCUENTA = p_idcuenta;
END //
DELIMITER ;

-- Obtener cuentas de servicio por RUT
DELIMITER //
CREATE PROCEDURE sp_obtener_cuentas_servicio_por_rut(
    IN p_rut VARCHAR(20)
)
BEGIN
    SELECT IDCUENTA 
    FROM CUENTAS_CLIENTES 
    WHERE CLASE = 'SERVICIO' AND RUT = p_rut;
END //
DELIMITER ;

-- Obtener cuenta de servicio asociada
DELIMITER //
CREATE PROCEDURE sp_obtener_cuenta_servicio_asociada(
    IN p_idcuenta_facturacion VARCHAR(255)
)
BEGIN
    SELECT IDCUENTA_SERVICIO 
    FROM CUENTA_SERVICIO_FACTURACION 
    WHERE IDCUENTA_FACTURACION = p_idcuenta_facturacion;
END //
DELIMITER ;

-- Obtener cuenta cliente por ID
DELIMITER //
CREATE PROCEDURE sp_obtener_cuenta_cliente_por_id(
    IN p_idcuenta VARCHAR(255)
)
BEGIN
    SELECT * FROM CUENTAS_CLIENTES 
    WHERE IDCUENTA = p_idcuenta;
END //
DELIMITER ;

-- Obtener ID cuenta servicio desde facturación
DELIMITER //
CREATE PROCEDURE sp_obtener_idcuenta_servicio_desde_facturacion(
    IN p_idcuenta_facturacion VARCHAR(255)
)
BEGIN
    SELECT IDCUENTA_SERVICIO 
    FROM CUENTA_SERVICIO_FACTURACION 
    WHERE IDCUENTA_FACTURACION = p_idcuenta_facturacion;
END //
DELIMITER ;

-- Verificar existencia de ID cuenta
DELIMITER //
CREATE PROCEDURE sp_verificar_existencia_idcuenta(
    IN p_idcuenta VARCHAR(255),
    OUT p_existe BOOLEAN
)
BEGIN
    DECLARE cuenta INT;
    SELECT COUNT(*) INTO cuenta 
    FROM CUENTAS_CLIENTES 
    WHERE IDCUENTA = p_idcuenta;
    SET p_existe = (cuenta > 0);
END //
DELIMITER ; 

-- ================================================
-- Procedimientos para ACTIVIDAD_SOLICITUDES
-- ================================================

-- Crear una nueva relación actividad-solicitud
DELIMITER //
CREATE PROCEDURE sp_crear_actividad_solicitud(
    IN p_idactividad VARCHAR(255),
    IN p_idsolicitud VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    INSERT INTO ACTIVIDAD_SOLICITUDES (IDACTIVIDAD, IDSOLICITUD)
    VALUES (p_idactividad, p_idsolicitud);
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ;

-- Listar relaciones por actividad
DELIMITER //
CREATE PROCEDURE sp_listar_por_actividad(
    IN p_idactividad VARCHAR(255)
)
BEGIN
    SELECT * FROM ACTIVIDAD_SOLICITUDES 
    WHERE IDACTIVIDAD = p_idactividad;
END //
DELIMITER ;

-- Listar relaciones por solicitud
DELIMITER //
CREATE PROCEDURE sp_listar_por_solicitud(
    IN p_idsolicitud VARCHAR(255)
)
BEGIN
    SELECT * FROM ACTIVIDAD_SOLICITUDES 
    WHERE IDSOLICITUD = p_idsolicitud;
END //
DELIMITER ;

-- Eliminar una relación actividad-solicitud
DELIMITER //
CREATE PROCEDURE sp_eliminar_actividad_solicitud(
    IN p_idactividad VARCHAR(255),
    IN p_idsolicitud VARCHAR(255),
    OUT p_resultado BOOLEAN
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        SET p_resultado = FALSE;
        ROLLBACK;
    END;

    START TRANSACTION;
    DELETE FROM ACTIVIDAD_SOLICITUDES 
    WHERE IDACTIVIDAD = p_idactividad 
    AND IDSOLICITUD = p_idsolicitud;
    SET p_resultado = TRUE;
    COMMIT;
END //
DELIMITER ; 