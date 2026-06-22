package DAO;

import entities.Pedido;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO extends GenericMemoryDAO<Pedido> {
    public List<Pedido> findByUsuario(Long idUsuario) {
        List<Pedido> res = new ArrayList<>();
        for (Pedido p : storage.values()) {
            if (!p.isEliminado() && p.getUsuario() != null && p.getUsuario().getId().equals(idUsuario)) {
                res.add(p);
            }
        }
        return res;
    }
}
