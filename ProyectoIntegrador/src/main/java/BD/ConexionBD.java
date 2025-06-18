/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author matias papu
 */
public class ConexionBD {
    // 1. Atributos de conexión
    private static final String BD = "SistemasCRM_V2"; // nombre de tu base
    private static final String URL = "jdbc:mysql://localhost:3306/" + BD;
    private static final String USER = "root"; // usuario de MySQL
    private static final String PASSWORD = "Ms211596321*"; // contraseña de MySQL

    // 2. Método público para obtener la conexión
    public static Connection conectar() {
        Connection conn = null;

        try {
            // 3. Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println(" Conectando a la base de datos: " + BD);

            // 4. Establecer conexión
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println(" Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.err.println(" Error: No se encontró el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" Error al conectar con la base de datos.");
            e.printStackTrace();
        }

        return conn;
    }
}
