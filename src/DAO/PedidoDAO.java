package DAO;

import config.ConexionDB;
import entities.DetallePedido;
import entities.Pedido;
import enums.Estado;
import enums.FormaPago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void save(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedido (fecha, estado, total, forma_pago, usuario_id) VALUES (?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedido (pedido_id, producto_id, cantidad, subtotal) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexionDB.getConexion();
            conn.setAutoCommit(false);

            try (PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setDate(1, Date.valueOf(pedido.getFecha()));
                stmtPedido.setString(2, pedido.getEstado().toString());
                stmtPedido.setDouble(3, pedido.getTotal());
                stmtPedido.setString(4, pedido.getFormaPago().toString());
                stmtPedido.setLong(5, pedido.getUsuario().getId());
                stmtPedido.executeUpdate();

                try (ResultSet rs = stmtPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setId(rs.getLong(1));
                    }
                }
            }

            try (PreparedStatement stmtDetalle = conn.prepareStatement(sqlDetalle)) {
                for (DetallePedido dp : pedido.getDetalles()) {
                    stmtDetalle.setLong(1, pedido.getId());
                    stmtDetalle.setLong(2, dp.getProducto().getId());
                    stmtDetalle.setInt(3, dp.getCantidad());
                    stmtDetalle.setDouble(4, dp.getSubtotal());
                    stmtDetalle.addBatch();
                }
                stmtDetalle.executeBatch();
            }

            conn.commit(); 
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Pedido p) {
        String sql = "UPDATE pedido SET estado = ?, forma_pago = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getEstado().toString());
            stmt.setString(2, p.getFormaPago().toString());
            stmt.setLong(3, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        String sql = "UPDATE pedido SET eliminado = TRUE WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pedido> findAll() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                pedidos.add(mapRowToPedido(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public List<Pedido> findByUsuario(Long idUsuario) {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE usuario_id = ? AND eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapRowToPedido(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public Pedido findById(Long id) {
        String sql = "SELECT * FROM pedido WHERE id = ? AND eliminado = FALSE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToPedido(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private Pedido mapRowToPedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setId(rs.getLong("id"));
        p.setFecha(rs.getDate("fecha").toLocalDate());
        p.setEstado(Estado.valueOf(rs.getString("estado")));
        p.setTotal(rs.getDouble("total"));
        p.setFormaPago(FormaPago.valueOf(rs.getString("forma_pago")));
        return p;
    }
}