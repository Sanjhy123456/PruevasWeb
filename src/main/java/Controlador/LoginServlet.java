package Controlador;

import Modelo.Persona;
import DAO.PersonaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private static final int COD_ROL_CLIENTE = 2;
    private static final int COD_UBICACION_DEFAULT = 0; // Usar 0 como valor predeterminado para cod_ubicacion_cliente
    private PersonaDAO personaDAO = new PersonaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("cerrarSesion".equals(action)) {
            cerrarSesion(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "El método HTTP GET no está soportado por esta URL");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("login".equals(action)) {
            login(request, response);
        } else if ("registrar".equals(action)) {
            registrar(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "El método HTTP POST no está soportado para esta acción");
        }
    }

    // Método para cerrar sesión
    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            HttpSession session = request.getSession(false); // Obtener la sesión actual
            if (session != null) {
                session.invalidate(); // Invalidar la sesión
                System.out.println("Sesión cerrada."); // Mensaje de depuración
            }
            response.sendRedirect("index.jsp"); // Redirigir a index.jsp
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el stack trace para depuración
            response.sendRedirect("index.jsp?error=cierre_fallido"); // Redirigir con error
        }
    }

    // Método login
    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String correo = request.getParameter("correo");
        String contraseña = request.getParameter("contraseña");

        Persona usuario = personaDAO.validarCredenciales(correo, contraseña);

        if (usuario != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario); // Almacena todo el objeto usuario
            session.setAttribute("nombreUsuario", usuario.getNombre()); // Almacena solo el nombre
            session.setAttribute("dniUsuario", usuario.getDni()); // Aquí guardas el DNI

            System.out.println("Sesión iniciada para: " + usuario.getNombre()); // Agrega un mensaje de depuración

            // Redirigir según el rol
            if (usuario.getCod_rol() == 1) {
                response.sendRedirect("Admin/num.jsp");
            } else if (usuario.getCod_rol() == 2) {
                response.sendRedirect("index.jsp");
            }
        } else {
            response.sendRedirect("index.jsp?error=true");
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Obtener parámetros de la solicitud
            int dni = Integer.parseInt(request.getParameter("dni").trim());
            String nombre = request.getParameter("nombre").trim();
            String apellido = request.getParameter("apellido").trim();
            String correo = request.getParameter("correo").trim();
            int telefono = Integer.parseInt(request.getParameter("telefono").trim());
            String contraseña = request.getParameter("contraseña").trim();

            // Validaciones
            if (nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || contraseña.isEmpty()) {
                response.sendRedirect("login.jsp?error=campos_vacios");
                return;
            }

            // Crear objeto Persona y asignar valores
            Persona persona = new Persona();
            persona.setDni(dni);
            persona.setNombre(nombre);
            persona.setApellido(apellido);
            persona.setCorreo(correo);
            persona.setTelefono(telefono);
            persona.setContraseña(contraseña); // Hashear la contraseña
            persona.setCod_ubicacion_cliente(COD_UBICACION_DEFAULT); // Establecer ubicación predeterminada
            persona.setCod_rol(COD_ROL_CLIENTE); // Establecer rol como cliente

            // Intentar registrar el usuario
            if (personaDAO.registrarLogin(persona)) {
                response.sendRedirect("login.jsp?success=true"); // Redirigir con mensaje de éxito
            } else {
                response.sendRedirect("login.jsp?error=registro_fallido"); // Redirigir con mensaje de error
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("login.jsp?error=parametros_invalidos"); // Redirigir con error si los parámetros son incorrectos
        } catch (Exception e) {
            response.sendRedirect("login.jsp?error=error_interno"); // Redirigir con error interno
            e.printStackTrace(); // Imprimir el stack trace para depuración
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones de agregar, editar y eliminar personas";
    }
}
