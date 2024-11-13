package Controlador;

import DAO.PersonaDAO;
import Modelo.Persona;
import com.google.common.base.Preconditions;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;

@WebServlet("/PersonaServlet")
public class PersonaServlet extends HttpServlet {
    PersonaDAO personaDAO = new PersonaDAO();
     private static final int COD_UBICACION_DEFAULT = 0; // Usar 0 como valor predeterminado para cod_ubicacion_cliente

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("agregarPer".equals(action)) {
            agregarPersona(request, response);
        } else if ("actualizarPer".equals(action)) {
            actualizarPersona(request, response);
        }
    }
private void agregarPersona(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String mensaje;
    try {
        // Obtener parámetros de la solicitud
        int dni = Integer.parseInt(request.getParameter("dni").trim());
        String nombre = request.getParameter("nombre").trim();
        String apellido = request.getParameter("apellido").trim();
        String correo = request.getParameter("correo").trim();
        int telefono = Integer.parseInt(request.getParameter("telefono").trim());
        String contraseña = request.getParameter("contraseña").trim();
        int cod_rol = Integer.parseInt(request.getParameter("cod_rol").trim());

        // Validación de datos usando Google Guava
        Preconditions.checkNotNull(nombre, "El nombre no puede ser nulo.");
        Preconditions.checkArgument(!nombre.isEmpty(), "El nombre no puede estar vacío.");
        Preconditions.checkArgument(nombre.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+"), "El nombre contiene caracteres inválidos.");

        Preconditions.checkNotNull(apellido, "El apellido no puede ser nulo.");
        Preconditions.checkArgument(!apellido.isEmpty(), "El apellido no puede estar vacío.");
        Preconditions.checkArgument(apellido.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ ]+"), "El apellido contiene caracteres inválidos.");

        Preconditions.checkNotNull(correo, "El correo no puede ser nulo.");
        Preconditions.checkArgument(correo.contains("@gmail.com"), "El correo debe contener un '@gmail.com'.");

        Preconditions.checkNotNull(contraseña, "La contraseña no puede ser nula.");
        Preconditions.checkArgument(!contraseña.isEmpty(), "La contraseña no puede estar vacía.");

        Preconditions.checkArgument(Integer.toString(telefono).length() == 9, "El teléfono debe tener 9 dígitos.");
        Preconditions.checkArgument(telefono > 0, "El teléfono debe ser un número positivo.");

        Preconditions.checkArgument(Integer.toString(dni).length() == 8, "El DNI debe tener 8 dígitos.");
        Preconditions.checkArgument(dni > 0, "El DNI debe ser un número positivo.");

        Preconditions.checkArgument(cod_rol > 0, "El rol es inválido.");

        // Crear el objeto Persona
        Persona persona = new Persona();
        persona.setDni(dni); 
        persona.setNombre(nombre); 
        persona.setApellido(apellido); 
        persona.setCorreo(correo); 
        persona.setTelefono(telefono); 
        persona.setContraseña(contraseña); 
        persona.setCod_ubicacion_cliente(COD_UBICACION_DEFAULT); // Establecer ubicación predeterminada
        persona.setCod_rol(cod_rol); 

        // Verificación si la persona fue agregada correctamente
        if (personaDAO.agregar(persona)) {
            response.sendRedirect("Admin/num2.jsp?mensaje=agregado"); // Redirigir a Persona.jsp
        } else {
            response.sendRedirect("Admin/num2.jsp?mensaje=Error al Agregar");
        }
    } catch (NumberFormatException e) {
        mensaje = "Formato inválido en los datos: " + e.getMessage();
        response.sendRedirect("Admin/num2.jsp?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
        e.printStackTrace(); // Registra el error en la consola
    } catch (Exception e) {
        mensaje = "Error al agregar persona: " + e.getMessage();
        response.sendRedirect("Admin/num2.jsp?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
        e.printStackTrace(); // Registra cualquier otro error
    }
}

// Método para actualizar una persona
 private void actualizarPersona(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String mensaje;
    try {
        // Obtener parámetros
        int dni = Integer.parseInt(request.getParameter("dni"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        String contraseña = request.getParameter("contraseña");
        int cod_ubicacion_cliente = Integer.parseInt(request.getParameter("cod_ubicacion_cliente"));
        int cod_rol = Integer.parseInt(request.getParameter("cod_rol"));

        // Crear el objeto Persona
        Persona persona = new Persona();
        persona.setDni(dni); 
        persona.setNombre(nombre); 
        persona.setApellido(apellido); 
        persona.setCorreo(correo); 
        persona.setTelefono(telefono); 
        persona.setContraseña(contraseña); 
        persona.setCod_ubicacion_cliente(cod_ubicacion_cliente); 
        persona.setCod_rol(cod_rol); 
        
        // Llamar al DAO para actualizar la persona
        boolean actualizado = personaDAO.actualizar(persona);
        mensaje = actualizado ? "Persona actualizada correctamente." : "Error al actualizar la persona.";
    } catch (NumberFormatException e) {
        e.printStackTrace();
        mensaje = "Error de formato en los datos: " + e.getMessage();
    } catch (Exception e) {
        e.printStackTrace();
        mensaje = "Error al actualizar la persona: " + e.getMessage();
    }

    // Redireccionar con el mensaje
    response.sendRedirect("Admin/num2.jsp?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
}

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones de agregar, editar y eliminar personas";
    }
}
