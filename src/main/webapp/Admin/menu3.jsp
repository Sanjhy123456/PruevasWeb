<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="DAO.HistorialEliminacionDAO" %>
<%@ page import="Modelo.HistorialEliminacion" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Date" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Usuarios Eliminados</title>
    <link rel="stylesheet" href="../Admin/css/style.css">
</head>
<body>
    <div class="container">
        <div class="navigation">
            <ul>
                <li>
                    <a href="../Admin/menuAdmin.jsp">
                        <span class="icon">
                            <ion-icon name="home-outline"></ion-icon>
                        </span>
                        <span class="title">Panel de Administrador</span>
                    </a>
                </li>
                <!-- Añadir nuevo enlace para Usuarios Eliminados -->
                <li>
                    <a href="../Admin/menu3.jsp">
                        <span class="icon">
                            <ion-icon name="person-remove-outline"></ion-icon>
                        </span>
                        <span class="title">Usuarios Eliminados</span>
                    </a>
                </li>
                <!-- Otros elementos del menú -->
            </ul>
        </div>
        
        <!-- Contenido principal -->
        <div class="main">
            <div class="topbar">
                <div class="toggle">
                    <ion-icon name="menu-outline"></ion-icon>
                </div>
                <div class="search">
                    <label>
                        <input type="text" placeholder="Buscar...">
                        <ion-icon name="search-outline"></ion-icon>
                    </label>
                </div>
            </div>

            <div class="details">
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
                            // Obtiene el historial de eliminaciones
                            HistorialEliminacionDAO historialDAO = new HistorialEliminacionDAO();
                            List<HistorialEliminacion> historial = historialDAO.obtenerHistorial();

                            // Formatear fecha en formato deseado
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

                            // Itera sobre el historial para mostrar los datos
                            for (HistorialEliminacion registro : historial) {
                                Date fechaEliminacion = registro.getFechaEliminacion();  // Suponiendo que es de tipo java.sql.Date
                                String fechaFormateada = sdf.format(fechaEliminacion);
                        %>
                            <tr>
                                <td><%= registro.getDni() %></td>
                                <td><%= registro.getNombre() %></td>
                                <td><%= registro.getApellido() %></td>
                                <td><%= registro.getCorreo() %></td>
                                <td><%= registro.getTelefono() %></td>
                                <td><%= fechaFormateada %></td> <!-- Muestra la fecha formateada -->
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Scripts -->
    <script src="../Admin/js/main.js"></script>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>
</html>
