<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="Modelo.Productos"%>
<%@page import="DAO.ProductoDAO"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Responsive Admin Dashboard | Korsat X Parmaga</title>
    <!-- ======= Styles ====== -->
    <link rel="stylesheet" href="../Admin/css/style.css">
</head>

<body>
    <!-- =============== Navigation ================ -->
  <div class="container">
      
      <!-- =============== Eliminar ================ -->
    <%
            String mensaje = null;
            String accion = request.getParameter("accion");
            if ("eliminar".equals(accion)) {
                int codProducto = Integer.parseInt(request.getParameter("codProducto"));
                ProductoDAO productoDAO = new ProductoDAO();
                boolean eliminado = productoDAO.eliminarProducto(codProducto);
                if (eliminado) {
                    mensaje = "Producto eliminado con éxito";
                } else {
                    mensaje = "Error al eliminar el producto";
                }
            }
        %> has tipo asi para le eliminar Peronsa

        <div class="navigation">
            <ul>
                <li>
                    <a href="../Admin/menuAdmin.jsp">
                        <span class="icon">
                            <ion-icon name=""></ion-icon>
                        </span>
                        <span class="title">Productos</span>
                    </a>
                </li>
                <li>
                    <a href="../Admin/menuAdmin.jsp">
                        <span class="icon">
                            <ion-icon name="home-outline"></ion-icon>
                        </span>
                        <span class="title">Prodcutos</span>
                    </a>
                </li>
                <li>
                    <a href="../Admin/menu2.jsp">
                        <span class="icon">
                            <ion-icon name="people-outline"></ion-icon>
                        </span>
                        <span class="title">Personas</span>
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
                    <a href="../LoginServlet?action=cerrarSesion">
                        <span class="icon">
                            <ion-icon name="log-out-outline"></ion-icon>
                        </span>
                        <span class="title">Cerrar Sesion</span>
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

                <div class="card">
                    <div>
                        <div class="numbers">$7,842</div>
                        <div class="cardName">Earning</div>
                    </div>
                    <div class="iconBx">
                        <ion-icon name="cash-outline"></ion-icon>
                    </div>
                </div>
            </div>

            <!-- ================ Order Details List ================= -->
             <div class="details">
                  <div class="recentOrders">
                        <div class="cardHeader">
                            <h2>Recent Orders</h2>
                            <a href="../Producto/Registrar.jsp" class="btn">RegistrarProductoNuevo</a>
                        </div>
                        <table >
                            <thead >
                                <tr>
                                    <th>Código</th>
                                    <th>Nombre</th>
                                    <th>Detalle</th>
                                    <th>Descripción</th>
                                    <th>Precio</th>
                                    <th>Categoría</th>
                                    <th>Imagen</th>
                                    <th>Cantidad</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                    ProductoDAO productoDAO = new ProductoDAO();
                                    List<Productos> productos = productoDAO.obtenerTodosLosProductos();

                                    for (Productos producto : productos) {
                                %>
                                <tr>
                                    <td><%= producto.getCodProducto() %></td>
                                    <td><%= producto.getNombre() %></td>
                                    <td><%= producto.getDetalle() %></td>
                                    <td><%= producto.getDescripcion() %></td>
                                    <td><%= producto.getPrecio() %></td>
                                    <td><%= producto.getCodCategoria() %></td>
                                    <td>
                                        <img src="../ProImagenServlet?codProducto=<%= producto.getCodProducto() %>" 
                                             alt="Imagen de <%= producto.getNombre() %>" 
                                             style="width:100px; height:auto;" 
                                             onerror="this.onerror=null; this.src='ruta_por_defecto.jpg';" />
                                    </td>
                                    <td><%= producto.getCantidad() %></td>
                                     <td>
                            <!-- Botón de eliminación con enlace que activa la acción -->
                            <form action="../Admin/menuAdmin.jsp" method="get" style="display:inline;">
                                <input type="hidden" name="codProducto" value="<%= producto.getCodProducto() %>">
                                <input type="hidden" name="accion" value="eliminar">
                                <button type="submit" class="btn btn-danger">Eliminar</button>
                            </form>
                            <button class="btn btn-warning" onclick="window.location.href='../Producto/Actualizar.jsp?codProducto=<%= producto.getCodProducto() %>'">Actualizar</button>
                        </td>   
                    </tr>
                    <%
                        }
                    %>
                            </tbody>
                        </table>
                            <%-- Manejar eliminación del producto --%>

                    </div>
            
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
