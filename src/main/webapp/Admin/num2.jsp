<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="DAO.PersonaDAO"%>
<%@page import="Modelo.Persona"%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responsive Admin Dashboard | Personas</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="../Admin/css/style.css">
</head>

<body>
    <!-- =============== Navigation ================ -->
  <div class="container">
     
        <div class="navigation">
            <ul>
                <li>
                    <a href="../Admin/num.jsp">
                        <span class="icon">
                            <ion-icon name="#"></ion-icon>
                        </span>
                        <span class="title">Panel de Administrador</span>
                    </a>
                </li>
                <li>
                    <a href="../Admin/num.jsp">
                        <span class="icon">
                            <ion-icon name="home-outline"></ion-icon>
                        </span>
                        <span class="title">Producto</span>
                    </a>
                </li>
                <li>
                    <a href="../Admin/num2.jsp">
                        <span class="icon">
                            <ion-icon name="people-outline"></ion-icon>
                        </span>
                        <span class="title">Customers</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon">
                            <ion-icon name="chatbubble-outline"></ion-icon>
                        </span>
                        <span class="title">Messages</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon">
                            <ion-icon name="help-outline"></ion-icon>
                        </span>
                        <span class="title">Help</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon">
                            <ion-icon name="settings-outline"></ion-icon>
                        </span>
                        <span class="title">Settings</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon">
                            <ion-icon name="lock-closed-outline"></ion-icon>
                        </span>
                        <span class="title">Password</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <span class="icon">
                            <ion-icon name="log-out-outline"></ion-icon>
                        </span>
                        <span class="title">Sign Out</span>
                    </a>
                </li>
            </ul>
        </div>

        <!-- ========================= Main ==================== -->
        <div class="main">
            <div class="topbar">
                <div class="toggle">
                    <ion-icon name="menu-outline"></ion-icon>
                </div>

                <div class="search">
                    <label>
                        <input type="text" placeholder="Search here">
                        <ion-icon name="search-outline"></ion-icon>
                    </label>
                </div>

                <div class="user">
                    <img src="assets/imgs/customer01.jpg" alt="">
                </div>
            </div>

            <!-- ======================= Cards ================== -->
            <div class="cardBox">
                 
                <div class="card">
                    <div>
                        <div class="numbers">0</div>
                        <div class="cardName">Daily Views</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="eye-outline"></ion-icon>
                    </div>
                </div>

                <div class="card">
                    <div>
                        <div class="numbers">0</div>
                        <div class="cardName">Sales</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="cart-outline"></ion-icon>
                    </div>
                </div>

                <div class="card">
                    <div>
                        <div class="numbers">0</div>
                        <div class="cardName">Comments</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="chatbubbles-outline"></ion-icon>
                    </div>
                </div>

             
            </div>

            <!-- ================ Order Details List ================= -->
             <div class="details">
                  <div class="recentOrders">
                      <%
            // Inicializa el DAO y obtiene la lista de usuarios
            PersonaDAO personaDAO = new PersonaDAO();
            List<Persona> usuarios = personaDAO.obtenerDatos();

            // Manejo de mensajes
            String mensaje = request.getParameter("mensaje");
            if (mensaje != null) {
                out.println("<div class='alert alert-success'>" + mensaje + "</div>");
            }
        %>
                        <div class="cardHeader">
                            <h2>Recent Orders</h2>
                            <a href="../Usuarios/Registrar.jsp" class="btn">Registrar Nuevo Usuario</a>
                        </div>
                        <table >
                            <thead >
                                <tr>
                                    <th>DNI</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>Correo</th>
                                    <th>Teléfono</th>
                                    <th>Rol</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                 <%
                        for (Persona usuario : usuarios) {
                               %>
                                <tr>
                                     <td><%= usuario.getDni() %></td>
                                     <td><%= usuario.getNombre() %></td>
                                      <td><%= usuario.getApellido() %></td>
                                      <td><%= usuario.getCorreo() %></td>
                                      <td><%= usuario.getTelefono() %></td>
                                      <td><%= usuario.getCod_rol() %></td>
                             
                                     <td>
                            <!-- Botón de eliminación con enlace que activa la acción -->
                              <form action="../Admin/num2.jsp" method="post" onsubmit="return confirm('¿Estás seguro de que deseas eliminar este usuario?');">
                                <input type="hidden" name="dni" value="<%= usuario.getDni() %>">
                                <input type="hidden" name="accion" value="eliminar">
                                <button type="submit" class="btn btn-danger">Eliminar</button>
                            </form>
                            <button class="btn btn-warning" onclick="window.location.href='../Usuarios/Actualizar.jsp?dni=<%= usuario.getDni() %>'">Actualizar</button>
                        </td>
                    </tr>
                    <%
                        } // Fin del ciclo for
                    %>
                            </tbody>
                        </table>

                    </div>
                             <%
            // Manejar la eliminación después de la acción del formulario
            String accion = request.getParameter("accion");
            if ("eliminar".equals(accion)) {
                try {
                    int dniEliminar = Integer.parseInt(request.getParameter("dni"));
                    boolean eliminado = personaDAO.eliminar(dniEliminar);
                    if (eliminado) {
                        response.sendRedirect("../Admin/num2.jsp?mensaje=Usuario eliminado con éxito");
                    } else {
                        response.sendRedirect("../Admin/num2.jsp?mensaje=Error al eliminar el usuario");
                    }
                } catch (NumberFormatException e) {
                    response.sendRedirect("../Admin/num2.jsp?mensaje=El DNI proporcionado no es válido");
                }
            }
        %>
                    </div>
        </div>
                           
    </div>

    <!-- =========== Scripts =========  -->
    <script src="../Admin/js/main.js"></script>

    <!-- ====== ionicons ======= -->
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</body>

</html>
