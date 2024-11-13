<%@ page import="Modelo.Ubicacion" %>
<%@ page import="Modelo.Persona" %>

<%
    String nombreUsuario = (String) session.getAttribute("nombreUsuario");
    Integer dniUsuario = (Integer) session.getAttribute("dniUsuario"); // Obtener el dni desde la sesi�n
    
    if (nombreUsuario != null && dniUsuario != null) { // Asegurarse de que ambos valores est�n presentes
%>
    <!-- Mostrar Nombre -->
    <p><b><a href="Usuarios/HistorialCliente.jsp"><%= nombreUsuario %></a></b></p>
    <h2>Bienvenido, <%= nombreUsuario %></h2>
    <p>Registro de Ubicaci�n</p>

    <!-- Formulario para registrar la ubicaci�n -->
    <form action="UbicacionServlet" method="post">
        <input type="hidden" name="dniUsuario" value="<%= dniUsuario %>" /> <!-- Enviar el dni del usuario -->

        <label for="departamento">Departamento:</label>
        <input type="text" name="departamento" required />

        <label for="provincia">Provincia:</label>
        <input type="text" name="provincia" required />

        <label for="distrito">Distrito:</label>
        <input type="text" name="distrito" required />

        <label for="direccion">Direcci�n:</label>
        <input type="text" name="direccion" required />

        <label for="referencia">Referencia:</label>
        <input type="text" name="referencia" />

        <button type="submit">Registrar Ubicaci�n</button>
    </form>
<%
    } else {
%>
    <p>Por favor, inicia sesi�n para registrar tu ubicaci�n.</p>
<%
    }
%>
