package service;

import DAO.ProductoDAO;
import DAO.CategoriaDAO;
import entities.Producto;
import entities.Categoria;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import java.util.List;

public class ProductoService {
    private ProductoDAO productoDAO;
    private CategoriaDAO categoriaDAO;

    public ProductoService(ProductoDAO productoDAO, CategoriaDAO categoriaDAO) {
        this.productoDAO = productoDAO;
        this.categoriaDAO = categoriaDAO;
    }

    public void crearProducto(String nombre, Double precio, String descripcion, int stock, String imagen, Long idCategoria) throws ValidationException, EntityNotFoundException {
        if (nombre == null || nombre.trim().isEmpty()) throw new ValidationException("El nombre no puede estar vacío.");
        if (precio < 0) throw new ValidationException("El precio no puede ser menor a 0.");
        if (stock < 0) throw new ValidationException("El stock no puede ser menor a 0.");

        Categoria cat = categoriaDAO.findById(idCategoria);
        if (cat == null || cat.isEliminado()) throw new EntityNotFoundException("La categoría seleccionada no existe.");

        Producto prod = new Producto(nombre, precio, descripcion, stock, imagen, cat);
        prod.setCategoria(cat);
        productoDAO.save(prod);
    }

    public List<Producto> listarProductos() {
        return productoDAO.findAll();
    }

    public List<Producto> listarPorCategoria(Long idCategoria) {
        return productoDAO.findByCategoria(idCategoria);
    }

    public Producto obtenerPorId(Long id) throws EntityNotFoundException {
        Producto p = productoDAO.findById(id);
        if (p == null || p.isEliminado()) throw new EntityNotFoundException("El producto con ID " + id + " no existe.");
        return p;
    }

    public void editarProducto(Long id, String nombre, Double precio, String descripcion, int stock, String imagen, Long idCategoria) 
            throws EntityNotFoundException, ValidationException {
        Producto p = obtenerPorId(id);
        if (nombre == null || nombre.trim().isEmpty()) throw new ValidationException("El nombre no puede estar vacío.");
        if (precio < 0) throw new ValidationException("El precio no puede ser negativo.");
        if (stock < 0) throw new ValidationException("El stock no puede ser negativo.");

        Categoria cat = categoriaDAO.findById(idCategoria);
        if (cat == null || cat.isEliminado()) throw new EntityNotFoundException("La categoría no existe.");

        p.setNombre(nombre);
        p.setPrecio(precio);
        p.setDescripcion(descripcion);
        p.setStock(stock);
        p.setImagen(imagen);
        p.setCategoria(cat);
        productoDAO.update(p);
    }

    public void eliminarProducto(Long id) throws EntityNotFoundException {
        obtenerPorId(id);
        productoDAO.delete(id);
    }
}
