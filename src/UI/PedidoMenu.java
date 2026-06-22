package UI;

import entities.DetallePedido;
import entities.Pedido;
import entities.Producto;
import enums.Estado;
import enums.FormaPago;
import service.PedidoService;
import service.ProductoService;
import java.util.ArrayList;
import java.util.List;

public class PedidoMenu {
    private PedidoService pedidoService;
    private ProductoService productoService;

    public PedidoMenu(PedidoService pedidoService, ProductoService productoService) {
        this.pedidoService = pedidoService;
        this.productoService = productoService;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE PEDIDOS (FOOD STORE) ---");
            System.out.println("1. Listar Pedidos Activos");
            System.out.println("2. Registrar Nuevo Pedido (1..N Detalles)");
            System.out.println("3. Actualizar Estado o Forma de Pago");
            System.out.println("4. Eliminar Pedido (Baja Lógica)");
            System.out.println("0. Volver");
            opcion = ConsolaHelper.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> listar();
                    case 2 -> crearPedido();
                    case 3 -> actualizar();
                    case 4 -> eliminar();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void listar() {
        List<Pedido> lista = pedidoService.listarPedidos();
        if (lista.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    private void crearPedido() throws Exception {
        long idUsr = ConsolaHelper.leerEntero("Ingrese el ID del Cliente (Usuario): ");
        
        System.out.println("Forma de pago: 1. TARJETA | 2. TRANSFERENCIA | 3. EFECTIVO");
        int pagoSel = ConsolaHelper.leerEntero("Seleccione: ");
        FormaPago fp = switch (pagoSel) {
            case 1 -> FormaPago.TARJETA;
            case 2 -> FormaPago.TRANSFERENCIA;
            default -> FormaPago.EFECTIVO;
        };

        List<DetallePedido> carroDeCompras = new ArrayList<>();
        boolean agregando = true;

        while (agregando) {
            System.out.println("\n--- Catálogo de Productos ---");
            productoService.listarProductos().forEach(System.out::println);
            
            long idProd = ConsolaHelper.leerEntero("ID del producto a añadir: ");
            Producto prod = productoService.obtenerPorId(idProd);
            int cant = ConsolaHelper.leerEntero("Cantidad: ");

            DetallePedido item = new DetallePedido(prod, cant);
            carroDeCompras.add(item);

            agregando = ConsolaHelper.leerConfirmacion("¿Desea añadir otro producto a este pedido?");
        }

        pedidoService.registrarPedido(idUsr, fp, carroDeCompras);
        System.out.println("¡Pedido procesado e ingresado al sistema con éxito!");
    }

    private void actualizar() throws Exception {
        listar();
        long idPed = ConsolaHelper.leerEntero("Ingrese ID del pedido a modificar: ");
        
        System.out.println("Nuevo Estado: 1. PENDIENTE | 2. CONFIRMADO | 3. TERMINADO | 4. CANCELADO | 0. No cambiar");
        int estSel = ConsolaHelper.leerEntero("Opción: ");
        Estado est = switch(estSel) {
            case 1 -> Estado.PENDIENTE;
            case 2 -> Estado.CONFIRMADO;
            case 3 -> Estado.TERMINADO;
            case 4 -> Estado.CANCELADO;
            default -> null;
        };

        System.out.println("Nueva Forma de Pago: 1. TARJETA | 2. TRANSFERENCIA | 3. EFECTIVO | 0. No cambiar");
        int pagoSel = ConsolaHelper.leerEntero("Opción: ");
        FormaPago fp = switch (pagoSel) {
            case 1 -> FormaPago.TARJETA;
            case 2 -> FormaPago.TRANSFERENCIA;
            case 3 -> FormaPago.EFECTIVO;
            default -> null;
        };

        pedidoService.actualizarEstadoPago(idPed, est, fp);
        System.out.println("¡Pedido modificado correctamente!");
    }

    private void eliminar() throws Exception {
        listar();
        long idPed = ConsolaHelper.leerEntero("ID del pedido a eliminar: ");
        if (ConsolaHelper.leerConfirmacion("¿Confirma la eliminación lógica de la orden de compra?")) {
            pedidoService.eliminarPedido(idPed);
            System.out.println("¡Pedido ocultado del historial activo!");
        }
    }
}
