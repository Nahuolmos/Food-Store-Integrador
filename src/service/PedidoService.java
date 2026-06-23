package service;

import DAO.PedidoDAO;
import DAO.UsuarioDAO;
import DAO.ProductoDAO;
import entities.Pedido;
import entities.Usuario;
import entities.Producto;
import entities.DetallePedido;
import enums.Estado;
import enums.FormaPago;
import exceptions.BusinessException;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import java.util.List;

public class PedidoService {
    private PedidoDAO pedidoDAO;
    private UsuarioDAO usuarioDAO;
    private ProductoDAO productoDAO;

    public PedidoService(PedidoDAO pedidoDAO, UsuarioDAO usuarioDAO, ProductoDAO productoDAO) {
        this.pedidoDAO = pedidoDAO;
        this.usuarioDAO = usuarioDAO;
        this.productoDAO = productoDAO;
    }

    public void registrarPedido(Long idUsuario, FormaPago formaPago, List<DetallePedido> mockDetalles) throws EntityNotFoundException, ValidationException {
        
        Usuario u = usuarioDAO.findById(idUsuario);
        if (u == null || u.isEliminado()) throw new EntityNotFoundException("Usuario no válido.");
        if (mockDetalles == null || mockDetalles.isEmpty()) throw new ValidationException("Debe agregar al menos un producto al pedido.");

        Pedido pedido = new Pedido(formaPago, u);

        try {
            for (DetallePedido item : mockDetalles) {
                Producto prod = productoDAO.findById(item.getProducto().getId());
                if (prod == null || prod.isEliminado()) throw new EntityNotFoundException("Producto inexistente.");
                if (item.getCantidad() <= 0) throw new ValidationException("La cantidad debe ser mayor a 0.");
                if (prod.getStock() < item.getCantidad()) {
                    throw new ValidationException("Stock insuficiente para: " + prod.getNombre());
                }

                // Descontamos stock lógicamente
                prod.setStock(prod.getStock() - item.getCantidad());
                productoDAO.update(prod);

                pedido.addDetallePedido(item.getCantidad(), prod);
            }

            pedido.calcularTotal();
            pedidoDAO.save(pedido);

        } catch (BusinessException | EntityNotFoundException | ValidationException e) {
            for(DetallePedido item : mockDetalles) {
                Producto prod = productoDAO.findById(item.getProducto().getId());
                if (prod != null) {
                    prod.setStock(prod.getStock() + item.getCantidad());
                    productoDAO.update(prod);
                }
            }
            throw new ValidationException("Operación cancelada (Simulated Rollback). Detalle: " + e.getMessage());
        }
    }

    public List<Pedido> listarPedidos() {
        return pedidoDAO.findAll();
    }

    public List<Pedido> listarPorUsuario(Long idUsuario) {
        return pedidoDAO.findByUsuario(idUsuario);
    }

    public void actualizarEstadoPago(Long idPedido, Estado nuevoEstado, FormaPago nuevaForma) throws EntityNotFoundException {
        Pedido p = pedidoDAO.findById(idPedido);
        if (p == null || p.isEliminado()) throw new EntityNotFoundException("Pedido no encontrado.");
        if(nuevoEstado != null) p.setEstado(nuevoEstado);
        if(nuevaForma != null) p.setFormaPago(nuevaForma);
        
        pedidoDAO.update(p);
    }

    public void eliminarPedido(Long idPedido) throws EntityNotFoundException {
        Pedido p = pedidoDAO.findById(idPedido);
        if (p == null || p.isEliminado()) throw new EntityNotFoundException("Pedido no encontrado.");
        
        for(DetallePedido dp : p.getDetalles()) {
            dp.setEliminado(true);
        }
        pedidoDAO.delete(idPedido);
    }
}
