/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;
import java.sql.Connection;
/**
 *
 * @author mathi
 */
public class TestConexion {
     public static void main(String[] args) {
        Connection conn = ConexionBD.conectar();

        if (conn != null) {
            System.out.println(" Prueba superada: estas conectado hurra");
        } else {
            System.out.println(" Fallo en la conexión.");
        }
    }
}
