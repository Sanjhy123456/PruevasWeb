package DAO;

import Conexion.ConexionBD;
import Modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {

    Connection conex;

    public PersonaDAO() {
        ConexionBD cn = new ConexionBD();
        conex = cn.getConnection();
    }

    // Método para listar personas
    public List<Persona> obtenerDatos() {
        List<Persona> listarusuarios = new ArrayList<>();
        String sql = "SELECT * FROM persona";
        
        try (PreparedStatement ps = conex.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setDni(rs.getInt("dni"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setCorreo(rs.getString("correo"));
                persona.setTelefono(rs.getInt("telefono"));
                persona.setContraseña(rs.getString("contraseña"));
                persona.setCod_ubicacion_cliente(rs.getInt("cod_ubicacion_cliente")); // Cambia según tu implementación
                persona.setCod_rol(rs.getInt("cod_rol")); // Cambiado a cod_rol
                listarusuarios.add(persona);
            }
        } catch (SQLException e) {
            System.err.println("Error al Obtener Datos: " + e.getMessage());
        }
        return listarusuarios;
    }

    // Método para agregar persona
   public boolean agregar(Persona persona) {
    String sql = "INSERT INTO persona (dni, nombre, apellido, correo, telefono, contraseña, cod_ubicacion_cliente, cod_rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conex.prepareStatement(sql)) {
        ps.setInt(1, persona.getDni());
        ps.setString(2, persona.getNombre());
        ps.setString(3, persona.getApellido());
        ps.setString(4, persona.getCorreo());
        ps.setInt(5, persona.getTelefono());
        ps.setString(6, persona.getContraseña());
        ps.setInt(7, persona.getCod_ubicacion_cliente()); // Agregar cod_ubicacion_cliente
        ps.setInt(8, persona.getCod_rol()); // Agregar cod_rol
        
        // Ejecutar la consulta y verificar las filas afectadas
        int filasAfectadas = ps.executeUpdate();
        if (filasAfectadas > 0) {
            return true; // Se insertó correctamente
        } else {
            System.out.println("Error: No se insertaron filas en la base de datos.");
            return false; // No se insertó nada
        }
    } catch (SQLException e) {
        System.out.println("Error en el Registro: " + e.getMessage());
        return false; // Retorna false en caso de error
    }
}

    
    // Método para obtener persona por DNI
    public Persona obtenerUsuarioPorId(int dni) {
        String sql = "SELECT * FROM persona WHERE dni = ?";
        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, dni);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Persona usuario = new Persona();
                usuario.setDni(rs.getInt("dni"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getInt("telefono"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setCod_ubicacion_cliente(rs.getInt("cod_ubicacion_cliente")); // Cambia según tu implementación
                usuario.setCod_rol(rs.getInt("cod_rol")); // Cambiado a cod_rol
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener usuario por DNI: " + e.getMessage());
        }
        return null; 
    }

    // Método para actualizar persona
    
public boolean actualizar(Persona usuario) {
    String sql = "UPDATE persona SET nombre = ?, apellido = ?, correo = ?, telefono = ?, contraseña = ?, cod_ubicacion_cliente = ?, cod_rol = ? WHERE dni = ?";
    try (PreparedStatement pstmt = conex.prepareStatement(sql)) {
        pstmt.setString(1, usuario.getNombre());
        pstmt.setString(2, usuario.getApellido());
        pstmt.setString(3, usuario.getCorreo());
        pstmt.setInt(4, usuario.getTelefono());
        pstmt.setString(5, usuario.getContraseña());
        pstmt.setInt(6, usuario.getCod_ubicacion_cliente());
        pstmt.setInt(7, usuario.getCod_rol()); // cod_rol es el último
        pstmt.setInt(8, usuario.getDni());

        int filasActualizadas = pstmt.executeUpdate();
        if (filasActualizadas > 0) {
            return true; // La actualización fue exitosa
        } else {
            System.out.println("No se actualizó ninguna fila."); // Para depuración
            return false;
        }
    } catch (SQLException e) {
        System.err.println("Error al actualizar usuario: " + e.getMessage());
        e.printStackTrace(); // Muestra el error en la consola
        return false;
    }
}

    // Método para eliminar persona
    public boolean eliminar(int dni) {
        String sql = "DELETE FROM persona WHERE dni = ?";
        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, dni);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar persona: " + e.getMessage());
            return false;
        }
    }
    //Login 
  public Persona validarCredenciales(String correo, String contraseña) {
    String sql = "SELECT * FROM persona WHERE correo = ? AND contraseña = ?";
    try (PreparedStatement ps = conex.prepareStatement(sql)) {
        ps.setString(1, correo);
        ps.setString(2, contraseña);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Persona usuario = new Persona();
            usuario.setDni(rs.getInt("dni"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellido(rs.getString("apellido"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setTelefono(rs.getInt("telefono"));
            usuario.setContraseña(rs.getString("contraseña"));
            usuario.setCod_ubicacion_cliente(rs.getInt("cod_ubicacion_cliente"));
            usuario.setCod_rol(rs.getInt("cod_rol"));
            return usuario;
        }
    } catch (SQLException e) {
        System.out.println("Error en la validación de credenciales: " + e.getMessage());
    }
    return null; // Si no se encuentra, devuelve null
}
///parte del login
public boolean registrarLogin(Persona persona) {
    String sql = "INSERT INTO persona (dni, nombre, apellido, correo, telefono, contraseña, cod_ubicacion_cliente, cod_rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement ps = conex.prepareStatement(sql)) {
        ps.setInt(1, persona.getDni());
        ps.setString(2, persona.getNombre());
        ps.setString(3, persona.getApellido());
        ps.setString(4, persona.getCorreo());
        ps.setInt(5, persona.getTelefono());
        ps.setString(6, persona.getContraseña());

        // Manejar cod_ubicacion_cliente
        if (persona.getCod_ubicacion_cliente() == 0) {
            ps.setNull(7, java.sql.Types.INTEGER); // Establecer como NULL si es 0
        } else {
            ps.setInt(7, persona.getCod_ubicacion_cliente());
        }

        // Establecer cod_rol como 2
        ps.setInt(8, 2);

        int filasAfectadas = ps.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);

        return filasAfectadas > 0; // Retorna true si se insertó correctamente
    } catch (SQLException e) {
        System.out.println("Error en el Registro: " + e.getMessage());
        e.printStackTrace(); // Imprime el stack trace para obtener más detalles
        return false;
    }
}

    public boolean registrarPersona(Persona nuevoUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
