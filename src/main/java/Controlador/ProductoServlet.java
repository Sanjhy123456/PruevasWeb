package Controlador;

import DAO.ProductoDAO;
import Modelo.Productos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@WebServlet("/ProductoServlet")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024) // Limitar el tamaño del archivo a 5 MB
public class ProductoServlet extends HttpServlet {
    
    ProductoDAO productoDAO = new ProductoDAO();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("agregarProductos".equals(action)) {
            agregarProductos(request, response);
        } else if("actualizarProductos".equals(action)) {
            actualizarProductos(request, response);
        
        } 
    }

  // Método para agregar un producto
    private void agregarProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String mensaje;
        try {
            // Captura de parámetros
            int codProducto = Integer.parseInt(request.getParameter("codProducto")); // Captura codProducto
            String nombre = request.getParameter("nombre");
            Part part = request.getPart("imagen"); // Se asegura que coincide con el formulario
            InputStream inputStream = part.getInputStream(); // Obtener el InputStream de la imagen
            String detalle = request.getParameter("detalle");
            String descripcion = request.getParameter("descripcion");
            int precio = Integer.parseInt(request.getParameter("precio")); // Cambiar a double para manejar precios
            int codCategoria = Integer.parseInt(request.getParameter("codCategoria"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            
            // Crear y configurar el objeto Producto
            Productos pro = new Productos();
            pro.setCodProducto(codProducto); // Se asegura de establecer codProducto
            pro.setNombre(nombre);
            pro.setImagen(inputStream); // Leer la imagen como InputStream
            pro.setDetalle(detalle);
            pro.setDescripcion(descripcion);
            pro.setPrecio(precio); // Asegúrate de que este método sea de tipo double
            pro.setCodCategoria(codCategoria); // Asegurarse de establecer codCategoria
            pro.setCantidad(cantidad);
            
            // Llamar al DAO para agregar el producto
            boolean agregado = productoDAO.agregarProducto(pro);
            mensaje = agregado ? "Producto agregado correctamente." : "Error al agregar el producto.";
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al agregar producto: " + e.getMessage();
        }
        
        // Redireccionar con el mensaje
        response.sendRedirect("Admin/menuAdmin.jsp?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
    }
    
    
   // Método para actualizar un producto
    private void actualizarProductos(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String mensaje;
        try {
            // Captura de parámetros
            int codProducto = Integer.parseInt(request.getParameter("codProducto"));
            String nombre = request.getParameter("nombre");
            String detalle = request.getParameter("detalle");
            String descripcion = request.getParameter("descripcion");
            int precio = Integer.parseInt(request.getParameter("precio")); // Precio como int
            int codCategoria = Integer.parseInt(request.getParameter("codCategoria"));
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            
            Part part = request.getPart("imagen");
            InputStream inputStream = part.getInputStream(); // Obtener el InputStream de la imagen

            // Crear y configurar el objeto Producto
            Productos pro = new Productos();
            pro.setCodProducto(codProducto); // Se asegura de establecer codProducto
            pro.setNombre(nombre);
            pro.setImagen(inputStream); // Leer la imagen como InputStream
            pro.setDetalle(detalle);
            pro.setDescripcion(descripcion);
            pro.setPrecio(precio); // Asegúrate de que este método sea de tipo int
            pro.setCodCategoria(codCategoria); // Asegurarse de establecer codCategoria
            pro.setCantidad(cantidad);

            // Llamar al DAO para actualizar el producto
            boolean actualizado = productoDAO.actualizarProducto(pro);
            mensaje = actualizado ? "Producto actualizado correctamente." : "Error al actualizar el producto.";
        } catch (Exception e) {
            e.printStackTrace();
            mensaje = "Error al actualizar producto: " + e.getMessage();
        }
        
        // Redireccionar con el mensaje
        response.sendRedirect("Admin/menuAdmin.jsp?mensaje=" + URLEncoder.encode(mensaje, "UTF-8"));
    }


    @Override
    public String getServletInfo() {
        return "Servlet para gestionar operaciones de agregar productos";
    }
}
