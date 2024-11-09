<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="DAO.ProductoDAO"%>
<%@page import="Modelo.Productos"%>
<%@page import="jakarta.servlet.http.HttpServletRequest"%>
<%@page import="jakarta.servlet.http.HttpServletResponse"%>
<%@page import="java.io.IOException"%>
<%@page import="jakarta.servlet.ServletException"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Actualizar Producto</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Actualizar Producto</h1>
        <%
            try {
                String codProductoStr = request.getParameter("codProducto");
                
                if (codProductoStr == null || codProductoStr.isEmpty()) {
                    out.println("<h3 class='text-danger'>Código de producto no proporcionado</h3>");
                } else {
                    int codProducto = Integer.parseInt(codProductoStr);
                    ProductoDAO productoDAO = new ProductoDAO();
                    Productos producto = productoDAO.obtenerProductoPorCod(codProducto);

                    if (producto == null) {
                        out.println("<h3 class='text-danger'>Producto no encontrado</h3>");
                    } else {
        %>
      <form action="../ProductoServlet" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="actualizarProductos" />
        <input type="hidden" name="codProducto" value="<%= producto.getCodProducto() %>" />

        <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" class="form-control" id="nombre" name="nombre" value="<%= producto.getNombre() %>" required>
        </div>

        <div class="form-group">
            <label for="detalle">Detalle</label>
            <input type="text" class="form-control" id="detalle" name="detalle" value="<%= producto.getDetalle() %>" required>
        </div>

        <div class="form-group">
            <label for="descripcion">Descripción</label>
            <textarea class="form-control" id="descripcion" name="descripcion" required><%= producto.getDescripcion() %></textarea>
        </div>

        <div class="form-group">
            <label for="precio">Precio</label>
            <input type="number" class="form-control" id="precio" name="precio" value="<%= producto.getPrecio() %>" required>
        </div>

        <div class="form-group">
            <label for="codCategoria">Código de Categoría</label>
            <input type="number" class="form-control" id="codCategoria" name="codCategoria" value="<%= producto.getCodCategoria() %>" required>
        </div>

        <div class="form-group">
            <label for="imagen">Imagen</label>
            <input type="file" class="form-control-file" id="imagen" name="imagen">
            <small class="form-text text-muted">Sube una nueva imagen si deseas cambiarla.</small>
        </div>

        <button type="submit" class="btn btn-primary">Actualizar Producto</button>
        <a href="../Admin/num2.jsp" class="btn btn-secondary">Cancelar</a>
      </form>
        <%
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("<h3 class='text-danger'>Ocurrió un error: " + e.getMessage() + "</h3>");
            }
        %>
    </div>
</body>
</html>
