package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Producto extends Base {

    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria;

    private List<DetallePedido> detallePedidos = new ArrayList<>();

    public Producto(String nombre, Double precio, String descripcion, int stock, String imagen, Boolean disponible, Categoria categoria) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.disponible = disponible;
        this.imagen = imagen;
        this.categoria = categoria;

    }

    public void addDetallePedido(DetallePedido detallePedido) {
        if (detallePedido != null && !detallePedidos.contains(detallePedido)) {
            detallePedidos.add(detallePedido);
        }
    }

    public void removeDetallePedido(DetallePedido detallePedido) {
        if (detallePedido != null && !detallePedidos.contains(detallePedido)) {
            detallePedidos.remove(detallePedido);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getStock() {
        return stock;
    }

    public String getImagen() {
        return imagen;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public List<DetallePedido> getDetallePedidos() {
        return Collections.unmodifiableList(detallePedidos);
    }

    @Override
    public String toString() {
        return "Producto{" + "nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion + ", stock=" + stock + ", imagen=" + imagen + ", disponible=" + disponible + ", categoria=" + categoria + ", detallePedidos=" + detallePedidos + '}';
    }
}
