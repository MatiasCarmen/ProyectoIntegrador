-- Procedimientos almacenados para la gestión de Adicionales

DELIMITER //

-- Crear un nuevo adicional
CREATE PROCEDURE sp_crear_adicional(
    IN p_id_adicionales VARCHAR(255),
    IN p_costo_t DECIMAL(10,2)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al crear el adicional';
    END;

    START TRANSACTION;
    INSERT INTO ADICIONALES (IDADICIONALES, COSTOT)
    VALUES (p_id_adicionales, p_costo_t);
    COMMIT;
END //

-- Obtener adicional por ID
CREATE PROCEDURE sp_obtener_adicional_por_id(
    IN p_id_adicionales VARCHAR(255)
)
BEGIN
    SELECT * FROM ADICIONALES WHERE IDADICIONALES = p_id_adicionales;
END //

-- Listar todos los adicionales
CREATE PROCEDURE sp_listar_adicionales()
BEGIN
    SELECT * FROM ADICIONALES;
END //

-- Actualizar adicional
CREATE PROCEDURE sp_actualizar_adicional(
    IN p_id_adicionales VARCHAR(255),
    IN p_costo_t DECIMAL(10,2)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al actualizar el adicional';
    END;

    START TRANSACTION;
    UPDATE ADICIONALES 
    SET COSTOT = p_costo_t
    WHERE IDADICIONALES = p_id_adicionales;
    COMMIT;
END //

-- Eliminar adicional
CREATE PROCEDURE sp_eliminar_adicional(
    IN p_id_adicionales VARCHAR(255)
)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Error al eliminar el adicional';
    END;

    START TRANSACTION;
    DELETE FROM ADICIONALES WHERE IDADICIONALES = p_id_adicionales;
    COMMIT;
END //

-- Obtener adicional por ID de cuenta
CREATE PROCEDURE sp_obtener_adicional_por_cuenta(
    IN p_id_cuenta VARCHAR(255)
)
BEGIN
    SELECT a.* 
    FROM PRODUCTOS_INSTALADOS pi
    JOIN ADICIONALES a ON pi.IDADICIONALES = a.IDADICIONALES
    WHERE pi.IDCUENTA = p_id_cuenta;
END //

DELIMITER ; 