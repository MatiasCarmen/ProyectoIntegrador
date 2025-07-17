/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;//la libreria lookback
/**
 *
 * @author matias papu
 */
public class ConexionBD {
    private static final Logger logger = LoggerFactory.getLogger(ConexionBD.class); //manda al look que cree para que aparesca en una carpetita

    // 1. Atributos de conexión
    private static final String BD = "proyectointegrador"; // nombre de la base de datos actual
    private static final String URL = "jdbc:mysql://localhost:3306/" + BD;
     private static final String USER = "root"; // modificar solo es para local
    private static final String PASSWORD = "root123*"; // modificar solo es local

    // 2. Método público para obtener la conexión
    public static Connection conectar() {
        Connection conn = null;

        try {
            // 3. Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            logger.info("Conectando a la base de datos: {}", BD);

            // 4. Establecer conexión
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexión exitosa a la base de datos :D");

        } catch (ClassNotFoundException e) {
            logger.error("Error: No se encontró el driver de MySQL", e);
        } catch (SQLException e) {
            logger.error("Error al conectar con la base de datos", e);
        }

        return conn;
    }
}
