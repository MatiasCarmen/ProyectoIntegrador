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
import entidades.Pais;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * DAO – PaisDAO: maneja listado de PAISES.
 */
public class PaisDAO {
    private static final Logger LOGGER = Logger.getLogger(PaisDAO.class.getName());

    public List<Pais> listarTodos() {
        List<Pais> lista = new ArrayList<>();
        String sql = "SELECT * FROM PAISES";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Pais(
                    rs.getString("IDPAIS"),
                    rs.getString("DESCRIPCION")
                ));
            }
        } catch (SQLException e) {
           	LOGGER.severe("Error listar Paises: " + e.getMessage());
        }
        return lista;
    }
}
