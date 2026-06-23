package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Categoria extends Base {

    private String nombre;
    private String descripcion;
    private List<Producto> productos = new ArrayList<>();

    public Categoria(String nombre, String descripcion) {
        super();
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Producto> getProductos() {
        return Collections.unmodifiableList(productos);
    }

    public void addProducto2Categoria(Producto producto) {
        if (producto != null && !productos.contains(producto)) {
            producto.setCategoria(this);
            productos.add(producto);
        } else {
            System.out.println("El producto " + producto + " no pudo ser ingresado.");
        }
    }

    public void removeProductoFromCategoria(Producto producto) {
        if (producto != null && !productos.contains(producto)) {
            producto.setCategoria(null);
            productos.remove(producto);
        }
    }

    @Override
    public String toString() {
        return "Categoría [ID: " + getId() + " | Nombre: " + nombre + " | Descripción: " + descripcion + "]";
    }
}
