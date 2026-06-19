package entities;

import Exceptions.BusinessException;
import enums.Estado;
import enums.FormaPago;
import interfaces.Calculable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido extends Base implements Calculable{

    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario;
    private List<DetallePedido> detalles = new ArrayList();
    
    public Pedido(FormaPago formaPago, Usuario usuario) {
        super();
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
        this.usuario = usuario;
        calcularTotal();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }
    
    public void addDetallePedido(int cantidad, Producto producto) throws BusinessException{
        if (producto == null) {
            throw new BusinessException("El producto no puede ser nulo.");
        }
        
        if (cantidad < 0) {
            throw new BusinessException("La cantidad no puede ser menor a 0");
        }
        
        detalles.add(new DetallePedido(producto, cantidad));
    }
    
    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().equals(producto)) {
                return detalle;
            }
        }
        return null;
    }
    
    public void deleteDetallePedidoByProducto(Producto producto) {
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().equals(producto)) {
                detalle.setEliminado(true);
            }
        }
        calcularTotal();
    }
        
    @Override
    public Double calcularTotal() {
        total = 0.0;
        for (DetallePedido detalle : detalles) {
            total = total + detalle.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString() {
        return '='+"===== Pedido ======" + 
                "\n|Fecha: " + fecha + " - Estado: " + estado + "Usuario" + " |" + 
                "\n" + detalles + 
                "\n- Total: $" + total + " - Metodo de pago: " + formaPago;
    }
}
