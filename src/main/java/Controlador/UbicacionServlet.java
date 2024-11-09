package Controlador;

import DAO.UbicacionDAO;
import Modelo.Persona;
import Modelo.Ubicacion;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;

@WebServlet("/UbicacionServlet")
public class UbicacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UbicacionDAO ubicacionDAO;

    public UbicacionServlet() {
        super();
        ubicacionDAO = new UbicacionDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("insertar".equals(accion)) {
            insertarUbicacion(request, response);
        }
    }

    private void insertarUbicacion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int codUbicacionCliente = Integer.parseInt(request.getParameter("codUbicacionCliente"));
            String departamento = request.getParameter("departamento");
            String provincia = request.getParameter("provincia");
            String distrito = request.getParameter("distrito");
            String direccion = request.getParameter("direccion");
            String referencia = request.getParameter("referencia");

            // Recuperar el objeto usuario de la sesión
            HttpSession session = request.getSession();
            Persona usuario = (Persona) session.getAttribute("usuario"); // Obtener el objeto usuario
            String nombreUsuario = (usuario != null) ? usuario.getNombre() : "Desconocido"; // Si no hay usuario, colocar un nombre por defecto

            // Crear el objeto Ubicacion
            Ubicacion nuevaUbicacion = new Ubicacion(codUbicacionCliente, departamento, provincia, distrito, direccion, referencia);

            // Insertar la ubicación en la base de datos
            if (ubicacionDAO.insertarUbicacion(nuevaUbicacion)) {
                request.setAttribute("mensaje", "Ubicación registrada exitosamente.");
            } else {
                request.setAttribute("mensaje", "Error al registrar la ubicación.");
            }

            // Pasar el nombre del usuario al JSP
            request.setAttribute("nombreUsuario", nombreUsuario);

        } catch (NumberFormatException e) {
            request.setAttribute("mensaje", "Código de ubicación no válido.");
            e.printStackTrace();
        }

        // Redirigir de vuelta a la página de ubicación con el mensaje correspondiente
        request.getRequestDispatcher("historial.jsp").forward(request, response);
    }
}
