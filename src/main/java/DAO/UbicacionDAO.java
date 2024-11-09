package DAO;

import Conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Modelo.Ubicacion;


public class UbicacionDAO {

    Connection conex;

    public UbicacionDAO() {
        ConexionBD cn = new ConexionBD();
        conex = cn.getConnection();
    }


    public boolean insertarUbicacion(Ubicacion ubicacion) {
        String sql = "INSERT INTO ubicacion_cliente (cod_ubicacion_cliente, departamento, provincia, distrito, direccion, referencia) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conex.prepareStatement(sql)) {
            ps.setInt(1, ubicacion.getCodUbicacionCliente());
            ps.setString(2, ubicacion.getDepartamento());
            ps.setString(3, ubicacion.getProvincia());
            ps.setString(4, ubicacion.getDistrito());
            ps.setString(5, ubicacion.getDireccion());
            ps.setString(6, ubicacion.getReferencia());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean actualizarUbicacion(Ubicacion ubicacion) {
        String sql = "UPDATE ubicacion_cliente SET departamento = ?, provincia = ?, distrito = ?, direccion = ?, referencia = ? WHERE cod_ubicacion_cliente = ?";
        try (PreparedStatement stmt = conex.prepareStatement(sql)) {
            stmt.setString(1, ubicacion.getDepartamento());
            stmt.setString(2, ubicacion.getProvincia());
            stmt.setString(3, ubicacion.getDistrito());
            stmt.setString(4, ubicacion.getDireccion());
            stmt.setString(5, ubicacion.getReferencia());
            stmt.setInt(6, ubicacion.getCodUbicacionCliente());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarUbicacion(int codUbicacionCliente) {
        String sql = "DELETE FROM ubicacion_cliente WHERE cod_ubicacion_cliente = ?";
        try (PreparedStatement stmt = conex.prepareStatement(sql)) {
            stmt.setInt(1, codUbicacionCliente);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Ubicacion obtenerUbicacionPorCodigo(int codUbicacionCliente) {
        String sql = "SELECT * FROM ubicacion_cliente WHERE cod_ubicacion_cliente = ?";
        try (PreparedStatement stmt = conex.prepareStatement(sql)) {
            stmt.setInt(1, codUbicacionCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Ubicacion(
                    rs.getInt("cod_ubicacion_cliente"),
                    rs.getString("departamento"),
                    rs.getString("provincia"),
                    rs.getString("distrito"),
                    rs.getString("direccion"),
                    rs.getString("referencia")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ubicacion> obtenerTodasLasUbicaciones() {
        List<Ubicacion> ubicaciones = new ArrayList<>();
        String sql = "SELECT * FROM ubicacion_cliente";
        try (PreparedStatement stmt = conex.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Ubicacion ubicacion = new Ubicacion(
                    rs.getInt("cod_ubicacion_cliente"),
                    rs.getString("departamento"),
                    rs.getString("provincia"),
                    rs.getString("distrito"),
                    rs.getString("direccion"),
                    rs.getString("referencia")
                );
                ubicaciones.add(ubicacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ubicaciones;
    }
}
