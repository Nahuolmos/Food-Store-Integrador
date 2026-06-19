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

    public Producto(String nombre, Double precio, String descripcion, int stock, String imagen) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        disponible = stock > 0;
        this.imagen = imagen;

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

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setDetallePedidos(List<DetallePedido> detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    
    
    public List<DetallePedido> getDetallePedidos() {
        return Collections.unmodifiableList(detallePedidos);
    }

    @Override
    public String toString() {
        return "Producto{" + "nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion + ", stock=" + stock + ", imagen=" + imagen + ", disponible=" + disponible + ", categoria=" + categoria + ", detallePedidos=" + detallePedidos + '}';
    }
}
