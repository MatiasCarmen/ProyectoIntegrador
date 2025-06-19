/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import BD.ConexionBD;
import entidades.ProductoInstalado;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO/Controlador para PRODUCTOS_INSTALADOS.
 */
public class ControladorProductosInstalados {
    /**
     * Obtiene un map de lista de descripciones por tipo de producto.
     * Tipo = PLAN / ADICIONAL / DESCUENTO
     */
    public Map<String,List<String>> obtenerProductosPorTipo(String idCuenta) {
        Map<String,List<String>> resultado = new HashMap<>();
        try (Connection conn = ConexionBD.conectar()) {
            // 1) Planes instalados
            String sqlPlanes = 
              "SELECT 'PLAN' AS tipo, P.NOMBRE AS desc FROM PRODUCTOS_INSTALADOS PI " +
              " JOIN PLANES P ON PI.IDPLAN = P.IDPLAN " +
              " WHERE PI.IDCUENTA = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlPlanes)) {
                ps.setString(1, idCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        resultado
                          .computeIfAbsent(rs.getString("tipo"), k->new ArrayList<>())
                          .add(rs.getString("desc"));
                    }
                }
            }

            // 2) Adicionales instalados
            String sqlAdic = 
              "SELECT 'ADICIONAL' AS tipo, A.DESCRIPCION AS desc FROM PRODUCTOS_INSTALADOS PI " +
              " JOIN ADICIONALES A ON PI.IDADICIONALES = A.IDADICIONAL " +
              " WHERE PI.IDCUENTA = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlAdic)) {
                ps.setString(1, idCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        resultado
                          .computeIfAbsent(rs.getString("tipo"), k->new ArrayList<>())
                          .add(rs.getString("desc"));
                    }
                }
            }

            // 3) Descuentos instalados
            String sqlDesc = 
              "SELECT 'DESCUENTO' AS tipo, D.DESCRIPCION AS desc FROM PRODUCTOS_INSTALADOS PI " +
              " JOIN DESCUENTOS D ON PI.IDDESCUENTOS = D.IDDESCUENTO " +
              " WHERE PI.IDCUENTA = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlDesc)) {
                ps.setString(1, idCuenta);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        resultado
                          .computeIfAbsent(rs.getString("tipo"), k->new ArrayList<>())
                          .add(rs.getString("desc"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}
