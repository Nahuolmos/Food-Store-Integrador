package service;

import Exceptions.EntityNotFoundException;
import DAO.PedidoDAO;
import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import entities.Usuario;
import enums.Estado;
import enums.FormaPago;

import java.time.LocalDate;
import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;

    public PedidoService(PedidoDAO pedidoDAO, UsuarioService usuarioService, ProductoService productoService) {
        this.pedidoDAO = pedidoDAO;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
    }

    public Pedido iniciarPedido(Long usuarioId, FormaPago formaPago) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        Pedido pedido = new Pedido(formaPago, usuario);
        return pedido;
    }

    /** Usa obligatoriamente el método propio de Pedido definido en el UML. */
    public void agregarDetalle(Pedido pedido, Long productoId, int cantidad) {
        if (cantidad <= 0) {
            throw new ValidationException("La cantidad debe ser mayor a 0.");
        }
        Producto producto = productoService.buscarPorId(productoId);
        if (!producto.getDisponible()) {
            throw new ValidationException("El producto '" + producto.getNombre() + "' no está disponible.");
        }
        pedido.addDetallePedido(cantidad, producto);
    }

    public void quitarDetalle(Pedido pedido, Long productoId) {
        Producto producto = productoService.buscarPorId(productoId);
        pedido.deleteDetallePedidoByProducto(producto);
    }

    /** Calcula el total vía Calculable y persiste el pedido completo. */
    public Pedido confirmarPedido(Pedido pedido) {
        if (pedido.getDetalles().isEmpty()) {
            throw new ValidationException("El pedido debe tener al menos un detalle.");
        }
        pedido.calcularTotal();

        // --- Simulación de transacción ---
        // En la versión JDBC acá iría BEGIN TRANSACTION + INSERT pedido + INSERT detalles + COMMIT,
        // con ROLLBACK si algo falla. En memoria, si falla el guardado no queda nada persistido.
        try {
            return pedidoDAO.save(pedido);
        } catch (RuntimeException ex) {
            throw new ValidationException("No se pudo registrar el pedido: " + ex.getMessage());
        }
    }

    public List<Pedido> listar() {
        return pedidoDAO.findAll();
    }

    public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoDAO.findByUsuario(usuarioId);
    }

    public Pedido buscarPorId(Long id) throws EntityNotFoundException {
        return pedidoDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("No existe el pedido con id " + id));
    }

    public void actualizarEstado(Long id, Estado nuevoEstado) throws EntityNotFoundException {
        Pedido pedido = buscarPorId(id);
        pedido.setEstado(nuevoEstado);
        pedidoDAO.update(pedido);
    }

    public void actualizarFormaPago(Long id, FormaPago nuevaFormaPago) throws EntityNotFoundException {
        Pedido pedido = buscarPorId(id);
        pedido.setFormaPago(nuevaFormaPago);
        pedidoDAO.update(pedido);
    }

    public void eliminar(Long id) throws EntityNotFoundException {
        Pedido pedido = buscarPorId(id);
        pedidoDAO.delete(id);
        for (DetallePedido detalle : pedido.getDetalles()) {
            detalle.setEliminado(true);
        }
    }
}
