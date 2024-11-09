<%-- num3.jsp --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="Modelo.Persona" %>
<!DOCTYPE html>
<html>
<head>
    <title>Datos del Usuario</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <h1>Datos del Usuario</h1>

    <%
        // Recuperar el objeto Persona de la sesión
        Persona usuario = (Persona) session.getAttribute("usuario");
        if (usuario != null) {
    %>
        <table>
            <tr>
                <th>DNI</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Correo</th>
                <th>Teléfono</th>
                <th>Ubicacion</th>
                <th>Rol</th>
            </tr>
            <tr>
                <td><%= usuario.getDni() %></td>
                <td><%= usuario.getNombre() %></td>
                <td><%= usuario.getApellido() %></td>
                <td><%= usuario.getCorreo() %></td>
                <td><%= usuario.getTelefono() %></td>
                <td><%= usuario.getCod_ubicacion_cliente() %></td>
                <td><%= usuario.getCod_rol() == 1 ? "Administrador" : "Cliente" %></td>
            </tr>
        </table>
        <p><a href="index.jsp">Volver a la página principal</a></p>
       <button class="btn btn-success" onclick="window.location.href='../Ubicacion.jsp'">Agregar Ubicacion</button>
    <%
        } else {
    %>
        <p>No se encontraron datos de usuario. <a href="login.jsp">Inicia sesión</a></p>
    <%
        }
    %>
</body>
</html>
