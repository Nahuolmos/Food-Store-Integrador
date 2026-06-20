package DAO;

import entities.Pedido;
import java.util.List;
import java.util.stream.Collectors;

public class PedidoDAO extends GenericMemoryDAO<Pedido>{
     public List<Pedido> findByUsuario(Long usuarioId) {
        return findAll().stream().filter(p -> p.getUsuario() != null && p.getUsuario().getId().equals(usuarioId)).collect(Collectors.toList());
    }
}
