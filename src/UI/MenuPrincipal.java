package UI;

import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

public class MenuPrincipal {
    private CategoriaMenu categoriasMenu;
    private ProductoMenu productosMenu;
    private UsuarioMenu usuariosMenu;
    private PedidoMenu pedidosMenu;

    public MenuPrincipal(CategoriaService cs, ProductoService ps, UsuarioService us, PedidoService pds) {
        this.categoriasMenu = new CategoriaMenu(cs);
        this.productosMenu = new ProductoMenu(ps);
        this.usuariosMenu = new UsuarioMenu(us);
        this.pedidosMenu = new PedidoMenu(pds, ps);
    }

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\n=======================================");
            System.out.println("=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("=======================================");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.println("=======================================");
            opcion = ConsolaHelper.leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1 -> categoriasMenu.mostrarMenu();
                case 2 -> productosMenu.mostrarMenu();
                case 3 -> usuariosMenu.mostrarMenu();
                case 4 -> pedidosMenu.mostrarMenu();
                case 0 -> System.out.println("Finalizando ejecución. ¡Gracias por utilizar Food Store!");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }
}