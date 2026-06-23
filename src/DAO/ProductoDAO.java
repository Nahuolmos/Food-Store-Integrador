package DAO;

import config.ConexionDB;
import entities.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void save(Producto p) {
        String sql = "INSERT INTO producto (nombre, precio, descripcion, stock, imagen, categoria_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setDouble(2, p.getPrecio());
            stmt.setString(3, p.getDescripcion());
            stmt.setInt(4, p.getStock());
            stmt.setString(5, p.getImagen());
            stmt.setLong(6, p.getCategoria().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean tieneProductosAsociados(Long categoriaId) {
    String sql = "SELECT COUNT(*) FROM producto WHERE categoria_id = ? AND eliminado = FALSE";
    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setLong(1, categoriaId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; 
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public List<Producto> findAll() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRowToProducto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Producto findById(Long id) {
        String sql = "SELECT * FROM producto WHERE id = ? AND eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapRowToProducto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Producto> findByCategoria(Long idCategoria) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE categoria_id = ? AND eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRowToProducto(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void update(Producto p) {
        String sql = "UPDATE producto SET nombre=?, precio=?, descripcion=?, stock=?, imagen=?, categoria_id=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre());
            stmt.setDouble(2, p.getPrecio());
            stmt.setString(3, p.getDescripcion());
            stmt.setInt(4, p.getStock());
            stmt.setString(5, p.getImagen());
            stmt.setLong(6, p.getCategoria().getId());
            stmt.setLong(7, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void delete(Long id) {
        String sql = "UPDATE producto SET eliminado = TRUE WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Producto mapRowToProducto(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setId(rs.getLong("id"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setStock(rs.getInt("stock"));
        p.setImagen(rs.getString("imagen"));
        p.setEliminado(rs.getBoolean("eliminado"));
        return p;
    }
}