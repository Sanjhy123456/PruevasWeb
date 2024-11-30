/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Conexion.ConexionBD;
import Modelo.Favorito;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritoDAO {
      Connection conex;

    public FavoritoDAO() {
        ConexionBD cn = new ConexionBD();
        conex = cn.getConnection();
    }
    // Agregar producto a favoritos
    public boolean agregarFavorito(Favorito favorito) {
        try {
            String sql = "INSERT INTO favoritos (codProducto, codUsuario) VALUES (?, ?)";
            PreparedStatement stmt = conex.prepareStatement(sql);
            stmt.setInt(1, favorito.getCodProducto());
            stmt.setInt(2, favorito.getDniPersona());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Eliminar producto de favoritos
    public boolean eliminarFavorito(int codProducto, int dniPersona ) {
        try {
            String sql = "DELETE FROM favoritos WHERE codProducto = ? AND codUsuario = ?";
            PreparedStatement stmt = conex.prepareStatement(sql);
            stmt.setInt(1, codProducto);
            stmt.setInt(2, dniPersona);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Obtener productos favoritos de un usuario
    public List<Favorito> obtenerFavoritos(int codUsuario) {
        List<Favorito> favoritos = new ArrayList<>();
        try {
            String sql = "SELECT * FROM favoritos WHERE codUsuario = ?";
            PreparedStatement stmt = conex.prepareStatement(sql);
            stmt.setInt(1, codUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Favorito favorito = new Favorito(
                        rs.getInt("idFavorito"),
                        rs.getInt("dniPersona"),
                        rs.getInt("codUsuario")
                );
                favoritos.add(favorito);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoritos;
    }
    
    // Verificar si un producto ya está en los favoritos de un usuario
    public boolean existeFavorito(int codProducto, int codUsuario) {
        try {
            String sql = "SELECT 1 FROM favoritos WHERE codProducto = ? AND codUsuario = ?";
            PreparedStatement stmt = conex.prepareStatement(sql);
            stmt.setInt(1, codProducto);
            stmt.setInt(2, codUsuario);
            ResultSet rs = stmt.executeQuery();
            // Si existe algún registro, retornamos true
            return rs.next(); // Si la consulta devuelve alguna fila, significa que ya existe
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Si no existe, retornamos false
    }
}