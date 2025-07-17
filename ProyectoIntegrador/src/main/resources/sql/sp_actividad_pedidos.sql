-- Procedimientos almacenados para la gestión de ActividadPedido

DELIMITER //

-- Crear una nueva relación actividad-pedido
CREATE PROCEDURE sp_crear_actividad_pedido(
    IN p_id_actividad VARCHAR(255),
    IN p_id_pedido VARCHAR(255),
    IN p_categoria VARCHAR(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al crear la relación actividad-pedido';
    END;

    START TRANSACTION;
    INSERT INTO ACTIVIDAD_PEDIDOS (IDACTIVIDAD, IDPEDIDO, CATEGORIA)
    VALUES (p_id_actividad, p_id_pedido, p_categoria);
    COMMIT;
END //

-- Listar actividades por pedido
CREATE PROCEDURE sp_listar_actividades_por_pedido(
    IN p_id_pedido VARCHAR(255)
)
BEGIN
    SELECT * FROM ACTIVIDAD_PEDIDOS 
    WHERE IDPEDIDO = p_id_pedido;
END //

-- Listar pedidos por actividad
CREATE PROCEDURE sp_listar_pedidos_por_actividad(
    IN p_id_actividad VARCHAR(255)
)
BEGIN
    SELECT * FROM ACTIVIDAD_PEDIDOS 
    WHERE IDACTIVIDAD = p_id_actividad;
END //

-- Eliminar relación actividad-pedido
CREATE PROCEDURE sp_eliminar_actividad_pedido(
    IN p_id_actividad VARCHAR(255),
    IN p_id_pedido VARCHAR(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al eliminar la relación actividad-pedido';
    END;

    START TRANSACTION;
    DELETE FROM ACTIVIDAD_PEDIDOS 
    WHERE IDACTIVIDAD = p_id_actividad 
    AND IDPEDIDO = p_id_pedido;
    COMMIT;
END //

DELIMITER ; 