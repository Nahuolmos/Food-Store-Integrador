package UI;

import exceptions.BusinessException;
import exceptions.EntityNotFoundException;
import exceptions.ValidationException;
import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

import java.util.Scanner;

public class MenuPrincipal {

    private final ConsolaHelper consola;
    private final CategoriaMenu categoriaMenu;
    private final ProductoMenu productoMenu;
    private final UsuarioMenu usuarioMenu;
    private final PedidoMenu pedidoMenu;

    public MenuPrincipal(CategoriaService categoriaService, ProductoService productoService,
                          UsuarioService usuarioService, PedidoService pedidoService) {
        this.consola = new ConsolaHelper(new Scanner(System.in));

        this.categoriaMenu = new CategoriaMenu(categoriaService, productoService, consola);
        this.productoMenu = new ProductoMenu(productoService, categoriaMenu, consola);
        this.usuarioMenu = new UsuarioMenu(usuarioService, consola);
        this.pedidoMenu = new PedidoMenu(pedidoService, usuarioMenu, productoMenu, consola);
    }

    public void iniciar() throws ValidationException, BusinessException, EntityNotFoundException {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            switch (consola.leerEntero("Seleccione: ")) {
                case 1 -> categoriaMenu.mostrarMenu();
                case 2 -> productoMenu.mostrarMenu();
                case 3 -> usuarioMenu.mostrarMenu();
                case 4 -> pedidoMenu.mostrarMenu();
                case 0 -> { salir = true; System.out.println("¡Hasta luego!"); }
                default -> System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        consola.cerrar();
    }
}