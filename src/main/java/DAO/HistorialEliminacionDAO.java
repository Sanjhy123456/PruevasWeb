package DAO;

import Conexion.ConexionBD;
import Modelo.HistorialEliminacion;
import Modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistorialEliminacionDAO {
    Connection conex;

    public HistorialEliminacionDAO() {
        ConexionBD cn = new ConexionBD();
        conex = cn.getConnection();
    }

   // Método para registrar una eliminación
public void registrarEliminacion(Persona persona) {
    String query = "INSERT INTO historial_eliminacion (dni, nombre, apellido, correo, telefono, fecha_eliminacion) VALUES (?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conex.prepareStatement(query)) {
        stmt.setInt(1, persona.getDni());
        stmt.setString(2, persona.getNombre());
        stmt.setString(3, persona.getApellido());
        stmt.setString(4, persona.getCorreo());
        stmt.setInt(5, persona.getTelefono());

        // Si prefieres manejar la fecha desde Java
        java.sql.Date fecha = new java.sql.Date(System.currentTimeMillis());
        stmt.setDate(6, fecha);  // Usa la fecha actual

        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

// Método para obtener el historial de eliminaciones
public List<HistorialEliminacion> obtenerHistorial() {
    List<HistorialEliminacion> historialList = new ArrayList<>();
    String query = "SELECT dni, nombre, apellido, correo, telefono, fecha_eliminacion FROM historial_eliminacion ORDER BY fecha_eliminacion DESC";

    try (PreparedStatement stmt = conex.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            HistorialEliminacion historial = new HistorialEliminacion();
            historial.setDni(rs.getInt("dni"));
            historial.setNombre(rs.getString("nombre"));
            historial.setApellido(rs.getString("apellido"));
            historial.setCorreo(rs.getString("correo"));
            historial.setTelefono(rs.getInt("telefono"));
            historial.setFechaEliminacion(rs.getDate("fecha_eliminacion"));  // Usamos getDate() para un tipo DATE

            historialList.add(historial);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return historialList;
}
}
