package DAO;

import config.ConexionDB;
import entities.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Método para guardar un nuevo usuario
    public void save(Usuario u) {
        String sql = "INSERT INTO usuario (nombre, apellido, mail, celular, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellido());
            stmt.setString(3, u.getMail());
            stmt.setString(4, u.getCelular());
            stmt.setString(5, u.getContrasenia());
            stmt.setString(6, u.getRol().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para buscar por email (usado para validar duplicados y login)
    public Usuario findByMail(String mail) {
        String sql = "SELECT * FROM usuario WHERE mail = ? AND eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, mail);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapRowToUsuario(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para listar todos los usuarios no eliminados
    public List<Usuario> findAll() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRowToUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para buscar por ID
    public Usuario findById(Long id) {
        String sql = "SELECT * FROM usuario WHERE id = ? AND eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return mapRowToUsuario(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Método para actualizar usuario
    public void update(Usuario u) {
        String sql = "UPDATE usuario SET nombre=?, apellido=?, mail=?, celular=?, contrasena=?, rol=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getApellido());
            stmt.setString(3, u.getMail());
            stmt.setString(4, u.getCelular());
            stmt.setString(5, u.getContrasenia());
            stmt.setString(6, u.getRol().toString());
            stmt.setLong(7, u.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para eliminar (Soft Delete)
    public void delete(Long id) {
        String sql = "UPDATE usuario SET eliminado = TRUE WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para mapear ResultSet a Objeto
    private Usuario mapRowToUsuario(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getLong("id"));
        u.setNombre(rs.getString("nombre"));
        u.setApellido(rs.getString("apellido"));
        u.setMail(rs.getString("mail"));
        u.setCelular(rs.getString("celular"));
        u.setContrasenia(rs.getString("contrasena"));
        // u.setRol(Rol.valueOf(rs.getString("rol"))); // Descomentar si usas un Enum
        u.setEliminado(rs.getBoolean("eliminado"));
        return u;
    }
}