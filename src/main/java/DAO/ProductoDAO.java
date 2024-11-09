package DAO;

import Conexion.ConexionBD;

import Modelo.Productos;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    Connection conex;

    public ProductoDAO() {
        ConexionBD cn = new ConexionBD();
        conex = cn.getConnection();
    }

    // Método para obtener todos los productos
    public List<Productos> obtenerTodosLosProductos() {
        String sql = "SELECT * FROM producto";
        List<Productos> listaProductos = new ArrayList<>();
        try (PreparedStatement ps = conex.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Aquí mantenemos el InputStream para la imagen
                Productos producto = new Productos(
                        rs.getInt("cod_producto"),
                        rs.getString("nombre"),
                        rs.getBinaryStream("imagen"), // Cambiado a InputStream
                        rs.getString("detalle"),
                        rs.getString("descripcion"),
                        rs.getInt("precio"),
                        rs.getInt("cod_categoria")
                );
                listaProductos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener productos: " + e.getMessage());
        }
        return listaProductos;
    }
// Método para agregar un producto
// Método para agregar un producto
public boolean agregarProducto(Productos producto) {
    String sql = "INSERT INTO producto (cod_producto, nombre, imagen, detalle, descripcion, precio, cod_categoria) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conex.prepareStatement(sql)) {
        stmt.setInt(1, producto.getCodProducto());
        stmt.setString(2, producto.getNombre());
        
        // Usar setBlob para InputStream
        stmt.setBlob(3, producto.getImagen());
        
        stmt.setString(4, producto.getDetalle());
        stmt.setString(5, producto.getDescripcion());
        stmt.setDouble(6, producto.getPrecio()); // Asegúrate de que este método retorne un double
        stmt.setInt(7, producto.getCodCategoria());
        
        stmt.executeUpdate();
        return true;
    } catch (SQLException e) {
        System.out.println("Error en el Registro: " + e.getMessage());
        return false;
    }
}


    // Método para listar imagen
 public void listarImg(int codProducto, HttpServletResponse response) throws SQLException, IOException {
    String sql = "SELECT imagen FROM producto WHERE cod_producto = ?";
    try (PreparedStatement statement = conex.prepareStatement(sql)) {
        statement.setInt(1, codProducto);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                InputStream imagenStream = resultSet.getBinaryStream("imagen");
                if (imagenStream != null) {
                    response.setContentType("image/jpeg"); // Asegúrate de que este tipo sea correcto para tus imágenes
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = imagenStream.read(buffer)) != -1) {
                        response.getOutputStream().write(buffer, 0, bytesRead);
                    }
                    response.getOutputStream().flush();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Imagen no encontrada para el código de producto: " + codProducto);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado con el código: " + codProducto);
            }
        }
    }
}
 
 
 // Método para obtener un producto por su código
public Productos obtenerProductoPorCod(int codProducto) {
    String sql = "SELECT * FROM producto WHERE cod_producto = ?";
    try (PreparedStatement ps = conex.prepareStatement(sql)) {
        ps.setInt(1, codProducto);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Productos prod = new Productos();
            prod.setCodProducto(rs.getInt("cod_producto"));
            prod.setNombre(rs.getString("nombre"));
            prod.setImagen(rs.getBinaryStream("imagen")); // Verifica que este tipo sea correcto
            prod.setDetalle(rs.getString("detalle"));
            prod.setDescripcion(rs.getString("descripcion"));
            prod.setPrecio(rs.getInt("precio")); // Cambia a double si es necesario
            prod.setCodCategoria(rs.getInt("cod_categoria"));
            return prod;
        } else {
            System.out.println("No se encontró el producto con cod_producto: " + codProducto);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Imprimir traza del error
    }
    return null; // Si no se encuentra, devuelve null
}



 public boolean actualizarProducto(Productos producto) {
        // SQL para actualizar el producto
        String sql = "UPDATE producto SET nombre=?, detalle=?, descripcion=?, precio=?, cod_categoria=?" +
                     (producto.getImagen() != null ? ", imagen=?" : "") + " WHERE cod_producto=?";

        try (PreparedStatement statement = conex.prepareStatement(sql)) {
            // Asignar valores a cada parámetro
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDetalle());
            statement.setString(3, producto.getDescripcion());
            statement.setInt(4, producto.getPrecio());  // Usar setInt para precio
            statement.setInt(5, producto.getCodCategoria());

            int index = 6;  // Índice para la imagen (si existe)
            if (producto.getImagen() != null) {
                statement.setBlob(index++, producto.getImagen()); // Si hay nueva imagen, setearla
            }

            statement.setInt(index, producto.getCodProducto()); // Código del producto

            // Ejecutar la actualización
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Devuelve verdadero si se actualizó correctamente
        } catch (SQLException e) {
            e.printStackTrace(); // Imprimir traza del error
            return false; // En caso de error, devuelve falso
        }
    }


 // Método para eliminar un producto por su código
public boolean eliminarProducto(int cod_Producto) {
    String sql = "DELETE FROM producto WHERE cod_producto = ?"; // Cambiado a producto
    try (PreparedStatement ps = conex.prepareStatement(sql)) {
        ps.setInt(1, cod_Producto);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

   // Método para listar un producto por ID carrito
    public Productos listarId(int cod_producto) {
        String sql = "SELECT * FROM producto WHERE cod_producto = ?";
        Productos p = new Productos();
        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, cod_producto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p.setCodProducto(rs.getInt("cod_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setImagen(rs.getBinaryStream("imagen"));
                    p.setDetalle(rs.getString("detalle"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getInt("precio"));
                    p.setCodCategoria(rs.getInt("cod_categoria"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

}

    
