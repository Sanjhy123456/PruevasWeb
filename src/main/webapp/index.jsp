<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Productos"%>
<%@page import="DAO.ProductoDAO"%>
<%@page import="java.util.List" %>
<%@ page import="Modelo.Carrito" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Gestión de Productos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> <!-- Agregamos jQuery -->
    <script>
        function agregarAlCarrito(codProducto) {
            $.ajax({
                url: 'CarritoServlet?action=agregarCarrito',
                type: 'POST',
                data: { cod_producto: codProducto },
                success: function(data) {
                    // Actualizar el contador en la página
                    $('#itemCount').text(data.itemCount);
                },
                error: function(xhr, status, error) {
                    console.error('Error al agregar al carrito:', error);
                }
            });
        }
    </script>
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Bienvenido a la Gestión de Productos</h1>

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
        %>

        <% if (mensaje != null) { %>
            <div class="alert alert-info text-center">
                <%= mensaje %>
            </div>
        <% } %>
        
        <%
            Integer itemCount = (Integer) session.getAttribute("itemCount");
            int contador = (itemCount != null) ? itemCount : 0;
        %>
        <a href="CarritoServlet?action=carrito"><i class=""></i>(<label style="color: #1795ce" id="itemCount"><%= contador %></label>) Carrito</a>

        <div class="text-center mt-4">
            <button class="btn btn-success" onclick="window.location.href='Producto/Registrar.jsp'">Agregar Producto</button>
            <button class="btn btn-success" onclick="window.location.href='Admin/menuAdmin.jsp'">Usuarios</button>
              
            <!-- Inicio Login -->
            <%
            String nombreUsuario = (String) session.getAttribute("nombreUsuario");
            if (nombreUsuario != null) {
            %>
            <!-- Mostrar Nombre -->
            <p><b><a href="Usuarios/HistorialCliente.jsp"><%= nombreUsuario %></a></b></p>
            <p><a href="LoginServlet?action=cerrarSesion">Cerrar sesión</a></p>
            <%
            } else {
            %>
            <p><a href="login.jsp">inicia sesión</a>.</p>
            <%
            }
            %>
            <!-- Fin Login -->
            <button class="btn btn-primary" onclick="window.location.href='Menu.jsp'">Menu</button>
            <button class="btn btn-primary" onclick="window.location.href='Admin/menu2.jsp'">MenuP</button>
            <button class="btn btn-primary" onclick="window.location.href='Admin/menuAdmin.jsp'"></button>
        </div>

        <h2 class="text-center mt-5">Productos Registrados</h2>
        <div class="table-responsive mt-3">
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Detalle</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                        <th>Categoría</th>
                        <th>Imagen</th>
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
                            <img src="ProImagenServlet?codProducto=<%= producto.getCodProducto() %>" 
                                 alt="Imagen de <%= producto.getNombre() %>" 
                                 style="width:100px; height:auto;" 
                                 onerror="this.onerror=null; this.src='ruta_por_defecto.jpg';" />
                        </td>
                        <td>
                            <form action="index.jsp" method="get" style="display:inline;">
                                <input type="hidden" name="codProducto" value="<%= producto.getCodProducto() %>">
                                <input type="hidden" name="accion" value="eliminar">
                                <button type="submit" class="btn btn-danger">Eliminar</button>
                            </form>
                            <button class="btn btn-warning" onclick="window.location.href='Producto/Actualizar.jsp?codProducto=<%= producto.getCodProducto() %>'">Actualizar</button>
                            <button class="btn btn-warning" onclick="agregarAlCarrito(<%= producto.getCodProducto() %>)">Agregar Al carrito</button>
                        </td>   
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
