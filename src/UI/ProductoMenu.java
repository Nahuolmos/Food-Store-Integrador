package UI;

import entities.Producto;
import service.ProductoService;
import java.util.List;

public class ProductoMenu {
    private ProductoService service;

    public ProductoMenu(ProductoService service) {
        this.service = service;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar Todos los Productos");
            System.out.println("2. Listar Productos por Categoría");
            System.out.println("3. Crear Producto");
            System.out.println("4. Editar Producto");
            System.out.println("5. Eliminar Producto");
            System.out.println("0. Volver");
            opcion = ConsolaHelper.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listarGeneral();
                    case 2 -> listarPorCategoria();
                    case 3 -> crear();
                    case 4 -> editar();
                    case 5 -> eliminar();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listarGeneral() {
        List<Producto> lista = service.listarProductos();
        if (lista.isEmpty()) {
            System.out.println("No hay productos en el catálogo.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void listarPorCategoria() {
        long idCat = ConsolaHelper.leerEntero("Ingrese ID de la Categoría: ");
        List<Producto> lista = service.listarPorCategoria(idCat);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron productos para esa categoría.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crear() throws Exception {
        String nombre = ConsolaHelper.leerCadena("Nombre del producto: ");
        double precio = ConsolaHelper.leerDouble("Precio ($): ");
        String desc = ConsolaHelper.leerCadena("Descripción: ");
        int stock = ConsolaHelper.leerEntero("Stock inicial: ");
        String imagen = ConsolaHelper.leerCadena("URL de la imagen: ");
        long idCat = ConsolaHelper.leerEntero("ID de la Categoría a asociar: ");

        service.crearProducto(nombre, precio, desc, stock, imagen, idCat);
        System.out.println("¡Producto añadido al catálogo con éxito!");
    }

    private void editar() throws Exception {
        listarGeneral();
        long id = ConsolaHelper.leerEntero("Ingrese el ID del producto a modificar: ");
        String nombre = ConsolaHelper.leerCadena("Nuevo nombre: ");
        double precio = ConsolaHelper.leerDouble("Nuevo precio ($): ");
        String desc = ConsolaHelper.leerCadena("Nueva descripción: ");
        int stock = ConsolaHelper.leerEntero("Nuevo stock: ");
        String imagen = ConsolaHelper.leerCadena("Nueva URL de imagen: ");
        long idCat = ConsolaHelper.leerEntero("ID de la Categoría: ");

        service.editarProducto(id, nombre, precio, desc, stock, imagen, idCat);
        System.out.println("¡Producto actualizado!");
    }

    private void eliminar() throws Exception {
        listarGeneral();
        long id = ConsolaHelper.leerEntero("Ingrese el ID del producto a eliminar: ");
        if (ConsolaHelper.leerConfirmacion("¿Desea aplicar baja lógica al producto?")) {
            service.eliminarProducto(id);
            System.out.println("¡Producto retirado de la vista comercial!");
        }
    }
}
