package controladores;

import entidades.Usuario;
import BD.ConexionBD;
import dao.UsuarioDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PRINCIPIO MVC: Esta clase representa el "Controlador" en el patrón MVC.
 * DAO: Aquí implementamos la lógica para acceder a la tabla USUARIOS.
 * SOLID - SRP: Esta clase tiene una única responsabilidad: gestionar usuarios.
 * SEGURIDAD: Uso de PreparedStatement para prevenir inyección SQL.
 */
public class ControladorUsuarios {

    private Connection conn = ConexionBD.conectar(); // Conexión a la BD 

    /**
     * Valida usuario y clave contra la base de datos.
     * @param idUsuario ID del usuario 
     * @param clave Contraseña del usuario
     * @return objeto Usuario si es válido, null si no existe
     */
    public Usuario validarLogin(String idUsuario, String clave) {
       return  new UsuarioDAO().validarLogin(idUsuario, clave);
    }
    /**
     * Valida las credenciales del usuario.
     * @param usuario Nombre de usuario
     * @param password Contraseña del usuario
     * @return objeto Usuario si es válido, null si no existe
     */
    public Usuario validarCredenciales(String usuario, String password) {
        try {
            String sql = "SELECT * FROM USUARIOS WHERE IDUSUARIO = ? AND CLAVE = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Usuario(
                    rs.getString("IDUSUARIO"),
                    rs.getString("RUT"),
                    rs.getString("IDROL"),
                    rs.getString("IDPAIS"),
                    rs.getString("CLAVE"),
                    rs.getString("NOMBRES"),
                    rs.getString("APELLIDOP"),
                    rs.getString("APELLIDOM"),
                    rs.getString("AREA"),
                    rs.getDate("FECHA_CREACION")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error al validar credenciales: " + e.getMessage());
        }

        return null;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     * @param user Objeto Usuario con datos a insertar
     * @throws SQLException si ocurre un error de BD
     */
   
    
    
}
