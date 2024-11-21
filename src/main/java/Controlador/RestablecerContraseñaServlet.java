package Controlador;

import DAO.PersonaDAO;
import Modelo.Persona;
import Email.EmailRecuperarContraceña;  // Asegúrate de importar la clase de correo
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RestablecerContraseñaServlet", urlPatterns = "/RestablecerContraseñaServlet")
public class RestablecerContraseñaServlet extends HttpServlet {

    private PersonaDAO personaDAO;

    @Override
    public void init() throws ServletException {
        // Inicializar PersonaDAO al inicio del servlet
        personaDAO = new PersonaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        int dni = Integer.parseInt(request.getParameter("dni"));
        String nuevaContraseña = request.getParameter("nuevaContrasena");
        String confirmarContraseña = request.getParameter("confirmarContrasena");

        // Verificar que las contraseñas coincidan
        if (!nuevaContraseña.equals(confirmarContraseña)) {
            response.getWriter().println("Las contraseñas no coinciden. Inténtalo nuevamente.");
            return; // Terminar aquí si las contraseñas no coinciden
        }

        // Intentar obtener la persona por el DNI
        Persona persona = personaDAO.obtenerPersonaPorDNI(dni);
        
        // Si la persona no existe en la base de datos
        if (persona == null) {
            response.getWriter().println("No se encontró una persona con ese DNI.");
            return;
        }

        // Intentar actualizar la contraseña
        boolean actualizado = personaDAO.actualizarContraseña(dni, nuevaContraseña);

        // Redirigir según el resultado
        if (actualizado) {
            // Enviar el correo de confirmación al usuario
            String correoUsuario = persona.getCorreo();  // Obtener el correo de la persona
            EmailRecuperarContraceña.sendPasswordResetEmail(correoUsuario);

            response.getWriter().println("Contraseña actualizada correctamente.");
            // Redirigir al login después de la actualización
            response.sendRedirect("login.jsp");
        } else {
            response.getWriter().println("Error al actualizar la contraseña. Inténtalo nuevamente.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Procesamiento del formulario o visualización de la página para cambiar la contraseña
        // Este método puede quedarse vacío si solo necesitas procesar la lógica de POST
    }
}
