<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="Modelo.Carrito" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="ruta_a_tu_css/bootstrap.min.css">
    <style>
        .img-producto {
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Carrito de Compras</h2>
        
        <%
            Integer itemCount = (Integer) session.getAttribute("itemCount");
            int contador = (itemCount != null) ? itemCount : 0;
        %>
        <a href="CarritoServlet?action=carrito">
            (<label style="color: #1795ce"><%= contador %></label>) Carrito
        </a>

        <table class="table table-bordered">
            <thead class="thead-dark">
                <tr>
                    <th>Item</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Imagen</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Subtotal</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
            <%
                List<Carrito> carritoList = (List<Carrito>) session.getAttribute("listarCarrito");
                double totalAPagar = 0;

                if (carritoList != null && !carritoList.isEmpty()) {
                    for (Carrito car : carritoList) {
                        totalAPagar += car.getSubTotal();
            %>
                <tr>
                    <td><%= carritoList.indexOf(car) + 1 %></td>
                    <td><%= car.getNombre() %></td>
                    <td><%= car.getDescripcion() %></td>
                    <td>
                        <img class="img-producto" src="ProImagenServlet?codProducto=<%= car.getCod_producto() %>" alt="Imagen de <%= car.getNombre() %>">
                    </td>
                    <td><%= car.getPrecio() %></td>
                    <td>
                        <input type="hidden" name="codProducto" value="<%= car.getCod_producto() %>">
                        <input type="number" id="cantidad-<%= car.getCod_producto() %>" name="cantidad" 
                               value="<%= car.getCantidad() %>" min="1" style="width: 60px;" required 
                               onchange="actualizarCantidad(<%= car.getCod_producto() %>, this.value)">
                    </td>
                    <td><%= car.getSubTotal() %></td>
                  <td>
          <form action="CarritoServlet" method="post" style="display:inline;">
        <input type="hidden" name="codProducto" value="<%= car.getCod_producto() %>">
        <input type="hidden" name="action" value="quitarProducto">
        <button type="submit" class="btn btn-danger">Quitar</button>
         </form>
              </td>
                        </form>
              
                </tr>
            <%
                    }
            %>
                <tr>
                    <td colspan="6" class="text-right"><strong>Total a Pagar:</strong></td>
                    <td><strong><%= totalAPagar %></strong></td>
                    <td>
                        <form action="CarritoServlet" method="post">
                            <input type="hidden" name="action" value="vaciarCarrito">
                            <button type="submit" class="btn btn-danger">Vaciar Carrito</button>
                        </form>
                    </td>
                </tr>
            <%
                } else { 
            %>
                <tr>
                    <td colspan="8" class="text-center">No hay productos en el carrito.</td>
                </tr>
            <%
                } 
            %>
            </tbody>
        </table>
        <a href="index.jsp" class="btn btn-primary">Volver a Productos</a>
    </div>
    
    <script src="ruta_a_tu_js/bootstrap.min.js"></script>
    <script>
function actualizarCantidad(codProducto, nuevaCantidad) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "CarritoServlet", true);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Aquí puedes manejar la respuesta del servidor si es necesario
            // Por ejemplo, podrías actualizar la visualización del carrito
            console.log('Cantidad actualizada con éxito');
            location.reload(); // Recarga la página para ver los cambios
        }
    };
    xhr.send("codProducto=" + codProducto + "&cantidad=" + nuevaCantidad + "&action=actualizarCantidad");
}
</script>


</body>  
</html>
