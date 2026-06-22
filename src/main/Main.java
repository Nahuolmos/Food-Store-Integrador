import DAO.CategoriaDAO;
import DAO.PedidoDAO;
import DAO.ProductoDAO;
import DAO.UsuarioDAO;
import UI.MenuPrincipal;
import enums.Rol;
import service.CategoriaService;
import service.PedidoService;
import service.ProductoService;
import service.UsuarioService;

public class Main {
    public static void main(String[] args) {
        // Inicialización de DAOs en memoria (Simulación persistence.xml)
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProductoDAO productoDAO = new ProductoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        // Inicialización de Capa de Negocio (Services)
        CategoriaService categoriaService = new CategoriaService(categoriaDAO, productoDAO);
        ProductoService productoService = new ProductoService(productoDAO, categoriaDAO);
        UsuarioService usuarioService = new UsuarioService(usuarioDAO);
        PedidoService pedidoService = new PedidoService(pedidoDAO, usuarioDAO, productoDAO);

        // Datos iniciales Hardcodeados (Semilla / Seeding) para pruebas inmediatas
        try {
            categoriaService.crearCategoria("Hamburguesas", "Hamburguesas caseras premium de ternera");
            categoriaService.crearCategoria("Bebidas", "Gaseosas y jugos de línea premium");
            
            productoService.crearProducto("Simple Burger", 4500.0, "Medallón 120g y queso cheddar", 15, "img_b1.png", 1L);
            productoService.crearProducto("Doble Bacon", 5800.0, "Doble carne, cheddar y panceta", 10, "img_b2.png", 1L);
            productoService.crearProducto("Coca Cola 500ml", 1500.0, "Bebida sabor original", 50, "coca.png", 2L);
            
            usuarioService.crearUsuario("Nahuel", "Olmos", "nahuel@foodstore.com", "261000000", "pass123", Rol.ADMIN);
            usuarioService.crearUsuario("Juan", "Perez", "juan@perez.com", "261111111", "perez456", Rol.USUARIO);
        } catch (Exception e) {
            System.out.println("Error cargando registros iniciales ficticios: " + e.getMessage());
        }

        // Inyección e Inicio de la interfaz de usuario en consola
        MenuPrincipal menu = new MenuPrincipal(categoriaService, productoService, usuarioService, pedidoService);
        menu.iniciar();
    }
}