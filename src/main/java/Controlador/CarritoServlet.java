package Controlador;

import DAO.ProductoDAO;
import Modelo.Carrito;
import Modelo.Productos;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/CarritoServlet")
public class CarritoServlet extends HttpServlet {

    ProductoDAO productoDAO = new ProductoDAO();
    List<Carrito> listarCarrito = new ArrayList<>();
    int totalPagar = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("agregarCarrito".equals(action)) {
            agregarCarrito(request, response);
        } else if ("carrito".equals(action)) {
            listarCarrito(request, response);

        } else if ("vaciarCarrito".equals(action)) {
            vaciarCarrito(request, response);
        } else if ("quitarProducto".equals(action)) {
            quitarProductoCarrito(request, response);
        } else if ("actualizarCantidad".equals(action)) {
            actualizarCantidad(request, response);
    }
    }

 private void agregarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int cod_producto = Integer.parseInt(request.getParameter("cod_producto"));
    Productos p = productoDAO.obtenerProductoPorCod(cod_producto);

    if (p != null) {
        // Obtener la lista del carrito desde la sesión
        List<Carrito> carritoList = (List<Carrito>) request.getSession().getAttribute("listarCarrito");
        
        if (carritoList == null) {
            carritoList = new ArrayList<>();
        }

        // Verificar si el producto ya está en el carrito
        boolean productoExistente = false;
        for (Carrito car : carritoList) {
            if (car.getCod_producto() == cod_producto) {
                // Aumentar la cantidad y el subtotal
                car.setCantidad(car.getCantidad() + 1);
                car.setSubTotal(car.getCantidad() * p.getPrecio());
                productoExistente = true;
                break;
            }
        }

        // Si el producto no existe, agregarlo como nuevo
        if (!productoExistente) {
            Integer itemCount = (Integer) request.getSession().getAttribute("itemCount");
            if (itemCount == null) {
                itemCount = 0;
            }
            itemCount++;
            request.getSession().setAttribute("itemCount", itemCount);

            // Crear un nuevo objeto Carrito y agregarlo a la lista
            Carrito car = new Carrito();
            car.setCod_producto(p.getCodProducto());
            car.setNombre(p.getNombre());
            car.setDescripcion(p.getDescripcion());
            car.setPrecio(p.getPrecio());
            car.setCantidad(1);
            car.setSubTotal(car.getCantidad() * p.getPrecio());

            carritoList.add(car);
            request.getSession().setAttribute("listarCarrito", carritoList);
        }

        // Actualizar la sesión
        request.getSession().setAttribute("listarCarrito", carritoList);

        // Devolver el nuevo contador como JSON
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print("{\"itemCount\": " + request.getSession().getAttribute("itemCount") + "}");
        out.flush();
    } else {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado.");
    }
}


    private void listarCarrito(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        totalPagar = 0;

        // Calcular el total a pagar
        for (Carrito producto : listarCarrito) {
            totalPagar += producto.getPrecio(); // getPrecio() debe devolver un int
        }

        // Establecer atributos para la vista
        request.setAttribute("carrito", listarCarrito);
        request.setAttribute("totalPagar", totalPagar);

        // Redirigir a la vista carrito.jsp
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }

  

private void vaciarCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Vaciar el carrito
    listarCarrito.clear();
    
    // Establecer el contador a cero
    request.getSession().setAttribute("itemCount", 0);
    
    // Actualizar la sesión
    request.getSession().setAttribute("listarCarrito", listarCarrito);
    
    // Redirigir a index.jsp después de vaciar el carrito
    response.sendRedirect("index.jsp");
}



private void quitarProductoCarrito(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Obtener el código del producto a eliminar del parámetro de la solicitud
    int codProducto = Integer.parseInt(request.getParameter("codProducto"));
    
    // Obtener la lista del carrito desde la sesión
    List<Carrito> carritoList = (List<Carrito>) request.getSession().getAttribute("listarCarrito");
    
    // Eliminar el producto del carrito
    if (carritoList != null) {
        // Buscar el producto en el carrito
        boolean productoEliminado = carritoList.removeIf(car -> car.getCod_producto() == codProducto);
        
        if (productoEliminado) {
            // Actualizar la sesión después de eliminar el producto
            request.getSession().setAttribute("listarCarrito", carritoList);
            
            // Obtener el contador actual de ítems y decrementar
            Integer itemCount = (Integer) request.getSession().getAttribute("itemCount");
            if (itemCount != null && itemCount > 0) {
                itemCount--; // Disminuir el contador
                request.getSession().setAttribute("itemCount", itemCount);
            }
            
            // Redirigir a carrito.jsp después de eliminar el producto
            response.sendRedirect("carrito.jsp");
        } else {
            // Si no se encontró el producto, redirigir con un mensaje de error (opcional)
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado en el carrito.");
        }
    } else {
        // Si la lista está vacía, redirigir con un mensaje de error
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "El carrito está vacío.");
    }
}

private void actualizarCantidad(HttpServletRequest request, HttpServletResponse response) throws IOException {
    int cod_producto = Integer.parseInt(request.getParameter("codProducto"));
    int nuevaCantidad = Integer.parseInt(request.getParameter("cantidad"));

    List<Carrito> carritoList = (List<Carrito>) request.getSession().getAttribute("listarCarrito");
    if (carritoList != null) {
        for (Carrito car : carritoList) {
            if (car.getCod_producto() == cod_producto) {
                car.setCantidad(nuevaCantidad); // Actualizar cantidad
                car.setSubTotal(car.getCantidad() * car.getPrecio()); // Recalcular subtotal
                break;
            }
        }
        request.getSession().setAttribute("listarCarrito", carritoList);
    }
    
    // En este caso, no es necesario redirigir, ya que estamos usando AJAX
    response.setStatus(HttpServletResponse.SC_OK);
}



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
