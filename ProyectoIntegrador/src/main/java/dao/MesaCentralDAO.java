/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author mathi
 */

import BD.ConexionBD;
import entidades.MesaCentral;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – MesaCentralDAO: maneja CRUD de MESA_CENTRAL.
 */
public class MesaCentralDAO {
    private static final Logger LOGGER = Logger.getLogger(MesaCentralDAO.class.getName());

    public boolean crear(MesaCentral m) {
        String sql = "INSERT INTO MESA_CENTRAL (IDACTIVIDAD, TELEFONO, LUGAR) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, m.getIdActividad());
            ps.setLong(2, m.getTelefono());
            ps.setString(3, m.getLugar());
            LOGGER.info("Insertando MesaCentral: actividad=" + m.getIdActividad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crear MesaCentral: " + e.getMessage());
            return false;
        }
    }

    public MesaCentral obtenerPorActividad(String idActividad) {
        String sql = "SELECT * FROM MESA_CENTRAL WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new MesaCentral(
                        rs.getString("IDACTIVIDAD"),
                        rs.getLong("TELEFONO"),
                        rs.getString("LUGAR")
                    );
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtener MesaCentral: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizar(MesaCentral m) {
        String sql = "UPDATE MESA_CENTRAL SET TELEFONO = ?, LUGAR = ? WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, m.getTelefono());
            ps.setString(2, m.getLugar());
            ps.setString(3, m.getIdActividad());
            LOGGER.info("Actualizando MesaCentral: actividad=" + m.getIdActividad());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizar MesaCentral: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(String idActividad) {
        String sql = "DELETE FROM MESA_CENTRAL WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
           	LOGGER.info("Eliminando MesaCentral: actividad=" + idActividad);
           	return ps.executeUpdate() > 0;
        } catch (SQLException e) {
           	LOGGER.severe("Error eliminar MesaCentral: " + e.getMessage());
           	return false;
        }
    }

    public List<MesaCentral> listarTodos() {
        List<MesaCentral> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "SELECT * FROM MESA_CENTRAL";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MesaCentral mc = new MesaCentral();
                mc.setIdActividad(rs.getString("ID_ACTIVIDAD"));
                mc.setTelefono(rs.getLong("TELEFONO")); // Convertir a Long
                mc.setLugar(rs.getString("LUGAR"));
                lista.add(mc);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar mesa central: " + e.getMessage());
        }
        return lista;
    } 
    
      public List<MesaCentral> listarTodosPorIdCuenta(String idCuenta) {
        List<MesaCentral> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "SELECT M.IDACTIVIDAD,M.TELEFONO,M.LUGAR FROM MESA_CENTRAL AS M INNER JOIN " +
"ACTIVIDADES AS A ON M.IDACTIVIDAD = A.IDACTIVIDAD " +
"WHERE A.IDCUENTA = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,idCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                MesaCentral mc = new MesaCentral();
                mc.setIdActividad(rs.getString("M.IDACTIVIDAD"));
                mc.setTelefono(rs.getLong("TELEFONO")); 
                mc.setLugar(rs.getString("LUGAR"));
                lista.add(mc);
            }
        } catch (SQLException e) {
            LOGGER.severe("Error al listar mesa central: " + e.getMessage());
        }
        return lista;
    }
      
      public List<MesaCentral> filtrarPorCampoYValor(String idCuenta, String campo, Object valor) {
    List<MesaCentral> lista = new ArrayList<>();

    List<String> camposPermitidos = List.of("M.LUGAR", "M.TELEFONO", "M.IDACTIVIDAD");
    if (!camposPermitidos.contains("M." + campo.toUpperCase())) {
        return lista;
    }

    String sql = "SELECT M.IDACTIVIDAD, M.TELEFONO, M.LUGAR " +
                 "FROM MESA_CENTRAL AS M " +
                 "INNER JOIN ACTIVIDADES AS A ON M.IDACTIVIDAD = A.IDACTIVIDAD " +
                 "WHERE A.IDCUENTA = ? AND M." + campo + " = ?";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idCuenta);
        if (valor instanceof String) {
            ps.setString(2, (String) valor);
        } else if (valor instanceof Long) {
            ps.setLong(2, (Long) valor);
        } else {
            ps.setObject(2, valor);
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MesaCentral mc = new MesaCentral();
            mc.setIdActividad(rs.getString("IDACTIVIDAD"));
            mc.setTelefono(rs.getLong("TELEFONO"));
            mc.setLugar(rs.getString("LUGAR"));
            lista.add(mc);
        }
    } catch (SQLException e) {
        LOGGER.severe("Error al filtrar mesa central: " + e.getMessage());
    }
    return lista;
}
      
      public List<MesaCentral> filtrarPorCampoYValorLike(String idCuenta, String campo, String valor) {
    List<MesaCentral> lista = new ArrayList<>();
    String sql = "SELECT M.IDACTIVIDAD, M.TELEFONO, M.LUGAR FROM MESA_CENTRAL M " +
                 "JOIN ACTIVIDADES A ON M.IDACTIVIDAD = A.IDACTIVIDAD " +
                 "WHERE A.IDCUENTA = ? AND M." + campo + " LIKE ?";
    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, idCuenta);
        ps.setString(2, "%" + valor + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            MesaCentral mc = new MesaCentral();
            mc.setIdActividad(rs.getString("IDACTIVIDAD"));
            mc.setTelefono(rs.getLong("TELEFONO"));
            mc.setLugar(rs.getString("LUGAR"));
            lista.add(mc);
        }
    } catch (SQLException e) {
        LOGGER.severe("Error al filtrar mesa central: " + e.getMessage());
    }
    return lista;
}

}
