<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAO.HistorialEliminacionDAO"%>
<%@ page import="Modelo.HistorialEliminacion"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historial de Eliminaciones</title>
    <link rel="stylesheet" href="../Admin/css/style.css">
</head>

<body>
    <div class="container">
        <div class="main">
            <div class="cardHeader">
                <h2>Historial de Eliminaciones</h2>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>DNI</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>Correo</th>
                        <th>Teléfono</th>
                        <th>Fecha de Eliminación</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        HistorialEliminacionDAO historialDAO = new HistorialEliminacionDAO();
                        List<HistorialEliminacion> historial = historialDAO.obtenerHistorial();

                        for (HistorialEliminacion registro : historial) {
                    %>
                        <tr>
                            <td><%= registro.getDni() %></td>
                            <td><%= registro.getNombre() %></td>
                            <td><%= registro.getApellido() %></td>
                            <td><%= registro.getCorreo() %></td>
                            <td><%= registro.getTelefono() %></td>
                            <td><%= registro.getFechaEliminacion() %></td>
                        </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </div>
    </div>
</body>

</html>
