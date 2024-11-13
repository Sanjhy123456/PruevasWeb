package Controlador;

import Modelo.Persona;
import DAO.PersonaDAO;
import com.google.common.base.Preconditions;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;

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

            System.out.println("Sesión iniciada para: " + usuario.getNombre()); // Agrega un mensaje de depuración

            // Redirigir según el rol
            if (usuario.getCod_rol() == 1 || usuario.getCod_rol() == 3) {
                response.sendRedirect("Admin/num.jsp");
            } else if (usuario.getCod_rol() == 2) {
                response.sendRedirect("index.jsp");
            }
        } else {
            response.sendRedirect("index.jsp?error=true");
        }
    }
////Google Guava 
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
        Preconditions.checkArgument(!nombre.isEmpty(), "El nombre no puede estar vacío");
        Preconditions.checkArgument(nombre.matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+"), "El nombre solo puede contener letras y espacios");
        
        Preconditions.checkArgument(!apellido.isEmpty(), "El apellido no puede estar vacío");
        Preconditions.checkArgument(apellido.matches("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+"), "El apellido solo puede contener letras y espacios");

        Preconditions.checkArgument(!correo.isEmpty(), "El correo no puede estar vacío");
        Preconditions.checkArgument(correo.contains("@gmail.com"), "El correo debe terminar en @gmail.com");

        Preconditions.checkArgument(!contraseña.isEmpty(), "La contraseña no puede estar vacía");
        Preconditions.checkArgument(telefono > 0, "El teléfono debe ser un número positivo");

        // Crear objeto Persona y asignar valores
        Persona persona = new Persona();
        persona.setDni(dni);
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        persona.setCorreo(correo);
        persona.setTelefono(telefono);
        persona.setContraseña(contraseña); // Hashear la contraseña si es necesario
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
    } catch (IllegalArgumentException e) {
        response.sendRedirect("login.jsp?error=" + URLEncoder.encode(e.getMessage(), "UTF-8")); // Redirigir con error de validación
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
