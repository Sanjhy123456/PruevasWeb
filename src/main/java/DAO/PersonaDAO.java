package DAO;

import Conexion.ConexionBD;
import Modelo.Persona;
import Email.EmailService;
import Modelo.HistorialEliminacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PersonaDAO {

    Connection conex;
    BCryptPasswordEncoder passwordEncoder;
    public PersonaDAO() {
        ConexionBD cn = new ConexionBD();
        conex = cn.getConnection();
        passwordEncoder = new BCryptPasswordEncoder();
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
    String sql;
    // Si no se ha definido una ubicación, omitir `cod_ubicacion_cliente` del INSERT
    if (persona.getCod_ubicacion_cliente() == 0) {
        sql = "INSERT INTO persona (dni, nombre, apellido, correo, telefono, contraseña, cod_rol) VALUES (?, ?, ?, ?, ?, ?, ?)";
    } else {
        sql = "INSERT INTO persona (dni, nombre, apellido, correo, telefono, contraseña, cod_ubicacion_cliente, cod_rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    }

    try (PreparedStatement ps = conex.prepareStatement(sql)) {
        ps.setInt(1, persona.getDni());
        ps.setString(2, persona.getNombre());
        ps.setString(3, persona.getApellido());
        ps.setString(4, persona.getCorreo());
        ps.setInt(5, persona.getTelefono());

        // Encriptar la contraseña antes de guardarla
        String contraseñaEncriptada = passwordEncoder.encode(persona.getContraseña());
        ps.setString(6, contraseñaEncriptada);

        // Manejar cod_rol
        if (persona.getCod_ubicacion_cliente() == 0) {
            ps.setInt(7, persona.getCod_rol()); // Establece cod_rol como el séptimo parámetro si no hay ubicación
        } else {
            ps.setInt(7, persona.getCod_ubicacion_cliente());
            ps.setInt(8, persona.getCod_rol()); // Establece cod_rol como el octavo parámetro si hay ubicación
        }

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
         // Encriptar la contraseña antes de actualizarla
        String contraseñaEncriptada = passwordEncoder.encode(usuario.getContraseña());
        pstmt.setString(5, contraseñaEncriptada);

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
    
        //LLLLOOOOOOOGGGGGIIIIINNNNN///////////////////////////////////////
 public Persona validarCredenciales(String correo, String contraseña) {
    String sql = "SELECT * FROM persona WHERE correo = ?";

    try (PreparedStatement ps = conex.prepareStatement(sql)) {
        ps.setString(1, correo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            // Recuperamos la contraseña desde la base de datos
            String contraseñaEncriptada = rs.getString("contraseña");

            // Intentamos verificar la contraseña con encriptación (si es encriptada)
            if (passwordEncoder.matches(contraseña, contraseñaEncriptada)) {
                // Contraseña válida con encriptación
                Persona usuario = new Persona();
                usuario.setDni(rs.getInt("dni"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setTelefono(rs.getInt("telefono"));
                usuario.setContraseña(rs.getString("contraseña")); // Aquí la contraseña encriptada
                usuario.setCod_ubicacion_cliente(rs.getInt("cod_ubicacion_cliente"));
                usuario.setCod_rol(rs.getInt("cod_rol"));
                return usuario;
            } else {
                // Si no es encriptada, comparamos con la contraseña en texto claro
                // Esto es para las contraseñas que no han sido encriptadas
                if (contraseña.equals(contraseñaEncriptada)) {
                    // Contraseña válida sin encriptación
                    Persona usuario = new Persona();
                    usuario.setDni(rs.getInt("dni"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setTelefono(rs.getInt("telefono"));
                    usuario.setContraseña(rs.getString("contraseña")); // Aquí la contraseña en texto claro
                    usuario.setCod_ubicacion_cliente(rs.getInt("cod_ubicacion_cliente"));
                    usuario.setCod_rol(rs.getInt("cod_rol"));
                    return usuario;
                }
            }
        }
    } catch (SQLException e) {
        System.out.println("Error en la validación de credenciales: " + e.getMessage());
    }
    return null; // Si no se encuentra el correo o no coincide la contraseña
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

        // Encriptar la contraseña antes de guardarla
        String contraseñaEncriptada = passwordEncoder.encode(persona.getContraseña());
        ps.setString(6, contraseñaEncriptada);
        
        // Manejar cod_ubicacion_cliente
        if (persona.getCod_ubicacion_cliente() == 0) {
            ps.setNull(7, java.sql.Types.INTEGER); // Establecer como NULL si es 0
        } else {
            ps.setInt(7, persona.getCod_ubicacion_cliente());
        }

        // Establecer cod_rol como 2
        ps.setInt(8, 3);

        int filasAfectadas = ps.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);

        return filasAfectadas > 0; // Retorna true si se insertó correctamente
    } catch (SQLException e) {
        System.out.println("Error en el Registro: " + e.getMessage());
        e.printStackTrace(); // Imprime el stack trace para obtener más detalles
        return false;
    }
}
    

//Metodo Recuperar Contraceña

// Método para obtener una persona por su DNI
    public Persona obtenerPersonaPorDNI(int dni) {
        Persona persona = null;
        String query = "SELECT * FROM persona WHERE dni = ?";

        try (PreparedStatement stmt = conex.prepareStatement(query)) {
            stmt.setInt(1, dni);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Crear el objeto Persona con los datos de la base de datos
                persona = new Persona();
                persona.setDni(rs.getInt("dni"));
                persona.setNombre(rs.getString("nombre"));
                persona.setApellido(rs.getString("apellido"));
                persona.setCorreo(rs.getString("correo"));
                persona.setTelefono(rs.getInt("telefono"));
                persona.setContraseña(rs.getString("contraseña"));
                persona.setCod_ubicacion_cliente(rs.getInt("cod_ubicacion_cliente"));
                persona.setCod_rol(rs.getInt("cod_rol"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return persona;
    }

    // Método para actualizar la contraseña de una persona por su DNI
    public boolean actualizarContraseña(int dni, String nuevaContraseña) {
        String sql = "UPDATE persona SET contraseña = ? WHERE dni = ?";

        try (PreparedStatement stmt = conex.prepareStatement(sql)) {
            stmt.setString(1, nuevaContraseña); // Contraseña encriptada
            stmt.setInt(2, dni);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // Si se actualizó al menos una fila, la operación fue exitosa
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
