package entities;

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
    
    public Pedido(FormaPago formaPago) {
        this.fecha = LocalDate.now();
        this.estado = Estado.PENDIENTE;
        this.formaPago = formaPago;
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
                "\n|Fecha: " + fecha + " - Estado: " + estado + "|" +
                "\n" + detalles + 
                "\n- Total: $" + total + " - Metodo de pago: " + formaPago;
    }
}
