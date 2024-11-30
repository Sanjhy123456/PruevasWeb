<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Modelo.Favorito"%>
<%@page import="DAO.FavoritoDAO"%>
<%@page import="Modelo.Productos"%>
<%@page import="DAO.ProductoDAO"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Mis Favoritos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Mis Productos Favoritos</h1>
        <div class="table-responsive mt-3">
            <table class="table table-bordered">
                <thead class="thead-dark">
                    <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Detalle</th>
                        <th>Descripción</th>
                        <th>Precio</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int codUsuario = (Integer) session.getAttribute("usuarioId");
                        FavoritoDAO favoritoDAO = new FavoritoDAO();
                        List<Favorito> favoritos = favoritoDAO.obtenerFavoritos(codUsuario);
                        ProductoDAO productoDAO = new ProductoDAO();
                        for (Favorito favorito : favoritos) {
                            Productos producto = productoDAO.obtenerProductoPorId(favorito.getCodProducto());
                    %>
                    <tr>
                        <td><%= producto.getCodProducto() %></td>
                        <td><%= producto.getNombre() %></td>
                        <td><%= producto.getDetalle() %></td>
                        <td><%= producto.getDescripcion() %></td>
                        <td><%= producto.getPrecio() %></td>
                        <td>
                            <form action="CarritoServlet" method="post" style="display:inline;">
                                <input type="hidden" name="cod_producto" value="<%= producto.getCodProducto() %>">
                                <button type="submit" class="btn btn-primary">Agregar al carrito</button>
                            </form>
                            <form action="FavoritosServlet" method="post" style="display:inline;">
                                <input type="hidden" name="cod_producto" value="<%= producto.getCodProducto() %>">
                                <button type="submit" class="btn btn-danger">Quitar de favoritos</button>
                            </form>
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
