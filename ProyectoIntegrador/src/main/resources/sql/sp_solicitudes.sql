-- Procedimientos almacenados para la gestión de Solicitudes

DELIMITER //

-- Crear una nueva solicitud
CREATE PROCEDURE sp_crear_solicitud(
    IN p_id_solicitud VARCHAR(255),
    IN p_id_cuenta VARCHAR(255),
    IN p_descripcion TEXT,
    IN p_fecha_solicitud DATE,
    IN p_estado VARCHAR(50),
    IN p_comentarios TEXT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al crear la solicitud';
    END;

    START TRANSACTION;
    INSERT INTO SOLICITUDES (IDSOLICITUD, IDCUENTA, DESCRIPCION, FECHASOLICITUD, ESTADO, COMENTARIOS)
    VALUES (p_id_solicitud, p_id_cuenta, p_descripcion, p_fecha_solicitud, p_estado, p_comentarios);
    COMMIT;
END //

-- Obtener solicitud por ID
CREATE PROCEDURE sp_obtener_solicitud_por_id(
    IN p_id_solicitud VARCHAR(255)
)
BEGIN
    SELECT * FROM SOLICITUDES WHERE IDSOLICITUD = p_id_solicitud;
END //

-- Listar todas las solicitudes
CREATE PROCEDURE sp_listar_solicitudes()
BEGIN
    SELECT * FROM SOLICITUDES;
END //

-- Actualizar solicitud
CREATE PROCEDURE sp_actualizar_solicitud(
    IN p_id_solicitud VARCHAR(255),
    IN p_id_cuenta VARCHAR(255),
    IN p_descripcion TEXT,
    IN p_fecha_solicitud DATE,
    IN p_estado VARCHAR(50),
    IN p_comentarios TEXT
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al actualizar la solicitud';
    END;

    START TRANSACTION;
    UPDATE SOLICITUDES 
    SET IDCUENTA = p_id_cuenta,
        DESCRIPCION = p_descripcion,
        FECHASOLICITUD = p_fecha_solicitud,
        ESTADO = p_estado,
        COMENTARIOS = p_comentarios
    WHERE IDSOLICITUD = p_id_solicitud;
    COMMIT;
END //

-- Eliminar solicitud
CREATE PROCEDURE sp_eliminar_solicitud(
    IN p_id_solicitud VARCHAR(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al eliminar la solicitud';
    END;

    START TRANSACTION;
    DELETE FROM SOLICITUDES WHERE IDSOLICITUD = p_id_solicitud;
    COMMIT;
END //

-- Contar solicitudes abiertas
CREATE PROCEDURE sp_contar_solicitudes_abiertas()
BEGIN
    SELECT COUNT(*) as total FROM SOLICITUDES WHERE ESTADO = 'ABIERTA';
END //

DELIMITER ; 