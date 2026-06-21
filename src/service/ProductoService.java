package service;

import DAO.CategoriaDAO;
import DAO.ProductoDAO;
import entities.Categoria;
import entities.Producto;
import Exceptions.EntityNotFoundException;
import exceptions.ValidationException;

import java.util.List;

public class ProductoService {

    private final ProductoDAO productoDAO;
    private final CategoriaDAO categoriaDAO;

    public ProductoService() {
        this.productoDAO = new ProductoDAO();
        this.categoriaDAO = new CategoriaDAO();
    }

    public ProductoService(ProductoDAO productoDAO, CategoriaDAO categoriaDAO) {
        this.productoDAO = productoDAO;
        this.categoriaDAO = categoriaDAO;
    }

    public Producto crear(String nombre, String descripcion, Double precio, int stock, String imagen,
                           boolean disponible, Long categoriaId) throws ValidationException, EntityNotFoundException {
        validarDatos(nombre, precio, stock);
        Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
        Producto producto = new Producto(nombre, precio, descripcion, stock, imagen, disponible, categoria);
        return productoDAO.crear(producto);
    }

    public List<Producto> listarTodos() {
        return productoDAO.listarTodos();
    }

    public List<Producto> listarPorCategoria(Long categoriaId) {
        return productoDAO.listarPorCategoria(categoriaId);
    }

    public Producto buscarPorId(Long id) throws EntityNotFoundException {
        return productoDAO.buscarPorId(id);
    }

    public void actualizar(Long id, String nombre, String descripcion, Double precio, int stock,
                            String imagen, boolean disponible, Long categoriaId)
            throws ValidationException, EntityNotFoundException {
        validarDatos(nombre, precio, stock);
        Producto producto = productoDAO.buscarPorId(id);
        Categoria categoria = categoriaDAO.buscarPorId(categoriaId);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio(precio);
        producto.setStock(stock);
        producto.setImagen(imagen);
        producto.setDisponible(disponible);
        producto.setCategoria(categoria);
        productoDAO.actualizar(producto);
    }

    public void eliminar(Long id) throws EntityNotFoundException {
        productoDAO.buscarPorId(id);
        productoDAO.eliminar(id);
    }

    private void validarDatos(String nombre, Double precio, int stock) throws ValidationException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidationException("El nombre del producto no puede estar vacio");
        }
        if (precio == null || precio < 0) {
            throw new ValidationException("El precio del producto no puede ser negativo");
        }
        if (stock < 0) {
            throw new ValidationException("El stock del producto no puede ser negativo");
        }
    }
}
