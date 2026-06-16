package entities;

public class DetallePedido extends Base {

    private int cantidad;
    private Double subtotal;
    private Producto producto;

    public DetallePedido(Producto producto, Integer cantidad) {
        super();
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = calcularSubtotal();
    }

    private Double calcularSubtotal() {
        if (producto == null || cantidad < 0) {
            return 0.0;
        }

        return producto.getPrecio() * cantidad;
    }

    public void actualizarSubtotal() {
        this.subtotal = calcularSubtotal();
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        actualizarSubtotal();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        actualizarSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return '-'+ "-- Detalle del Pedido ---" +
                "\n- Producto '" + producto.getNombre() + "' " + cantidad + " unidades." +
                "\n- Subtotal: $" + subtotal;
    }
}
