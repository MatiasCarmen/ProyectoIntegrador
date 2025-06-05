package Controladores;

import java.sql.*;
import Entidades.ConexionDB;
public class ControladorPedidos {
    Connection conn = ConexionDB.conectar();
    public void insertar(String idpedido, String idcuenta, String estado, String fecha_creacion, String fecha_actualizacion, String tipo, String modalidad, String usuario_creador, String usuario_modificador) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO PEDIDOS VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, idpedido);
            ps.setString(2, idcuenta);
            ps.setString(3, estado);
            ps.setString(4, fecha_creacion);
            ps.setString(5, fecha_actualizacion);
            ps.setString(6, tipo);
            ps.setString(7, modalidad);
            ps.setString(8, usuario_creador);
            ps.setString(9, usuario_modificador);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ResultSet consultarTodos() {
        try {
            Statement st = conn.createStatement();
            return st.executeQuery("SELECT * FROM PEDIDOS");
        } catch (SQLException e) { e.printStackTrace(); return null; }
    }

    public void eliminar(String id) {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM PEDIDOS WHERE IDPEDIDO = ?");
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
