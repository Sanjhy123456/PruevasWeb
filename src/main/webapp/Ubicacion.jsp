<%@ page import="Modelo.Ubicacion" %>
<%@ page import="Modelo.Persona" %>
<%
    // Obtener la sesión y el nombre de usuario
    HttpSession session = request.getSession();  // Obtener la sesión activa
    String nombreUsuario = (String) session.getAttribute("nombreUsuario");  // Recuperar el nombre del usuario desde la sesión
    Integer dniUsuario = (Integer) session.getAttribute("dniUsuario");  // Recuperar el DNI del usuario desde la sesión

    if (nombreUsuario == null) {
        // Si no hay nombreUsuario, significa que el usuario no ha iniciado sesión
        response.sendRedirect("index.jsp"); // Redirige al login
    }
%>

<h2>Bienvenido, <%= nombreUsuario %></h2>
<p>Registro de Ubicación</p>

<!-- Formulario para registrar la ubicación -->
<form action="UbicacionServlet" method="post">
    <input type="hidden" name="dniUsuario" value="<%= dniUsuario %>" /> <!-- Enviar el dni del usuario -->
    
    <label for="departamento">Departamento:</label>
    <input type="text" name="departamento" required />
    
    <label for="provincia">Provincia:</label>
    <input type="text" name="provincia" required />
    
    <label for="distrito">Distrito:</label>
    <input type="text" name="distrito" required />
    
    <label for="direccion">Dirección:</label>
    <input type="text" name="direccion" required />
    
    <label for="referencia">Referencia:</label>
    <input type="text" name="referencia" />
    
    <button type="submit">Registrar Ubicación</button>
</form>
