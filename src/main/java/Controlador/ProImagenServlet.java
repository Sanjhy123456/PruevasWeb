package Controlador;

import DAO.ProductoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;

import java.io.IOException;

@WebServlet("/ProImagenServlet")
public class ProImagenServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ProductoDAO productoDAO = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int codProducto = Integer.parseInt(request.getParameter("codProducto"));
            if (codProducto <= 0) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El código del producto debe ser positivo.");
                return;
            }
            productoDAO.listarImg(codProducto, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Código de producto inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
