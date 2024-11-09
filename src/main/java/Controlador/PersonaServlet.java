package Controlador;

import DAO.PersonaDAO;
import Modelo.Persona;
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
    try {
        // Obtener parámetros de la solicitud
        int dni = Integer.parseInt(request.getParameter("dni"));
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        int telefono = Integer.parseInt(request.getParameter("telefono")); // Cambiado a int
        String contraseña = request.getParameter("contraseña");
        int cod_ubicacion_cliente = Integer.parseInt(request.getParameter("cod_ubicacion_cliente")); // Corregido el paréntesis
        int cod_rol = Integer.parseInt(request.getParameter("cod_rol")); // Nuevo parámetro cod_rol

        // Validación de datos
        if (nombre == null || nombre.trim().isEmpty() || 
            apellido == null || apellido.trim().isEmpty() || // Validación de apellido
            correo == null || !correo.contains("@") || 
            contraseña == null || contraseña.trim().isEmpty() || // Validación de contraseña
            telefono <= 0 || // Validación de teléfono
            cod_ubicacion_cliente <= 0 || // Validación de ubicación
            cod_rol <= 0) { // Validación de cod_rol
            response.sendRedirect("Admin/num2.jsp?mensaje=datosInvalidos");
            return;
        }

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
        
        // Verificación si la persona fue agregada correctamente
        if (personaDAO.agregar(persona)) {
            response.sendRedirect("Admin/num2.jsp?mensaje=agregado"); // Redirigir a Persona.jsp
        } else {
            response.sendRedirect("Admin/num2.jsp?mensaje=Error al Agregar");
        }
    }catch (NumberFormatException e) {
        response.sendRedirect("Admin/num2.jsp?mensaje=formatoInvalido");
        e.printStackTrace(); // Registra el error en la consola
    }
        // Registra errores específicos de base de datos
         catch (Exception e) {
        response.sendRedirect("Admin/num2.jsp?mensaje=errorGeneral");
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
