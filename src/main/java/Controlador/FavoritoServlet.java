package Controlador;

import DAO.FavoritoDAO;
import Modelo.Favorito;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

public class FavoritoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("agregarQuitarFavorito".equals(action)) {
            int codProducto = Integer.parseInt(request.getParameter("cod_producto"));
            int codUsuario = (Integer) request.getSession().getAttribute("usuarioId");
            FavoritoDAO favoritoDAO = new FavoritoDAO();

            // Verificar si ya está en favoritos
            if (favoritoDAO.existeFavorito(codProducto, codUsuario)) {
                // Eliminar de favoritos
                favoritoDAO.eliminarFavorito(codProducto, codUsuario);
            } else {
                // Agregar a favoritos
                favoritoDAO.agregarFavorito(new Favorito(0, codProducto, codUsuario));
            }

            response.sendRedirect("index.jsp");
        }
    }
}
