package UI;

import entities.Producto;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import service.ProductoService;
import java.util.List;

public class ProductoMenu {

    private final ProductoService productoService;
    private final CategoriaMenu categoriaMenu;
    private final ConsolaHelper consola;

    public ProductoMenu(ProductoService productoService, CategoriaMenu categoriaMenu, ConsolaHelper consola) {
        this.productoService = productoService;
        this.categoriaMenu = categoriaMenu;
        this.consola = consola;
    }

    public void mostrarMenu() {
        boolean volver = false;
        while (!volver) {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar Todos los Productos");
            System.out.println("2. Listar Productos por Categoría");
            System.out.println("3. Crear Producto");
            System.out.println("4. Editar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("0. Volver");
            switch (consola.leerEntero("Seleccione una opción: ")) {
                case 1 -> listar();
                case 2 -> listarPorCategoria();
                case 3 -> crear();
                case 4 -> editar();
                case 5 -> eliminar();
                case 0 -> volver = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    public void listar() {
        List<Producto> lista = productoService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos en el catálogo.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void listarPorCategoria() {
        Long idCat = consola.leerLong("Ingrese ID de la Categoría: ");
        List<Producto> lista = productoService.listarPorCategoria(idCat);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron productos para esa categoría.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crear() {
        String nombre = consola.leerTexto("Nombre del producto: ");
        String desc = consola.leerTexto("Descripción: ");
        double precio = consola.leerDouble("Precio ($): ");
        int stock = consola.leerEntero("Stock inicial: ");
        String imagen = consola.leerTexto("URL de la imagen: ", true);

        categoriaMenu.listar();
        Long idCat = consola.leerLong("ID de la Categoría a asociar: ");

        try {
            Producto nuevo = productoService.crear(nombre, desc, precio, stock, imagen, idCat);
            System.out.println("¡Producto añadido al catálogo con éxito! (id " + nuevo.getId() + ")");
        } catch (EntityNotFoundException | ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void editar() {
        listar();
        Long id = consola.leerLong("Ingrese el ID del producto a modificar: ");
        try {
            Producto actual = productoService.buscarPorId(id);

            String nombre = consola.leerTexto("Nuevo nombre [" + actual.getNombre() + "]: ", true);
            if (nombre.isBlank()) nombre = actual.getNombre();

            String desc = consola.leerTexto("Nueva descripción [" + actual.getDescripcion() + "]: ", true);
            if (desc.isBlank()) desc = actual.getDescripcion();

            String precioStr = consola.leerTexto("Nuevo precio [" + actual.getPrecio() + "] (vacío = no cambiar): ", true);
            double precio = precioStr.isBlank() ? actual.getPrecio() : Double.parseDouble(precioStr);

            String stockStr = consola.leerTexto("Nuevo stock [" + actual.getStock() + "] (vacío = no cambiar): ", true);
            int stock = stockStr.isBlank() ? actual.getStock() : Integer.parseInt(stockStr);

            String imagen = consola.leerTexto("Nueva URL de imagen [" + actual.getImagen() + "]: ", true);
            if (imagen.isBlank()) imagen = actual.getImagen();

            categoriaMenu.listar();
            Long idCat = consola.leerLong("ID de la Categoría [" + actual.getCategoria().getId() + "]: ");

            productoService.actualizar(id, nombre, desc, precio, stock, imagen, idCat);
            System.out.println("¡Producto actualizado!");
        } catch (NumberFormatException e) {
            System.out.println("Error: valor numérico inválido.");
        } catch (EntityNotFoundException | ValidationException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        listar();
        Long id = consola.leerLong("Ingrese el ID del producto a eliminar: ");
        if (!consola.confirmar("¿Desea aplicar baja lógica al producto? (S/N): ")) {
            System.out.println("Operación cancelada.");
            return;
        }
        try {
            productoService.eliminar(id);
            System.out.println("¡Producto retirado de la vista comercial!");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
