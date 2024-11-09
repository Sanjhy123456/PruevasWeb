<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAO.PersonaDAO"%>
<%@page import="Modelo.Persona"%>
<%
    // Obtenemos el DNI del parámetro
    String dniParam = request.getParameter("dni");
    if (dniParam == null || dniParam.isEmpty()) {
        out.println("<h3>Error: DNI no proporcionado</h3>"); // Mensaje de error
        return;
    }

    int dni = 0;
    try {
        dni = Integer.parseInt(dniParam);
        System.out.println("DNI recibido: " + dni); // Depuración
    } catch (NumberFormatException e) {
        out.println("<h3>Error: DNI no válido</h3>"); // Mensaje de error
        return;
    }

    // Accedemos al DAO para obtener el usuario
    PersonaDAO personaDAO = new PersonaDAO();
    Persona usuario = personaDAO.obtenerUsuarioPorId(dni);
    
    if (usuario == null) {
        out.println("<h3>Error: Usuario no encontrado</h3>"); // Mensaje de error
        return;
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar Usuario</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Actualizar Usuario</h1>
        <form action="../PersonaServlet" method="post">
            <input type="hidden" name="action" value="actualizarPer">
            <input type="hidden" name="dni" value="<%= usuario.getDni() %>">
            
            <div class="form-group">
                <label for="nombre">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" value="<%= usuario.getNombre() %>" required>
            </div>
            <div class="form-group">
                <label for="apellido">Apellido</label>
                <input type="text" class="form-control" id="apellido" name="apellido" value="<%= usuario.getApellido() %>" required>
            </div>
            <div class="form-group">
                <label for="correo">Correo</label>
                <input type="email" class="form-control" id="correo" name="correo" value="<%= usuario.getCorreo() %>" required>
            </div>
            <div class="form-group">
                <label for="telefono">Teléfono</label>
                <input type="tel" class="form-control" id="telefono" name="telefono" value="<%= usuario.getTelefono() %>" required>
            </div>
            <div class="form-group">
                <label for="contraseña">Contraseña</label>
                <input type="password" class="form-control" id="contraseña" name="contraseña" value="<%= usuario.getContraseña() %>"">
            </div>
            <div class="form-group">
                <label for="cod_ubicacion_cliente">Código de Ubicación del Cliente</label>
                <input type="number" class="form-control" id="cod_ubicacion_cliente" name="cod_ubicacion_cliente" value="<%= usuario.getCod_ubicacion_cliente() %>" required>
            </div>
            <div class="form-group">
                <label for="cod_rol">Código de Rol</label>
                <select class="form-control" id="cod_rol" name="cod_rol" required>
                    <option value="1" <%= usuario.getCod_rol() == 1 ? "selected" : "" %>>Administrador</option>
                    <option value="2" <%= usuario.getCod_rol() == 2 ? "selected" : "" %>>Cliente</option>
                    <option value="3" <%= usuario.getCod_rol() == 3 ? "selected" : "" %>>Trabajador</option>
                </select>
            </div>
            
            <button type="submit" class="btn btn-success">Actualizar Usuario</button>
            <button type="button" class="btn btn-secondary" onclick="window.location.href='../Admin/num2.jsp'">Cancelar</button>
        </form>

        <% 
            // Mensaje de error o éxito
            String mensaje = request.getParameter("mensaje");
            if (mensaje != null) {
                out.println("<div class='alert alert-warning mt-3'>" + mensaje + "</div>");
            }
        %>
    </div>
</body>
</html>
