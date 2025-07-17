/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author mathi
 */
package dao;

import BD.ConexionBD;
import entidades.Actividad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO de ACTIVIDADES: CRUD completo para la tabla ACTIVIDADES.
 */
public class ActividadDAO {
    private static final Logger LOGGER = Logger.getLogger(ActividadDAO.class.getName());

    public boolean crearActividad(Actividad a) {
        String sql = "INSERT INTO ACTIVIDADES "
                + "(IDACTIVIDAD, IDCUENTA, DESCRIPCION, FECHA_CREACION, FECHA_CIERRE,"
                + "TIPO, RAZON, DETALLE, RESOLUCION, COMENTARIOS, TELEFONO, CORREO,IDUSUARIO) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getIdActividad());
            ps.setString(2, a.getIdCuenta());
            ps.setString(3, a.getDescripcion());
            ps.setTimestamp(4, a.getFechaCreacion());
            ps.setTimestamp(5, a.getFechaCierre());
            ps.setString(6, a.getTipo());
            ps.setString(7, a.getRazon());
            ps.setString(8, a.getDetalle());
            ps.setString(9, a.getResolucion());
            ps.setString(10, a.getComentarios());
            ps.setLong(11, a.getTelefono());
            ps.setString(12, a.getCorreo());
            ps.setString(13, a.getIdUsuario());
            int r = ps.executeUpdate();
            LOGGER.info("Filas insertadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error crearActividad: " + e.getMessage());
            return false;
        }
    }

    public List<Actividad> listarPorCuenta(String idCuenta) {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVIDADES WHERE IDCUENTA = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idCuenta);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Actividad a = new Actividad();
                    a.setIdActividad(rs.getString("IDACTIVIDAD"));
                    a.setIdCuenta(rs.getString("IDCUENTA"));
                    a.setDescripcion(rs.getString("DESCRIPCION"));
                    a.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
                    a.setFechaCierre(rs.getTimestamp("FECHA_CIERRE"));
                    a.setTipo(rs.getString("TIPO"));
                    a.setRazon(rs.getString("RAZON"));
                    a.setDetalle(rs.getString("DETALLE"));
                    a.setResolucion(rs.getString("RESOLUCION"));
                    a.setComentarios(rs.getString("COMENTARIOS"));
                    a.setTelefono(rs.getLong("TELEFONO"));
                    a.setCorreo(rs.getString("CORREO"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error listarPorCuenta: " + e.getMessage());
        }
        return lista;
    }

    public Actividad obtenerPorId(String idActividad) {
        String sql = "SELECT * FROM ACTIVIDADES WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Actividad a = new Actividad();
                    a.setIdActividad(rs.getString("IDACTIVIDAD"));
                    a.setIdCuenta(rs.getString("IDCUENTA"));
                    a.setDescripcion(rs.getString("DESCRIPCION"));
                    a.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
                    a.setFechaCierre(rs.getTimestamp("FECHA_CIERRE"));
                    a.setTipo(rs.getString("TIPO"));
                    a.setRazon(rs.getString("RAZON"));
                    a.setDetalle(rs.getString("DETALLE"));
                    a.setResolucion(rs.getString("RESOLUCION"));
                    a.setComentarios(rs.getString("COMENTARIOS"));
                    a.setTelefono(rs.getLong("TELEFONO"));
                    a.setCorreo(rs.getString("CORREO"));
                    a.setIdUsuario(rs.getString("IDUSUARIO"));
                    return a;
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error obtenerPorId: " + e.getMessage());
        }
        return null;
    }

    public boolean actualizarActividad(Actividad a) {
        String sql = "UPDATE ACTIVIDADES SET "
                + "DESCRIPCION=?, FECHA_CIERRE=?, TIPO=?, RAZON=?, DETALLE=?, RESOLUCION=?, COMENTARIOS=?, TELEFONO=?, CORREO=? "
                + "WHERE IDACTIVIDAD=?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getDescripcion());
            ps.setTimestamp(2, a.getFechaCierre());
            ps.setString(3, a.getTipo());
            ps.setString(4, a.getRazon());
            ps.setString(5, a.getDetalle());
            ps.setString(6, a.getResolucion());
            ps.setString(7, a.getComentarios());
            ps.setLong(8, a.getTelefono());
            ps.setString(9, a.getCorreo());
            ps.setString(10, a.getIdActividad());
            int r = ps.executeUpdate();
            LOGGER.info("Filas actualizadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error actualizarActividad: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarActividad(String idActividad) {
        String sql = "DELETE FROM ACTIVIDADES WHERE IDACTIVIDAD = ?";
        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, idActividad);
            int r = ps.executeUpdate();
            LOGGER.info("Filas eliminadas ACTIVIDADES: " + r);
            return r > 0;
        } catch (SQLException e) {
            LOGGER.severe("Error eliminarActividad: " + e.getMessage());
            return false;
        }
    }

    public List<Actividad> listarTodas() {
        List<Actividad> lista = new ArrayList<>();
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "SELECT * FROM ACTIVIDADES";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setIdActividad(rs.getString("IDACTIVIDAD"));
                actividad.setIdCuenta(rs.getString("IDCUENTA"));
                actividad.setDescripcion(rs.getString("DESCRIPCION"));
                actividad.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
                actividad.setFechaCierre(rs.getTimestamp("FECHA_CIERRE"));
                actividad.setTipo(rs.getString("TIPO"));
                actividad.setRazon(rs.getString("RAZON"));
                actividad.setDetalle(rs.getString("DETALLE"));
                actividad.setResolucion(rs.getString("RESOLUCION"));
                actividad.setComentarios(rs.getString("COMENTARIOS"));
                actividad.setTelefono(rs.getLong("TELEFONO"));
                actividad.setCorreo(rs.getString("CORREO"));
                lista.add(actividad);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar actividades: " + e.getMessage());
        }
        return lista;
    }

    public List<Actividad> obtenerActividadesPorCuenta(String idCuenta) {
        List<Actividad> lista = new ArrayList<>();
        try (Connection con = ConexionBD.conectar();
                PreparedStatement ps = con.prepareStatement(
                        "SELECT * FROM ACTIVIDADES WHERE IDCUENTA = ?")) {

            ps.setString(1, idCuenta);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Actividad act = new Actividad();
                act.setIdActividad(rs.getString("IDACTIVIDAD"));
                act.setIdCuenta(rs.getString("IDCUENTA"));
                act.setDescripcion(rs.getString("DESCRIPCION"));
                act.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
                act.setFechaCierre(rs.getTimestamp("FECHA_CIERRE"));
                act.setTipo(rs.getString("TIPO"));
                act.setRazon(rs.getString("RAZON"));
                act.setDetalle(rs.getString("DETALLE"));
                act.setResolucion(rs.getString("RESOLUCION"));
                act.setComentarios(rs.getString("COMENTARIOS"));
                act.setTelefono(rs.getLong("TELEFONO"));
                act.setCorreo(rs.getString("CORREO"));
                act.setIdUsuario(rs.getString("IDUSUARIO"));

                lista.add(act);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    /**
     * Obtiene las actividades pendientes (fecha_cierre >= hoy)
     * 
     * @return Lista de actividades pendientes
     */
    public List<Actividad> obtenerActividadesPendientes() {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT * FROM ACTIVIDADES WHERE FECHA_CIERRE >= CURDATE() ORDER BY FECHA_CIERRE ASC";

        try (Connection conn = ConexionBD.conectar();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setIdActividad(rs.getString("IDACTIVIDAD"));
                actividad.setIdCuenta(rs.getString("IDCUENTA"));
                actividad.setDescripcion(rs.getString("DESCRIPCION"));
                actividad.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
                actividad.setFechaCierre(rs.getTimestamp("FECHA_CIERRE"));
                actividad.setTipo(rs.getString("TIPO"));
                actividad.setRazon(rs.getString("RAZON"));
                actividad.setDetalle(rs.getString("DETALLE"));
                actividad.setResolucion(rs.getString("RESOLUCION"));
                actividad.setComentarios(rs.getString("COMENTARIOS"));
                actividad.setTelefono(rs.getLong("TELEFONO"));
                actividad.setCorreo(rs.getString("CORREO"));
                actividad.setIdUsuario(rs.getString("IDUSUARIO"));
                lista.add(actividad);
            }

        } catch (SQLException e) {
            LOGGER.severe("Error obtenerActividadesPendientes: " + e.getMessage());
        }

        return lista;
    }

    public int contarActividadesPendientes() {
       int i =0;
       for(Actividad act : obtenerActividadesPendientes()){
           i++;
       }
       return i;
    }
    
     public int contarActividadesFinalizadas() {
       int i =0;
       for(Actividad act : obtenerActividadesFinalizadas()){
           i++;
       }
       return i;
    }


    public static String generarIdActividadUnico() {
        String id;
        String query = "SELECT 1 FROM ACTIVIDADES WHERE IDACTIVIDAD = ? LIMIT 1";
        do {
            StringBuilder sb = new StringBuilder();
            sb.append("1-");
            String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            for (int i = 0; i < 8; i++) {
                int idx = (int) (Math.random() * caracteres.length());
                sb.append(caracteres.charAt(idx));
            }
            id = sb.toString();

            try (Connection conn = ConexionBD.conectar();
                    PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, id);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next())
                    break;
            } catch (SQLException e) {
                LOGGER.severe("Error generarIdActividadUnico: " + e.getMessage());
                return null;
            }

        } while (true);
        return id;
    }
    
    public List<Actividad> listarPorUsuario(String idUsuario) {
    List<Actividad> lista = new ArrayList<>();
    try {
        Connection con = ConexionBD.conectar();
        String sql = "SELECT * FROM actividades WHERE idUsuario = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Actividad a = new Actividad();
            a.setIdActividad(rs.getString("idActividad"));
            a.setIdCuenta(rs.getString("idCuenta"));
            a.setDescripcion(rs.getString("descripcion"));
            a.setFechaCreacion(rs.getTimestamp("fecha_creacion"));
            a.setFechaCierre(rs.getTimestamp("fecha_cierre"));
            a.setTipo(rs.getString("tipo"));
            a.setRazon(rs.getString("razon"));
            a.setDetalle(rs.getString("detalle"));
            a.setResolucion(rs.getString("resolucion"));
            a.setComentarios(rs.getString("comentarios"));
            a.setTelefono(rs.getLong("telefono"));
            a.setCorreo(rs.getString("correo"));
            a.setIdUsuario(rs.getString("idUsuario"));
            lista.add(a);
        }
        rs.close();
        ps.close();
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

    public List<Actividad> obtenerActividadesFinalizadas() {
    List<Actividad> lista = new ArrayList<>();
    String sql = "SELECT * FROM ACTIVIDADES WHERE RESOLUCION = 'FINALIZADO' ORDER BY FECHA_CIERRE DESC";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Actividad actividad = new Actividad();
            actividad.setIdActividad(rs.getString("IDACTIVIDAD"));
            actividad.setIdCuenta(rs.getString("IDCUENTA"));
            actividad.setDescripcion(rs.getString("DESCRIPCION"));
            actividad.setFechaCreacion(rs.getTimestamp("FECHA_CREACION"));
            actividad.setFechaCierre(rs.getTimestamp("FECHA_CIERRE"));
            actividad.setTipo(rs.getString("TIPO"));
            actividad.setRazon(rs.getString("RAZON"));
            actividad.setDetalle(rs.getString("DETALLE"));
            actividad.setResolucion(rs.getString("RESOLUCION"));
            actividad.setComentarios(rs.getString("COMENTARIOS"));
            actividad.setTelefono(rs.getLong("TELEFONO"));
            actividad.setCorreo(rs.getString("CORREO"));
            actividad.setIdUsuario(rs.getString("IDUSUARIO"));
            lista.add(actividad);
        }

    } catch (SQLException e) {
        LOGGER.severe("Error obtenerActividadesFinalizadas: " + e.getMessage());
    }

    return lista;
}
}
